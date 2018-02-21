package org.redalert1741.robotbase.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A config file. Parses a file and gets values.
 * <br><br>
 * A configuration item in a file is defined as a variable
 * and value. A configuration item name matches this regex:
 * <pre><code>
 * [\w\d_]+
 * </code></pre>
 * A value is parse to one of the following types:
 * <br><br>
 * String, requiring two {@code "}s on either side
 * <br>
 * Double, based on {@link Double#parseDouble(String)}
 * <br>
 * Boolean, based on {@link Boolean#parseBoolean(String)}
 * <br><br>
 * Any other format will not be loaded into the Config and
 * attempted access with {@link #getSetting(String, Object)} will return
 * the reasonable default.
 */
public class Config {
    /**
     * Used for finding a type and storage.
     */
    public static interface ConfigItem {
        boolean testCorrectType(String input);

        Object getValue();
    }

    /**
     * Parses and stores doubles.
     */
    public static class DoubleItem implements ConfigItem {
        private double value;

        DoubleItem() {
            value = 0;
        }

        @Override
        public boolean testCorrectType(String input) {
            try {
                value = Double.parseDouble(input);
            } catch(NumberFormatException e) {
                return false;
            }
            return true;
        }

        @Override
        public Object getValue() {
            return value;
        }
    }

    /**
     * Parses and stores booleans.
     */
    public static class BooleanItem implements ConfigItem {
        private boolean value;

        BooleanItem() {
            value = false;
        }

        @Override
        public boolean testCorrectType(String input) {
            if(input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                value = Boolean.parseBoolean(input);
                return true;
            }
            return false;
        }

        @Override
        public Object getValue() {
            return value;
        }
    }

    /**
     * Parses and stores Strings.
     */
    public static class StringItem implements ConfigItem {
        private String value;

        StringItem() {
            value = "";
        }

        @Override
        public boolean testCorrectType(String input) {
            String tmp = input.trim();
            if(tmp.charAt(0) == '\"' && tmp.charAt(tmp.length()-1) == '\"') {
                value = tmp.substring(1, tmp.length()-1);
                return true;
            }
            return false;
        }

        @Override
        public Object getValue() {
            return value;
        }
    }

    private String filename;
    private Map<String, ConfigItem> items;
    private List<Configurable> configurables;

    /**
     * Initializes the Config.
     */
    public Config() {
        configurables = new ArrayList<>();
    }

    /**
     * Loads configuration from a file.
     * @param filename name of the configuration file
     * @return success loading the file
     */
    public boolean loadFromFile(String filename) {
        this.filename = filename;
        return parse(filename);
    }

    boolean parse(String filename) {
        items = new HashMap<>();
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
        Matcher match;
        String in;
        while(infile.hasNextLine()) {
            in = infile.nextLine();
            match = itemPattern.matcher(in);
            parseLine(match);
        }
        infile.close();
        return true;
    }

    /**
     * Finishes parsing a single line based on a Matcher.
     * @param match line matcher
     */
    private void parseLine(Matcher match) {
        if(match.matches()) {
            ConfigItem[] tmpItems = { new DoubleItem(), new StringItem(), new BooleanItem() };
            for(ConfigItem item : tmpItems) {
                parseItem(item, match.group(1), match.group(2));
            }
        }
    }

    /**
     * Parses a single item.
     * @param item Item type
     * @param name Name to store setting
     * @param toParse String to parse for setting
     */
    private void parseItem(ConfigItem item, String name, String toParse) {
        if(item.testCorrectType(toParse)) {
            items.put(name, item);
        }
    }

    /**
     * Adds a configurable.
     * Calls {@link Configurable#reloadConfig(Config)} when {@link #reloadConfig()}
     * is called.
     * @param configurable configurable to add
     * @see Configurable
     * @see #removeConfigurable(Configurable)
     * @see #removeAllConfigurables()
     */
    public void addConfigurable(Configurable configurable) {
        configurables.add(configurable);
    }

    /**
     * Removes a specific configurable.
     * @param configurable configurable to remove
     * @see Configurable
     * @see #addConfigurable(Configurable)
     * @see #removeAllConfigurables()
     */
    public void removeConfigurable(Configurable configurable) {
        configurables.remove(configurable);
    }

    /**
     * Removes all configurables.
     * @see Configurable
     * @see #addConfigurable(Configurable)
     * @see #removeConfigurable(Configurable)
     */
    public void removeAllConfigurables() {
        configurables.removeAll(configurables);
    }

    /**
     * Reloads the config from the last specified file from {@link #loadFromFile(String)}.
     * Calls each {@link Configurable Configurable's} {@link Configurable#reloadConfig(Config)}.
     */
    public void reloadConfig() {
        parse(filename);
        for(Configurable c : configurables) {
            c.reloadConfig(this);
        }
    }

    /**
     * Retrieves the ConfigItem form of a configuration.
     * If you actually want the value, use {@link #getSetting(String, Object)}.
     * @param name configuration to get
     * @return specified item
     * @see #getSetting(String, Object)
     */
    public ConfigItem getItem(String name) {
        return items.get(name);
    }
    
    /**
     * Retrieves a setting. only supported reasonableDefault types are Boolean, Double, and String.
     * Returns reasonableDefault if the setting is not found.
     * @param name Setting to retrieve
     * @param reasonableDefault Default value to use in event of missing value
     * @param <T> return type, matches type of reasonableDefault
     * @return The requested value or the reasonableDefault
     */
    @SuppressWarnings("unchecked")
    public <T> T getSetting(String name, T reasonableDefault) {
        if(getItem(name) != null) {
            return (T) ((ConfigItem) getItem(name)).getValue();
        }
        return reasonableDefault;
    }
}
