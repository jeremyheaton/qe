import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jerem on 3/24/2020.
 */
public class MessageQueueManager {

    private Map<String, MessageQueue<Object>> messageQueues = new HashMap<>();
    private static MessageQueueManager messageQueueManager;


    private MessageQueueManager() {

    }

    public static MessageQueueManager getInstance() {
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

    private MessageQueue<Object> subscribe(String queue) {
        return messageQueues.computeIfAbsent(queue, k -> new MessageQueue<Object>(10));
    }

    CompletableFuture<Boolean> publish(String queue, Object t) {
        MessageQueue<Object> map = messageQueues.get(queue);
        if (map == null) return subscribe(queue).add(t);
        return map.add(t);
    }

    CompletableFuture<Object> consume(String queue) {
        MessageQueue<Object> map = messageQueues.get(queue);
        if (map == null) return subscribe(queue).poll();
        return map.poll();
    }
}
