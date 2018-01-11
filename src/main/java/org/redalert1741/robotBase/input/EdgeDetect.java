package org.redalert1741.robotBase.input;

public class EdgeDetect 
{
	private boolean state;
	
	public EdgeDetect()
	{
		state = false;
	}
	
	public boolean Check(boolean in)
	{
		boolean out;
		if(in && state)
		{
			out = true;
		}
		else
		{
			out = false;
		}

		if(in)
		{
			state = false;
		}
		else
		{
			state = true;
		}
		return out;
	}
}
