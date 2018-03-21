package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.Manipulation;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class ManipulationLiftMove implements AutoMoveMove{
	private Manipulation manip;
	private double firstStageHeight;
	
	public ManipulationLiftMove(Manipulation m){
		manip = m;
	}
	
	@Override
	public void setArgs(Map<String, String> args) {
		firstStageHeight = Double.parseDouble(args.get("height"));
	}

	@Override
	public void start() {/*doesn't need*/}

	@Override
	public void run() {
		manip.setFirstStageHeight(firstStageHeight);
	}

	@Override
	public void stop() {/*doesn't need*/}

}
