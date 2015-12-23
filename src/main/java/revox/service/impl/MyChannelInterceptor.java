package revox.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.security.core.session.SessionCreationEvent;
import revox.repository.userRepository;

/**
 * Created by ashraf on 8/1/2015.
 */
public class MyChannelInterceptor extends ChannelInterceptorAdapter {
    @Autowired
    userRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        return super.preSend(message, channel);
    }
}
