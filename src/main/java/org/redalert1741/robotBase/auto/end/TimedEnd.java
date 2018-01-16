package org.redalert1741.robotBase.auto.end;

import java.util.Map;

import org.redalert1741.robotBase.auto.core.AutoMoveEnd;

public class TimedEnd implements AutoMoveEnd {
    private long startTime;
    private int duration;

    @Override
    public void setArgs(Map<String, String> args) {
        duration = Integer.parseInt(args.get("length"));
        System.out.println(duration);
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
