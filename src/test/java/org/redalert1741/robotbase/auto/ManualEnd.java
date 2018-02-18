package org.redalert1741.robotbase.auto;

import java.util.Map;

import org.redalert1741.robotbase.auto.core.AutoMoveEnd;

public class ManualEnd implements AutoMoveEnd {
    public boolean completed;

    @Override
    public void setArgs(Map<String, String> args) {}

    @Override
    public void start() {
        completed = false;
    }

    @Override
    public boolean isFinished() {
        return completed;
    }
}
