package me.io2.staminaplugin;

public class MSTimer {
    private long startTime = 0L;
    public MSTimer() {
        startTime = System.currentTimeMillis();
    }

    public boolean hasTimePassed(long time) {
        return System.currentTimeMillis() - startTime > time;
    }

    public void reset() {
        startTime = System.currentTimeMillis();
    }
}
