package org.redalert1741.robotbase.auto.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * Parses a single POJO move.
     * @param move Move to parse
     * @return Parsed AutoMove
     * @throws MissingAutoMoveFactoryException if there's no
     * matching AutoMoveMoveFactory or AutoMoveEndFactory
     */
    public AutoMove parseMove(AutoPojo.MovePojo move) throws MissingAutoMoveFactoryException {
        if(move == null) {
            return new AutoMove(null, null, null);
        }

        AutoMoveMove amm = null;
        if(move.type != null) {
            if(ammf.containsKey(move.type)) {
                amm = ammf.get(move.type).createAutoMoveMove();
                amm.setArgs(move.args);
            } else {
                throw new MissingAutoMoveFactoryException(
                        MissingAutoMoveFactoryException.FactoryType.MOVE, move.type);
            }
        }

        AutoMoveEnd ame = null;
        if(move.end != null) {
            if(amef.containsKey(move.end.type)) {
                ame = amef.get(move.end.type).createAutoMoveEnd();
                ame.setArgs(move.end.args);
            } else {
                throw new MissingAutoMoveFactoryException(
                        MissingAutoMoveFactoryException.FactoryType.END, move.end.type);
            }
        }
        return new AutoMove(amm, ame, move.moveargs);
    }

    /**
     * Parses a full autonomous from POJO.
     * @param pojo Auto to parse
     * @return Runnable autonomous
     */
    public Autonomous parseAutonomous(AutoPojo pojo) {
        List<AutoMove> moves = new ArrayList<>();
        for (AutoPojo.MovePojo move : pojo.auto) {
            try {
                moves.add(parseMove(move));
            } catch(MissingAutoMoveFactoryException e) {
                if(e.factory == MissingAutoMoveFactoryException.FactoryType.MOVE) {
                    throw new MissingAutoMoveException(e.type, pojo.auto.indexOf(move));
                } else {
                    throw new MissingAutoEndException(e.type, pojo.auto.indexOf(move));
                }
            }
        }
        return new Autonomous(moves);
    }
}
