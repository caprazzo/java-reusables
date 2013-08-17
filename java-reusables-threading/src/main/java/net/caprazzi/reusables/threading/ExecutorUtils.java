package net.caprazzi.reusables.threading;

import org.slf4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorUtils {

    /**
     * Tries to gracefully shut down an ExecutorService
     * @param Log A Logger instance
     * @param executor The executor to shutdown
     * @param timeout How long to wait for termination. Total time could be double this number
     * @param unit Unit of the timeout parameter
     */
    public static void shutdown(Logger Log, ExecutorService executor, long timeout, TimeUnit unit) {
        // Disable new tasks from being submitted
        executor.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            if (!executor.awaitTermination(timeout, unit)) {
                executor.shutdownNow(); // Cancel currently executing tasks

                // Wait a while for tasks to respond to being cancelled
                if (!executor.awaitTermination(timeout, unit)) {
                    Log.warn("Executor did not terminate cleanly");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            executor.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }

        Log.info("Shut down complete");
    }
}
