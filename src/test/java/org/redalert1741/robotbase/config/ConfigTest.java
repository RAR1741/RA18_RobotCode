package org.redalert1741.robotbase.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.redalert1741.robotbase.config.Config;

public class ConfigTest {
    /**
     * Tests whether {@link Config} can load a file
     */
    @Test
    void configLoadTest() {
        Config config = new Config();
        boolean success = config.loadFromFile(getClass().getResource("test_config.txt").getPath());
        assertTrue(success, "Could not open test_config.txt");
    }

    /**
     * Tests whether {@link Config} values are parsed and returned as the correct types
     */
    @Test
    void configTypeTest() {
        Config config = new Config();
        config.loadFromFile(getClass().getResource("test_config.txt").getPath());
        assertEquals("string", config.getSetting("string_value", "wrong string"));
        assertEquals(5.5, config.getSetting("double_value", 1.0), 0.0001);
        assertEquals(true, config.getSetting("boolean_value", false));
    }

    /**
     * Tests whether comments are ignored in {@link Config}
     */
    @Test
    void configCommentTest() {
        Config config = new Config();
        config.loadFromFile(getClass().getResource("test_config.txt").getPath());
        assertEquals(20.0, config.getSetting("comment_value", 30.0), 0.0001);
    }

    /**
     * Tests whether default values are properly returned for nonexistant {@link Config} values
     */
    @Test
    void configDefaultValueTest() {
        Config config = new Config();
        config.loadFromFile(getClass().getResource("test_config.txt").getPath());
        assertEquals("default value", config.getSetting("nonexistant_value", "default value"));
    }

    /**
     * Tests that {@link Configurable Configurables} are called after {@link Config#reloadConfig()}
     */
    @Test
    void configurableTest() {
        Config config = new Config();
        boolean[] success = {false};
        config.addConfigurable(()->{
            assertEquals(5.5, config.getSetting("double_value", 1.0), 0.0001);
            success[0]=true;
        });
        config.loadFromFile(getClass().getResource("test_config.txt").getPath());
        assertFalse(success[0]);
        config.reloadConfig();
        assertTrue(success[0]);
    }
}
