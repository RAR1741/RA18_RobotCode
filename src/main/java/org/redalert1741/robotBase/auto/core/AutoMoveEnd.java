package org.redalert1741.robotBase.auto.core;

import java.util.Map;

public interface AutoMoveEnd
{
	/**
	 * Sets the arguments for the object to use. Actual usage depends on subclass
	 * @param args Map of arguments
	 */
	public void setArgs(Map<String, String> args);
	public void start();
	public boolean isFinished();
}
