package net.caprazzi.reusables.threading;

import com.google.common.util.concurrent.ListenableFuture;
import net.caprazzi.reusables.common.Managed;

public interface ResultQueueExecutor<TElement, TResult> extends Managed {
    public ListenableFuture<TResult> enqueue(TElement element);
    public int size();
}
