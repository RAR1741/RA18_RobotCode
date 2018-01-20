package org.redalert1741.robotbase.auto.end;

import java.util.Map;

import org.redalert1741.robotbase.auto.core.AutoMoveEnd;

public class TimedEnd implements AutoMoveEnd {
    private long startTime;
    private int duration;

    @Override
    public void setArgs(Map<String, String> args) {
        duration = Integer.parseInt(args.get("length"));
    }

    @Override
    public void start() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() > startTime + duration;
    }
}
