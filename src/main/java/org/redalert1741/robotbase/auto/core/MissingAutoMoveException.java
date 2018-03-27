package org.redalert1741.robotbase.auto.core;

public class MissingAutoMoveException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public MissingAutoMoveException(String type, int movenum) {
        super("Missing AutoMoveMoveFactory for type \""+type+"\" in move "+movenum);
    }
}
