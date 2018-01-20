package org.redalert1741.robotbase.auto.end;

import java.util.Map;

import org.redalert1741.robotbase.auto.core.AutoMoveEnd;

public class EmptyEnd implements AutoMoveEnd {
    @Override
    public void setArgs(Map<String, String> args) { /* EmptyEnd has no args */}

    @Override
    public boolean isFinished() { return true; }

    @Override
    public void start() { /* EmptyEnd has no start initialization */}
}
