import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jerem on 3/24/2020.
 */
public class MessageQueue<T> {

    private ConcurrentLinkedQueue<T>[] buckets;
    private volatile int pointer = -1;

    @SuppressWarnings("unchecked")
    MessageQueue(int bucketSize) {
        buckets = new ConcurrentLinkedQueue[bucketSize];
        for(int i = 0; i<buckets.length; i++) {
            buckets[i] = new ConcurrentLinkedQueue<>();
        }
    }

    private CompletableFuture<ConcurrentLinkedQueue<T>> get() {
        return CompletableFuture.supplyAsync(() -> buckets[getPointer()] , ExecuterPool.executorService);
    }

    CompletableFuture<T> poll() {
        return CompletableFuture.supplyAsync(() -> buckets[getPointer()].poll() , ExecuterPool.executorService);
    }

    CompletableFuture<Boolean> add(T t) {
       return CompletableFuture.supplyAsync(() -> buckets[getPointer()].add(t) , ExecuterPool.executorService);
    }

    private int getPointer() {
        return pointer >= buckets.length ? pointer = 0 : pointer++;
    }
}
