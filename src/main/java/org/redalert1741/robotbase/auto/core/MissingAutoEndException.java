package org.redalert1741.robotbase.auto.core;

public class MissingAutoEndException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public MissingAutoEndException(String type, int movenum) {
        super("Missing AutoMoveEndFactory for type \""+type+"\" in move "+movenum);
    }
}
