package net.caprazzi.reusables.common;

import java.text.MessageFormat;

public class FormattedRuntimeException extends RuntimeException {
    public FormattedRuntimeException(Throwable t) {
        super(t);
    }
    public FormattedRuntimeException(Throwable t, String message, Object... params) {
        super(MessageFormat.format(message, params), t);
    }
    public FormattedRuntimeException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }
}
