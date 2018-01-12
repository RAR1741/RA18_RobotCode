package org.redalert1741.robotbase.config;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.redalert1741.robotBase.config.Config;

public class ConfigTest
{
	@Test
    void configLoadTest() {
		boolean success = Config.loadFromFile(getClass().getResource("test_config.txt").getPath());
		assertTrue(success, "Could not open test_config.txt");
    }
	
	@Test
    void configTypeTest() {
		Config.loadFromFile(getClass().getResource("test_config.txt").getPath());
		assertEquals("string", Config.getSetting("string_value", "wrong string"));
		assertEquals(5.5, Config.getSetting("double_value", 1.0));
		assertEquals(true, Config.getSetting("boolean_value", false));
    }
	
	@Test
    void configCommentTest() {
		Config.loadFromFile(getClass().getResource("test_config.txt").getPath());
		assertEquals(20, Config.getSetting("comment_value", 30));
    }
	
	@Test
    void configDefaultValueTest() {
		Config.loadFromFile(getClass().getResource("test_config.txt").getPath());
		assertEquals("default value", Config.getSetting("nonexistant_value", "default value"));
    }
}
