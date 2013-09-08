package net.caprazzi.reusables.common;

import java.text.MessageFormat;

public abstract class FormattedException extends Exception {

    public FormattedException(Throwable t) {
        super(t);
    }

    public FormattedException(Throwable t, String message, Object... params) {
        super(MessageFormat.format(message, params), t);
    }

    public FormattedException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }

}
