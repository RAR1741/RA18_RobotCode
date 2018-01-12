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
}
