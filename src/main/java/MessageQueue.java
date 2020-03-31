import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jerem on 3/24/2020.
 */
public class MessageQueue {

    private ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<>();
    private volatile int pointer = -1;
    private int maxBucketSize;

    @SuppressWarnings("unchecked")
    MessageQueue() {
    }

    CompletableFuture<Message> poll() {
        return CompletableFuture.supplyAsync(() -> queue.poll() , ExecuterPool.executorService);
    }

    CompletableFuture<Boolean> add(Message object) {
       return CompletableFuture.supplyAsync(() -> queue.add(object) , ExecuterPool.executorService);
    }
}
