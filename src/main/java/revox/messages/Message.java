package revox.messages;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by ashraf on 8/2/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable {
    private MessageType messageType;
    private String content;
    private String from;
    private String to;

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public Message(MessageType messageType, String content, String from, String to) {
        this.messageType = messageType;
        this.content = content;
        this.from = from;
        this.to = to;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Message setMessageType(MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Message setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public Message setTo(String to) {
        this.to = to;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }
}