package org.redalert1741.robotBase.auto.core;

public interface AutoMoveEndFactory
{
    /**
     * Create an {@link AutoMoveEnd}. Meant to be implemented by a lambda.
     * @return Generated AutoMoveEnd
     */
    public AutoMoveEnd createAutoMoveEnd();
}
