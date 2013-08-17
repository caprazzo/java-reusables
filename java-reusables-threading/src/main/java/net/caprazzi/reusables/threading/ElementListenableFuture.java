package net.caprazzi.reusables.threading;

import com.google.common.util.concurrent.AbstractFuture;

/**
 * Support class for @see QueueExecutor
 * @param <TElement>
 */
final class ElementListenableFuture<TElement> extends AbstractFuture<Boolean> {
    private final TElement element;

    static <TElement> ElementListenableFuture<TElement> create(TElement element) {
        return new ElementListenableFuture(element);
    }

    ElementListenableFuture(TElement element) {
        this.element = element;
    }

    public TElement getElement() {
        return element;
    }

    public void setSuccess() {
        this.set(true);
    }

    public void setException(Exception ex) {
        this.setException(ex);
    }
}
