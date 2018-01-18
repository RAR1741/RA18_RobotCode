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
        boolean success = Config.loadFromFile(getClass().getResource("test_config.txt").getPath());
        assertTrue(success, "Could not open test_config.txt");
    }

    /**
     * Tests whether {@link Config} values are parsed and returned as the correct types
     */
    @Test
    void configTypeTest() {
        Config.loadFromFile(getClass().getResource("test_config.txt").getPath());
        assertEquals("string", Config.getSetting("string_value", "wrong string"));
        assertEquals(5.5, Config.getSetting("double_value", 1.0));
        assertEquals(true, Config.getSetting("boolean_value", false));
    }

    /**
     * Tests whether comments are ignored in {@link Config}
     */
    @Test
    void configCommentTest() {
        Config.loadFromFile(getClass().getResource("test_config.txt").getPath());
        assertEquals(20, Config.getSetting("comment_value", 30));
    }

    /**
     * Tests whether default values are properly returned for nonexistant {@link Config} values
     */
    @Test
    void configDefaultValueTest() {
        Config.loadFromFile(getClass().getResource("test_config.txt").getPath());
        assertEquals("default value", Config.getSetting("nonexistant_value", "default value"));
    }

    /**
     * Tests that {@link Configurable Configurables} are called after {@link Config#reloadConfig()}
     */
    @Test
    void configurableTest() {
        boolean[] success = {false};
        Config.addConfigurable(()->{
            assertEquals(5.5, Config.getSetting("double_value", 1.0));
            success[0]=true;
        });
        Config.loadFromFile(getClass().getResource("test_config.txt").getPath());
        assertFalse(success[0]);
        Config.reloadConfig();
        assertTrue(success[0]);
    }
}
