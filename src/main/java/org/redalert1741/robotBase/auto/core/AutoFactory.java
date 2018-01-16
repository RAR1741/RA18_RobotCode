package org.redalert1741.robotBase.auto.core;

import java.util.HashMap;
import java.util.Map;

/**
 *  Abstract class for parsing Autonomous files
 *  @see JsonAutoFactory
 */
public abstract class AutoFactory
{
    protected static Map<String, AutoMoveMoveFactory> ammf = new HashMap<>();
    protected static Map<String, AutoMoveEndFactory> amef = new HashMap<>();

    /**
     * Adds an {@link AutoMoveMoveFactory} to the static map
     * @param name The name of the type to be associated with the factory
     * @param a The factory
     * @return AutoFactory class, for method chaining
     * @see #addMoveEnd(String, AutoMoveEndFactory)
     */
    public static Class<AutoFactory> addMoveMove(String name, AutoMoveMoveFactory a) {
        ammf.put(name, a); return AutoFactory.class;
    }

    /**
     * Adds an {@link AutoMoveEndFactory} to the static map
     * @param name The name of the type to be associated with the factory
     * @param a The factory
     * @return AutoFactory class, for method chaining
     * @see #addMoveMove(String, AutoMoveMoveFactory)
     */
    public static Class<AutoFactory> addMoveEnd(String name, AutoMoveEndFactory a) {
        amef.put(name, a); return AutoFactory.class;
    }

    /**
     * Generates an autonomous given a file path.
     * @param in Path of the autonomous file
     * @return The generated {@link Autonomous}
     */
    public abstract Autonomous makeAuto(String in);
}
