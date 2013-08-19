package net.caprazzi.reusables.threading;

import com.google.common.util.concurrent.AbstractFuture;

public class ListenableConfirmation extends AbstractFuture<Boolean> {

    public void setSuccess() {
        set(true);
    }

    public void setFailure(Throwable ex) {
        super.setException(ex);
    }

    public static ListenableConfirmation failed(Throwable ex) {
        ListenableConfirmation confirmation = new ListenableConfirmation();
        confirmation.setException(ex);
        return confirmation;
    }

    public static ListenableConfirmation create() {
        return new ListenableConfirmation();
    }
}
