import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jerem on 3/24/2020.
 */
public class MessageQueueManager {

    private Map<String, MessageQueue> messageQueues = new HashMap<>();
    private static MessageQueueManager messageQueueManager;


    private MessageQueueManager() {

    }

    static MessageQueueManager getInstance() {
        if (messageQueueManager == null) {
            messageQueueManager = new MessageQueueManager();
        }
        return messageQueueManager;
    }

    public boolean removeQueue(String queue) {
        if (messageQueues.get(queue) != null) {
            messageQueues.remove(queue);
            return true;
        }
        return false;
    }

    private MessageQueue subscribe(String queue) {
        return messageQueues.computeIfAbsent(queue, k -> new MessageQueue());
    }

    CompletableFuture<Boolean> publish(String queue, Message message) {
        MessageQueue map = messageQueues.get(queue);
        if (map == null) return subscribe(queue).add(message);
        return map.add(message);
    }

    CompletableFuture<Message> consume(String queue) {
        MessageQueue map = messageQueues.get(queue);
        if (map == null) return subscribe(queue).poll();
        return map.poll();
    }
}
