package org.redalert1741.robotbase.auto.core;

public interface AutoMoveMoveFactory {
    /**
     * Create an {@link AutoMoveMove}. Meant to be implemented by a lambda.
     * @return Generated AutoMoveMove
     */
    public AutoMoveMove createAutoMoveMove();
}
