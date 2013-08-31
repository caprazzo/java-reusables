package net.caprazzi.reusables.threading;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public abstract class SingleThreadQueueExecutor<TElement> implements QueueExecutor<TElement> {

    private final Logger Log;

    private final BlockingQueue<ElementListenableFuture<TElement>> queue = new LinkedBlockingQueue<ElementListenableFuture<TElement>>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public SingleThreadQueueExecutor() {
        Log = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public ListenableConfirmation enqueue(TElement element) {
        Preconditions.checkNotNull(element, "Can't enqueue null elements");

        if (Log.isDebugEnabled())
            Log.debug("Adding element to queue: {}", element);

        ElementListenableFuture future = ElementListenableFuture.create(element);

        try {
            queue.put(future);
        } catch (InterruptedException e) {
            Log.warn("Interrupted Exception while adding an element to internal queue.");
            Thread.currentThread().interrupt();
        }

        return future;
    }

    protected abstract void doProcess(TElement element);

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public void start() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted() && !executor.isShutdown()) {
                    try {
                        ElementListenableFuture<TElement> element = queue.take();
                        if (element == null) {

                            if (Log.isTraceEnabled())
                                Log.trace("Element is null, not processing");

                            continue;
                        }

                        if (Log.isTraceEnabled())
                            Log.trace("Processing element {}", element);

                        try {
                            doProcess(element.getElement());
                            element.setResult();
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

    @Override
    public void stop() {
        ExecutorUtils.shutdown(Log, executor, 2, TimeUnit.SECONDS);
    }
}
