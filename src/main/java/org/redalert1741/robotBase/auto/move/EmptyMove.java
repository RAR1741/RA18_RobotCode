package org.redalert1741.robotBase.auto.move;

import java.util.Map;

import org.redalert1741.robotBase.auto.core.AutoMoveMove;

public class EmptyMove implements AutoMoveMove
{
    @Override
    public void setArgs(Map<String, String> args) {}

    @Override
    public void run() {}

    @Override
    public void start() {}

    @Override
    public void stop() {}
}
