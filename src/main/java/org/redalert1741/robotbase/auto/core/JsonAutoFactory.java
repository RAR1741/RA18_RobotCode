package org.redalert1741.robotbase.auto.core;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * JSON parser implentation of {@link AutoFactory}
 */
public class JsonAutoFactory extends AutoFactory {
    @Override
    public Autonomous makeAuto(String in) {
        try {
            AutoPOJO pojo = new Gson().fromJson(new FileReader(in), AutoPOJO.class);
            return parseAutonomous(pojo);
        }
        catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
