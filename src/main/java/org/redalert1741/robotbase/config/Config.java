package org.redalert1741.robotbase.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Config {
    /**
     * Used for finding a type and storage.
     */
    public static interface ConfigItem {
        boolean testCorrectType(String input);
    }

    /**
     * Parses and stores doubles
     */
    public static class DoubleItem implements ConfigItem {
        private double value;

        DoubleItem() { value = 0; }

        @Override
        public boolean testCorrectType(String input) {
            try {
                value = Double.parseDouble(input);
            } catch(NumberFormatException e) {
                return false;
            }
            return true;
        }

        public double getValue() {
            return value;
        }
    }

    /**
     * Parses and stores booleans.
     */
    public static class BooleanItem implements ConfigItem {
        private boolean value;

        BooleanItem() { value = false; }

        @Override
        public boolean testCorrectType(String input) {
            if(input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                value = Boolean.parseBoolean(input);
                return true;
            }
            return false;
        }

        public boolean getValue() {
            return value;
        }
    }

    /**
     * Parses and stores Strings.
     */
    public static class StringItem implements ConfigItem {
        private String value;

        StringItem() { value = ""; }

        @Override
        public boolean testCorrectType(String input) {
            String tmp = input.trim();
            if(tmp.charAt(0) == '\"' && tmp.charAt(tmp.length()-1) == '\"') {
                value = tmp.substring(1, tmp.length()-1);
                return true;
            }
            return false;
        }

        public String getValue() {
            return value;
        }
    }

    private String filename;
    private Map<String, ConfigItem> items;
    private List<Configurable> configurables;

    public boolean loadFromFile(String filename) {
        this.filename = filename;
        return parse(filename);
    }

    boolean parse(String filename) {
        Scanner infile;
        try {
            infile = new Scanner(new File(filename));
        }
        catch (FileNotFoundException e) {
            return false;
        }

        /*
         * Match exactly 0 '#'
         * Match as many alphanumeric characters + '_'
         * Optional ' ' around an '='
         * Match something
         */
        Pattern itemPattern = Pattern.compile("^#{0}([\\w\\d_]+)\\s*?=\\s*?(.+)$");
        Matcher t;
        String in;
        items = new HashMap<>();
        while(infile.hasNextLine()) {
            in = infile.nextLine();
            t = itemPattern.matcher(in);
            if(t.matches()) {
                ConfigItem[] tmpItems = { new DoubleItem(), new StringItem(), new BooleanItem() };
                for(ConfigItem item : tmpItems) {
                    if(item.testCorrectType(t.group(2))) {
                        items.put(t.group(1), item);
                    }
                }
            }
        }
        infile.close();
        return true;
    }

    public void addConfigurable(Configurable c)
    {
        if(configurables == null) { configurables = new ArrayList<>(); }
        configurables.add(c);
    }

    public void reloadConfig() {
        parse(filename);
        for(Configurable c : configurables) {
            c.reloadConfig();
        }
    }

    public ConfigItem getItem(String name) {
        return items.get(name);
    }

    public String getSetting(String name, String reasonableDefault) {
        if(items.containsKey(name) && items.get(name) instanceof StringItem) {
            return ((StringItem) items.get(name)).getValue();
        } else {
            return reasonableDefault;
        }
    }

    public boolean getSetting(String name, boolean reasonableDefault) {
        if(items.containsKey(name) && items.get(name) instanceof BooleanItem) {
            return ((BooleanItem) items.get(name)).getValue();
        } else {
            return reasonableDefault;
        }
    }

    public double getSetting(String name, double reasonableDefault) {
        if(items.containsKey(name) && items.get(name) instanceof DoubleItem) {
            return ((DoubleItem) items.get(name)).getValue();
        } else {
            return reasonableDefault;
        }
    }
}
