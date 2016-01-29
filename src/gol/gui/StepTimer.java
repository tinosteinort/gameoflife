package gol.gui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tino on 26.01.2016.
 */
public class StepTimer extends AnimationTimer {

    private final LongProperty stepDurationProperty;
    private final Runnable runnable;
    private boolean running;

    private long last = 0;

    public StepTimer(final long stepDuration, final Runnable runnable) {
        this.stepDurationProperty = new SimpleLongProperty(stepDuration);
        this.runnable = runnable;
    }

    @Override
    public void handle(final long nowInNanos) {
        final long now = TimeUnit.NANOSECONDS.toMillis(nowInNanos);
        final long diff = now - last;
        if (diff < stepDurationProperty.get()) {
            return;
        }
        last = now;

        runnable.run();
    }

    @Override
    public void start() {
        super.start();
        running = true;
    }

    @Override
    public void stop() {
        super.stop();
        running = false;
    }

    public LongProperty stepDurationProperty() {
        return stepDurationProperty;
    }

    public boolean isRunning() {
        return running;
    }
}
