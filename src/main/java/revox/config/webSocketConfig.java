package revox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import revox.service.impl.MyChannelInterceptor;


@Configuration
@EnableWebSocketMessageBroker
public class webSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Bean
    public MyChannelInterceptor myChannelInterceptor() {
        return new MyChannelInterceptor();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(myChannelInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/hello").withSockJS();
//        RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
//        registry.addEndpoint("/hello")
//                .setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy))
//                .setAllowedOrigins("*");
    }
}