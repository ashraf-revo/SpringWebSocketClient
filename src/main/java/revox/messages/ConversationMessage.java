package revox.messages;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by ashraf on 8/2/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversationMessage implements Serializable {
    private MessageType messageType;
    private String content;
    private String from;
    private String to;

    public ConversationMessage() {
    }


    public ConversationMessage(MessageType messageType, String content, String from, String to) {
        this.messageType = messageType;
        this.content = content;
        this.from = from;
        this.to = to;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public ConversationMessage setMessageType(MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public ConversationMessage setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public ConversationMessage setTo(String to) {
        this.to = to;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ConversationMessage setContent(String content) {
        this.content = content;
        return this;
    }
}