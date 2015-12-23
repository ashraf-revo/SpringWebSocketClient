package revox;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
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
import revox.messages.ConversationMessage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RevoxApplicationTests {

    @Test
    public void Main() throws InterruptedException, ExecutionException {
        List<Transport> transports = new ArrayList<>();
        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
        transports.add(new WebSocketTransport(standardWebSocketClient));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        String stompUrl = "ws://localhost:8080/hello";
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        String plainCreds = "revo:revo";
        String base64Creds = new String(Base64.getEncoder().encode(plainCreds.getBytes()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + base64Creds);
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders(httpHeaders);

        ListenableFuture<StompSession> connect = stompClient.connect(stompUrl,headers, new StompSessionHandlerAdapter() {
        });

        connect.addCallback(new ListenableFutureCallback<StompSession>() {
            @Override
            public void onFailure(Throwable ex) {
            }

            @Override
            public void onSuccess(StompSession result) {
                result.subscribe("/user/topic/greetings", new StompSessionHandlerAdapter() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return ConversationMessage.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        ConversationMessage message = (ConversationMessage) payload;
                        System.out.println(message.getContent());
                    }
                });
            }
        });


        connect.addCallback(new ListenableFutureCallback<StompSession>() {
            @Override
            public void onFailure(Throwable ex) {
            }

            @Override
            public void onSuccess(StompSession result) {
                StompHeaders stompHeaders = new StompHeaders();
                stompHeaders.setDestination("/app/hello");
                ConversationMessage payload = new ConversationMessage();
                payload.setContent("ddddddd");
                result.send(stompHeaders, payload);
            }
        });
        Thread.sleep(4000);
    }
}