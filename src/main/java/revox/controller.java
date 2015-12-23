package revox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import revox.messages.Message;
import revox.repository.userRepository;

import java.security.Principal;

/**
 * Created by ashraf on 8/2/15.
 */
@Controller
class controller {

    @Autowired
    userRepository userRepository;
    @Autowired
    SimpUserRegistry simpUserRegistry;

    @RequestMapping(value = "/")
    public String login() {
        return "redirect:/index.html";
    }

    @MessageMapping("/hello")
    public void greeting(Message message, Principal principal) {

        simpUserRegistry.getUsers().forEach(simpUser -> System.out.println(simpUser.getName()));

        simpMessagingTemplate.convertAndSendToUser(
                principal.getName(), "/topic/greetings",
                message.setContent("welcome " + message.getContent()));

    }

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping("/csrf")
    @ResponseBody
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
