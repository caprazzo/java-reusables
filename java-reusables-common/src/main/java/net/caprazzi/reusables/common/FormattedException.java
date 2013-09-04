package net.caprazzi.reusables.common;

import java.text.MessageFormat;

public abstract class FormattedException extends Exception {

    public FormattedException(Exception ex) {
        super(ex);
    }

    public FormattedException(Exception ex, String message, Object... params) {
        super(MessageFormat.format(message, params), ex);
    }

    public FormattedException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }

}
