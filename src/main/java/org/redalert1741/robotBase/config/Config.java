package org.redalert1741.robotBase.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Config {
    private static Map<String, Double> doubleSettings;
    private static Map<String, Boolean> booleanSettings;
    private static Map<String, String> stringSettings;
    private static List<Configurable> configurables;

    public static void dumpConfig() {
        System.out.println("DUMP");
        for(Map.Entry<String, Double> e : doubleSettings.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
        System.out.println("END DUMP");
    }

    public static boolean loadFromFile(String filename) {
        return parse(filename);
    }

    public static double getSetting(String name, double reasonable_default) {
        double retval = reasonable_default;
        name = name.toLowerCase();

        if (doubleSettings.containsKey(name)) {
            retval = doubleSettings.get(name);
        }

        return retval;
    }

    public static boolean getSetting(String name, boolean r_default) {
        boolean retval = r_default;
        name = name.toLowerCase();

        if (booleanSettings.containsKey(name)) {
            retval = booleanSettings.get(name);
        }

        return retval;
    }

    public static String getSetting(String name, String r_default) {
        String retval = r_default;
        name = name.toLowerCase();

        if (stringSettings.containsKey(name)) {
            retval = stringSettings.get(name);
        }

        return retval;
    }

    public static void setSetting(String name, double value) {
        name = name.toLowerCase();
        doubleSettings.put(name, value);
    }

    static boolean parse(String filename) {
        doubleSettings = new HashMap<String,Double>();
        booleanSettings = new HashMap<>();
        stringSettings = new HashMap<>();
        Scanner infile;
        try {
            infile = new Scanner(new File(filename));
        }
        catch (FileNotFoundException e) {
            System.out.println("Couldn't find config file \"" + filename + "\"");
            return false;
        }

        /*
         * Match exactly 0 '#'
         * Match as many alphanumeric characters + '_'
         * Optional ' ' around an '='
         * Match a number, with an optional '.' and more numbers
         */
        Pattern doublepattern = Pattern.compile("^#{0}([\\w\\d_]+)\\s*?=\\s*?(-?\\d*(?:\\.\\d+)?)$");
        Pattern booleanpattern = Pattern.compile("^#{0}([\\w\\d_]+)\\s*?= ?([Tt]rue|[Ff]alse)$");
        Pattern stringpattern = Pattern.compile("^#{0}([\\w\\d_]+)\\s*?=\\s*?\"([^\"]*)\"$");
        Matcher t;
        while(infile.hasNextLine()) {
            String in = infile.nextLine();
            if((t = doublepattern.matcher(in)).matches()) {
                String key = t.group(1);
                Double value = Double.parseDouble(t.group(2));
                doubleSettings.put(key.toLowerCase(), value);
                //System.out.println(key + ": " + value);
            }
            else if((t = booleanpattern.matcher(in)).matches()) {
                String key = t.group(1);
                Boolean value = Boolean.parseBoolean(t.group(2));
                booleanSettings.put(key.toLowerCase(), value);
                //System.out.println(key + ": " + value);
            }
            else if((t = stringpattern.matcher(in)).matches()) {
                String key = t.group(1);
                String value = t.group(2);
                stringSettings.put(key.toLowerCase(), value);
                //System.out.println(key + ": " + value);
            }
        }
        infile.close();
        return true;
    }

    public static void addConfigurable(Configurable c)
    {
        if(configurables == null) { configurables = new ArrayList<>(); }
        configurables.add(c);
    }

    public static void reloadConfig() {
        for(Configurable c : configurables) {
            c.reloadConfig();
        }
    }
}
