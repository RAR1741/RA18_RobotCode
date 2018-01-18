package org.redalert1741.robotbase.logging;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Logs data to a file.
 * Also polls the state of a {@link Loggable}
 */
public class DataLogger {
    private String filename;
    private FileWriter log = null;
    private Map<String, String> fields;
    private List<Loggable> loggables;
    private NetworkTable table;

    /**
     * This is a constructor.
     */
    public DataLogger() {
        fields = new LinkedHashMap<String,String>();
        loggables = new ArrayList<>();
        table = NetworkTableInstance.getDefault().getTable("logging");
        for(String s : table.getKeys()) {
            table.delete(s);
        }
    }

    /**
     * Opens a file to write the log to.
     * @param filename Name of the file
     * @return Success of opening the file
     */
    public boolean open(String filename) {
        this.filename = filename;
        try {
            log = new FileWriter(filename);
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Closes the current log file.
     */
    public void close() {
        if(log!=null) {
            try {
                log.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the current log file and reopens it with the same attributes.
     * @return Success of reopening the file
     */
    public boolean reset() {
        close();
        boolean success = open(this.filename);
        writeAttributes();
        return success;
    }

    public boolean hasAttribute(String name) {
        return fields.containsKey(name);
    }

    /**
     * Adds a new attribute to the logger.
     * @param field Attribute to add
     * @return Whether the attribute was successfully added, fails if it already exists
     */
    public boolean addAttribute(String field) {
        if (hasAttribute(field)) {
            // TODO: Output warning
            return false; // We already have this attribute
        }

        fields.put(field, "");

        return true;
    }

    /**
     * Logs an object. Uses {@link #toString()}.
     * @param field Attribute to log
     * @param data Object to log
     * @return Whether the value was successfully logged
     */
    public boolean log(String field, Object data) {
        if(!hasAttribute(field)) { return false; }

        fields.put(field, data.toString());
        return true;
    }

    /**
     * Writes the attributes to the log file, should be called before starting to log.
     * @return Success of writing
     */
    public boolean writeAttributes() {
        try {
            for (Map.Entry<String,String> e : fields.entrySet()) {
                log.write(e.getKey() + ',');
            }
            log.write("\n");
            log.flush();
        }
        catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Writes the current log values.
     * @return Success of writing
     */
    public boolean writeLine() {
        try {
            for (Map.Entry<String,String> e : fields.entrySet()) {
                log.write(e.getValue() + ',');
            }
            log.write("\n");
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Adds a {@link Loggable}.
     * TODO descibe loggables
     * @param l Loggable to add
     */
    public void addLoggable(Loggable loggable) {
        loggables.add(loggable);
    }

    /**
     * Calls each {@link Loggable Loggable's} {@link Loggable#setupLogging(DataLogger)}.
     */
    public void setupLoggables() {
        for(Loggable l : loggables) {
            l.setupLogging(this);
        }
    }

    /**
     * Calls each {@link Loggable Loggable's} {@link Loggable#log(DataLogger)}.
     */
    public void logAll() {
        for(Loggable l : loggables) {
            l.log(this);
        }
    }
}
