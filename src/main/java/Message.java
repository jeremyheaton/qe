import java.util.UUID;

/**
 * Created by jerem on 3/31/2020.
 */
public class Message {

    private UUID messageId;
    private String messageBody;

    Message(String messageBody) {
        messageId = UUID.randomUUID();
    }

    public String toString() {
        return "{\"messageId\":\"" +
                messageId +
                "\"," +
                "{\"messageBody\":\"" +
                messageBody + "\"}";
    }
}
