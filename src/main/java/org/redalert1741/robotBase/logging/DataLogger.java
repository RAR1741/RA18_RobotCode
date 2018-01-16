package org.redalert1741.robotBase.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;


public class DataLogger {
    private String filename;
    private FileWriter log = null;
    private Map<String, String> fields;
    private List<Loggable> loggables;
    private NetworkTable table;

    public DataLogger() {
        fields = new LinkedHashMap<String,String>();
        loggables = new ArrayList<>();
        table = NetworkTableInstance.getDefault().getTable("logging");
        for(String s : table.getKeys()) {
            table.delete(s);
        }
    }

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

    public boolean reset() {
        close();
        open(this.filename);
        writeAttributes();
        return true;
    }

    public boolean hasAttribute(String name) {
        return fields.containsKey(name);
    }

    public boolean addAttribute(String field) {
        if (hasAttribute(field)) {
            // TODO: Output warning
            return false; // We already have this attribute
        }

        fields.put(field, "");

        return true;
    }

    public boolean log(String field, double d) {
        table.getEntry(field).setDouble(d);
        return log(field, String.valueOf(d));
    }

    public boolean log(String field, String data) {
        if(!hasAttribute(field)) { return false; }

        fields.put(field, data);
        return true;
    }

    public boolean log(String field, Object data) {
        if(!hasAttribute(field)) { return false; }

        fields.put(field, data.toString());
        return true;
    }

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

    public void addLoggable(Loggable l) {
        loggables.add(l);
    }

    public void setupLoggables() {
        for(Loggable l : loggables) {
            l.setupLogging(this);
        }
    }

    public void log() {
        for(Loggable l : loggables) {
            l.log(this);
        }
    }
}
