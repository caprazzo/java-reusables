package net.caprazzi.reusables.threading;

import com.google.common.util.concurrent.AbstractFuture;

public class ElementListenableFuture<TElement> extends ListenableConfirmation {
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

    public void setException(Exception ex) {
        this.setException(ex);
    }

    public void setResult() {
        this.set(true);
    }
}
