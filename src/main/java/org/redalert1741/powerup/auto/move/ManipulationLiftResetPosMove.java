package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.Manipulation;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class ManipulationLiftResetPosMove implements AutoMoveMove{
	private Manipulation manip;
	private double firstStageHeight;
	private double secondStageHeight;
	
	public ManipulationLiftResetPosMove(Manipulation manip){
		this.manip = manip;
	}
	
	@Override
	public void setArgs(Map<String, String> args) {
		firstStageHeight = Double.parseDouble(args.getOrDefault("firstHeight", 
				String.valueOf(manip.getFirstStageHeight())));
		secondStageHeight = Double.parseDouble(args.getOrDefault("secondHeight", 
				String.valueOf(manip.getSecondStageHeight())));
	}

	@Override
	public void start() {/*doesn't need*/}

	@Override
	public void run() {
		manip.resetFirstStageHeight(firstStageHeight);
		manip.resetSecondStageHeight(secondStageHeight);
	}

	@Override
	public void stop() {/*doesn't need*/}

}
