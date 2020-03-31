import java.util.UUID;

/**
 * Created by jerem on 3/31/2020.
 */
public class Message {

    private UUID messageId;
    private String messageBody;

    Message(String messageBody) {
        messageId = UUID.randomUUID();
        this.messageBody = messageBody;
    }

    UUID getMessageId() {
        return messageId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String toString() {
        return "{\"messageId\":\"" +
                messageId +
                "\"," +
                "{\"messageBody\":\"" +
                messageBody + "\"}";
    }
}
