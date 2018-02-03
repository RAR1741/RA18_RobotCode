package org.redalert1741.robotbase.config;

/**
 * An object that can be configured.
 * Must be added to a {@link Config} by
 * using {@link Config#addConfigurable(Configurable)}.
 */
public interface Configurable {
    /**
     * Called when the {@link Config} is reloaded.
     * Values can be retrieved using {@link Config#getSetting(String, Object)}.
     * @param config config to get values from
     */
    public abstract void reloadConfig(Config config);
}
