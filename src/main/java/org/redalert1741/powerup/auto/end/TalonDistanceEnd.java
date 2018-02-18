package org.redalert1741.powerup.auto.end;

import java.util.Map;

import org.redalert1741.robotbase.auto.core.AutoMoveEnd;
import org.redalert1741.robotbase.wrapper.TalonSrxWrapper;

public class TalonDistanceEnd implements AutoMoveEnd {
    private TalonSrxWrapper talon;
    private int distance;
    private int start;
    private boolean dir;

    public TalonDistanceEnd(TalonSrxWrapper talon) {
        this.talon = talon;
    }
    
    @Override
    public void setArgs(Map<String, String> args) {
        distance = (int)Math.round(Double.parseDouble(args.get("distance")));
        if(args.containsKey("unit")) {
            if(args.get("unit").equals("inches")) {
                distance = (int) Math.round(Double.parseDouble(args.get("distance"))
                        /(28*(625/49.0)*6*Math.PI));
            }
        }
        
    }

    @Override
    public void start() {
        start = talon.getPosition();
        dir = distance > 0;
    }

    @Override
    public boolean isFinished() {
        int pos = talon.getPosition();
        System.out.println(pos + " ? "+start+distance);
        return (dir ? pos > start + distance :
            pos < start + distance);
    }
}
