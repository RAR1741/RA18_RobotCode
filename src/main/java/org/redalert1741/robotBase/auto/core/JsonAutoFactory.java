package org.redalert1741.robotBase.auto.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * JSON parser implentation of {@link AutoFactory}
 */
public class JsonAutoFactory extends AutoFactory
{
	@Override
	public Autonomous makeAuto(String in)
	{
		Autonomous out = null;
		try
		{
			JsonObject o = new JsonParser().parse(new FileReader(in)).getAsJsonObject();
			JsonArray m = o.get("auto").getAsJsonArray();
			List<AutoMove> moves = new ArrayList<>();
			for(JsonElement tmove : m)
			{
				JsonObject move = tmove.getAsJsonObject();
				System.out.println(move.get("type").getAsString());
				AutoMoveMove amm = ammf.get(move.get("type").getAsString()).createAutoMoveMove();
				Map<String, String> args = new HashMap<>();
				for(Entry<String, JsonElement> arg : move.get("args").getAsJsonObject().entrySet())
				{
					args.put(arg.getKey(), arg.getValue().getAsString());
				}
				amm.setArgs(args);
				AutoMoveEnd ame = amef.get(move.get("end").getAsJsonObject().get("type").getAsString()).createAutoMoveEnd();
				args = new HashMap<>();
				for(Entry<String, JsonElement> arg : move.get("end").getAsJsonObject().get("args").getAsJsonObject().entrySet())
				{
					args.put(arg.getKey(), arg.getValue().getAsString());
				}
				ame.setArgs(args);
				JsonObject moveargs = move.get("moveargs").getAsJsonObject();
				Map<String, Object> mArgs = new HashMap<String, Object>();
				for(Entry<String, JsonElement> a : moveargs.entrySet())
				{
					if(a.getValue().getAsJsonPrimitive().isString())
						mArgs.put(a.getKey(), a.getValue().getAsJsonPrimitive().getAsString());
					else if(a.getValue().getAsJsonPrimitive().isNumber())
						mArgs.put(a.getKey(), a.getValue().getAsJsonPrimitive().getAsDouble());
					else if(a.getValue().getAsJsonPrimitive().isBoolean())
						mArgs.put(a.getKey(), a.getValue().getAsJsonPrimitive().getAsBoolean());
				}
				
				AutoMove am = new AutoMove(amm, ame, mArgs);
				moves.add(am);
			}
			out = new Autonomous(moves);
		}
		catch (JsonIOException | JsonSyntaxException | FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return out;
	}
}
