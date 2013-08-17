package net.caprazzi.reusables.common;

/**
 * An interface for objects which need to be started and stopped.
 */
public interface Managed {
    public void start();
    public void stop();
}
