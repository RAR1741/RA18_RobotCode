package org.redalert1741.robotbase.auto;

import static org.junit.Assert.*;

import org.junit.Test;
import org.redalert1741.robotbase.auto.core.AutoPojo;
import org.redalert1741.robotbase.auto.core.JsonAutoFactory;
import org.redalert1741.robotbase.auto.core.MissingAutoEndException;
import org.redalert1741.robotbase.auto.core.MissingAutoMoveException;
import org.redalert1741.robotbase.auto.core.MissingAutoMoveFactoryException;

public class ExceptionsTest {
    @Test
    public void genericMissingTest() {
        JsonAutoFactory factory = new JsonAutoFactory();
        AutoPojo.MovePojo move = new AutoPojo.MovePojo();
        move.type = "not real";
        try {
            factory.parseMove(move);
            fail();
        } catch(MissingAutoMoveFactoryException e) {
            return;
        }
    }
    
    @Test
    public void missingMoveMoveTest() {
        try {
            new JsonAutoFactory().makeAuto(getClass().getResource("missing-move-test.json").getPath());
            fail();
        } catch (MissingAutoMoveException e) {
            return;
        }
    }
    
    @Test
    public void missingMoveEndTest() {
        try {
            new JsonAutoFactory().makeAuto(getClass().getResource("missing-end-test.json").getPath());
            fail();
        } catch (MissingAutoEndException e) {
            return;
        }
    }
}
