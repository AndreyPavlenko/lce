package me.aap.lce.jul;

import org.eclipse.core.runtime.IConfigurationElement;

import me.aap.lce.LogFinder;
import me.aap.lce.LogFinderFactory;

/**
 * @author Andrey Pavlenko
 */
public class JulFinderFactory extends LogFinderFactory {
    private volatile JulFinder finder;

    public LogFinder getLogFinder() {
        return finder;
    }

    public void setInitializationData(final IConfigurationElement config,
            final String propertyName, final Object data) {
        super.setInitializationData(config, propertyName, data);
        finder = new JulFinder(this);
    }
}
