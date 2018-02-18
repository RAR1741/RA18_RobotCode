package org.redalert1741.robotbase.auto;

import java.util.Map;

import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class CounterMove implements AutoMoveMove {
    public int count;

    @Override
    public void setArgs(Map<String, String> args) {}

    @Override
    public void start() {
        count = 0;
    }

    @Override
    public void run() {
        count++;
    }

    @Override
    public void stop() {}
}
