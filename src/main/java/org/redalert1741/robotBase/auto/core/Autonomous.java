package org.redalert1741.robotBase.auto.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the entirety of an Autonomous program, consisting of a set of {@link AutoMove moves} to be run one after the other.
 * @see AutoFactory
 */
public class Autonomous
{
    private List<AutoMove> moves;
    private List<AutoMove> active;
    private int i;

    public Autonomous(List<AutoMove> m)
    {
        moves = m;
        i = 0;
        active = new ArrayList<>();
    }

    public void start()
    {
        i = 0;
        moves.get(i).start();
    }

    /**
     * Runs the autonomous. Meant to be run iteratively.
     */
    public void run()
    {
        //TODO make async work
//        if(i < moves.size())
//        {
//            moves.get(i).run();
//            if(moves.get(i).isFinshed())
//            {
//                i++;
//                if(i == moves.size())
//                    return;
//                moves.get(i).start();
//            }
//        }
        addMoves();
        executeActiveMoves();
        finishActiveMoves();
    }
    
    public void addMoves()
    {
        if(active.isEmpty() || active.get(active.size()-1).isAsync())
        {
            do
            {
                active.add(moves.get(i));
                moves.get(i).start();
                i++;
            }
            while(moves.get(i-1).isAsync());
        }
    }
    
    public void executeActiveMoves()
    {
        for(AutoMove move : active)
        {
            move.run();
        }
    }
    
    public void finishActiveMoves()
    {
        List<AutoMove> toRemove = new ArrayList<>();
        for(AutoMove move : active)
        {
            if(move.isFinshed())
            {
                toRemove.add(move);
            }
        }
        active.removeAll(toRemove);
    }
}
