package net.caprazzi.reusables.threading;

import com.google.common.util.concurrent.AbstractFuture;

/**
 * Support class for @see QueueExecutor
 * @param <TElement>
 */
final class ElementListenableResult<TElement, TResult> extends AbstractFuture<TResult> {
    private final TElement element;

    static <TElement, TResult> ElementListenableResult<TElement, TResult> create(TElement element) {
        return new ElementListenableResult(element);
    }

    ElementListenableResult(TElement element) {
        this.element = element;
    }

    public TElement getElement() {
        return element;
    }

    public void setException(Exception ex) {
        this.setException(ex);
    }

    public void setResult(TResult result) {
        this.set(result);
    }
}
