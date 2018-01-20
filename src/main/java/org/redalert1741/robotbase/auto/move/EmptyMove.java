package org.redalert1741.robotbase.auto.move;

import java.util.Map;

import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class EmptyMove implements AutoMoveMove
{
    @Override
    public void setArgs(Map<String, String> args) { /* EmptyMove takes no args */ }

    @Override
    public void run() { /* EmptyMove, as the name implies, does nothing */ }

    @Override
    public void start() { /* EmptyMove does not start */ }

    @Override
    public void stop() { /* EmptyMove does not stop */ }
}
