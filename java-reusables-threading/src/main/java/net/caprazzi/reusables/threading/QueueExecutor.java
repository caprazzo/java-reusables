package net.caprazzi.reusables.threading;

import net.caprazzi.reusables.common.Managed;

public interface QueueExecutor<TElement> extends Managed {
    public ElementListenableFuture enqueue(TElement element);
}
