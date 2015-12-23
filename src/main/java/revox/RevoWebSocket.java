package revox;

import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import revox.messages.Message;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

/**
 * Created by revo on 23/12/15.
 */
public class RevoWebSocket {
    private WebSocketHttpHeaders headers;
    private String stompUrl;
    public ListenableFuture<StompSession> stompSession;
    private WebSocketStompClient stompClient;

    public RevoWebSocket(String URL, String email, String password) {
        List<Transport> transports = new ArrayList<>();
        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
        transports.add(new WebSocketTransport(standardWebSocketClient));
        SockJsClient sockJsClient = new SockJsClient(transports);
        this.stompUrl = "ws://" + URL;
        this.stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        this.headers = new WebSocketHttpHeaders(getHttpHeaders(email, password));
    }

    public ListenableFuture<StompSession> Connect() {
        stompSession = stompClient.connect(stompUrl, headers, new StompSessionHandlerAdapter() {
        });
        return stompSession;
    }

    public boolean isConnected() {
        try {
            return stompSession.get().isConnected();
        } catch (InterruptedException | ExecutionException e) {
        }
        return false;
    }

    private HttpHeaders getHttpHeaders(String email, String password) {
        String plainCreds = email + ":" + password;
        String base64Creds = new String(Base64.getEncoder().encode(plainCreds.getBytes()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + base64Creds);
        return httpHeaders;
    }

    public void send(String path, Message payload) {
        stompSession.addCallback(new ListenableFutureCallback<StompSession>() {
            @Override
            public void onFailure(Throwable ex) {
            }

            @Override
            public void onSuccess(StompSession result) {
                result.send(path, payload);
            }
        });
    }

    public void subscribe(String path, RevoStompHandler<Message> handlerAdapter) throws SessionException {
        if (stompSession.isDone()) {
            try {
                stompSession.get().subscribe(path, handlerAdapter);
            } catch (InterruptedException | ExecutionException ignored) {
            }
        } else throw new SessionException("Not connected ");
    }
}

class SessionException extends Exception {
    public SessionException(String message) {
        super(message);
    }
}

class RevoStompHandler<T> implements StompFrameHandler {
    private Type aClass;
    private Consumer<T> consumer;

    public RevoStompHandler(Class<T> aClass, Consumer<T> consumer) {
        this.aClass = aClass;
        this.consumer = consumer;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return aClass;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        consumer.accept((T) payload);
    }
}
