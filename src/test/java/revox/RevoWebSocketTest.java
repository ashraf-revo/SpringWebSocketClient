package revox;

import org.junit.Test;
import revox.messages.Message;

/**
 * Created by revo on 23/12/15.
 */
public class RevoWebSocketTest {
    @Test
    public void testsend() throws Exception {
        RevoWebSocket revoWebSocket = new RevoWebSocket("localhost:8080/hello", "revo", "revo");
        revoWebSocket.Connect();
        while (!revoWebSocket.isDone()) {
        }
        revoWebSocket.subscribe("/user/topic/greetings", new RevoStompHandler<>(Message.class, m ->
                System.out.println(m.getContent())
        ));
        revoWebSocket.send("/app/hello", new Message("lovex"));
        Thread.sleep(1000);
    }
}