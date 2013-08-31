package net.caprazzi.reusables.threading;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * A QueueExecutor that uses a single thread to dequeue items.
 */
public abstract class SingleThreadQueueResultExecutor<TElement, TResult> implements ResultQueueExecutor<TElement, TResult> {

    private final Logger Log;

    private final BlockingQueue<ElementListenableResult<TElement, TResult>> queue = new LinkedBlockingQueue<ElementListenableResult<TElement, TResult>>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    protected SingleThreadQueueResultExecutor() {
        Log = LoggerFactory.getLogger(this.getClass());
    }

    public abstract TResult doProcess(TElement element);

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public final void start() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted() && !executor.isShutdown()) {
                    try {
                        ElementListenableResult<TElement, TResult> element = queue.take();
                        if (element == null) {

                            if (Log.isTraceEnabled())
                                Log.trace("Element is null, not processing");

                            continue;
                        }

                        if (Log.isTraceEnabled())
                            Log.trace("Processing element {}", element);

                        try {
                            element.setResult(doProcess(element.getElement()));
                            Log.trace("Processing complete for element {}", element);

                        }
                        catch (Exception ex) {
                            Log.error("Error while processing element {}: {}", element, ex);
                            element.setException(ex);
                        }
                    } catch (InterruptedException e) {
                        // it's ok, we are shutting down now
                        break;
                    }
                }
            }
        });
    }

    public final ListenableFuture enqueue(TElement element) {
        Preconditions.checkNotNull(element, "Can't enqueue null elements");

        if (Log.isDebugEnabled())
            Log.debug("Adding element to queue: {}", element);

        ElementListenableResult future = ElementListenableResult.create(element);

        try {
            queue.put(future);
        } catch (InterruptedException e) {
            Log.warn("Interrupted Exception while adding an element to internal queue.");
            Thread.currentThread().interrupt();
        }

        return future;
    }

    @Override
    public final void stop() {
        ExecutorUtils.shutdown(Log, executor, 2, TimeUnit.SECONDS);
    }

}