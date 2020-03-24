import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jerem on 3/24/2020.
 */
public class MessageQueue {

    private Map<String, ConcurrentLinkedQueue<Object>> messageQueues = new HashMap<>();
    private static MessageQueue messageQueue;


    private MessageQueue() {

    }

    public static MessageQueue getInstance() {
        if (messageQueue == null) {
            messageQueue = new MessageQueue();
        }
        return messageQueue;
    }

    public boolean removeQueue(String queue) {
        if (messageQueues.get(queue) != null) {
            messageQueues.remove(queue);
            return true;
        }
        return false;
    }

    private ConcurrentLinkedQueue<Object> subscribe(String queue) {
        return messageQueues.computeIfAbsent(queue, k -> new ConcurrentLinkedQueue<Object>());
    }

    public <T> boolean publish(String queue, T t) {
        ConcurrentLinkedQueue<Object> map = messageQueues.get(queue);
        if (map == null) return subscribe(queue).add(t);
        return map.add(t);
    }

    public Object consume(String queue) {
        ConcurrentLinkedQueue<Object> map = messageQueues.get(queue);
        if (map == null) return subscribe(queue).poll();
        return map.poll();
    }
}
