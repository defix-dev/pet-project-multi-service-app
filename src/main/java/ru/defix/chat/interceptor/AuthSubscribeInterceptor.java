package ru.defix.chat.interceptor;

import org.springframework.messaging.simp.stomp.StompCommand;
import ru.defix.chat.exception.MessageAccessDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Service;
import ru.defix.user.service.UserService;

@Service
public class AuthSubscribeInterceptor implements ChannelInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthSubscribeInterceptor.class);

    private final UserService userService;

    @Autowired
    public AuthSubscribeInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getCommand() == null) {
            return message;
        }

        if (accessor.getCommand() != StompCommand.SUBSCRIBE) {
            return message;
        }

        String destination = accessor.getDestination();
        if (destination == null || !destination.contains("/chat/listen")) {
            return message;
        }

        logger.debug("Start checking subscribing");

        int expectedId = Integer.parseInt(destination.substring(destination.lastIndexOf('/') + 1));

        logger.debug("Checking authentication");

        String username = (String) accessor.getSessionAttributes().get("username");
        if (username == null) {
            throw new MessageAccessDeniedException();
        }

        var user = userService.findByUsername(username);
        if (user == null) {
            throw new MessageAccessDeniedException();
        }

        int userId = user.getId();

        logger.debug("Provided username: " + username);
        logger.debug("userid:" + userId);

        if (expectedId != userId) {
            throw new MessageAccessDeniedException();
        }

        return message;
    }
}
