import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jerem on 3/24/2020.
 */
class ExecuterPool {
    static ExecutorService executorService = Executors.newCachedThreadPool();
}