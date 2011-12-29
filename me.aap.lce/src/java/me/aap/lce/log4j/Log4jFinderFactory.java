package me.aap.lce.log4j;

import org.eclipse.core.runtime.IConfigurationElement;

import me.aap.lce.LogFinder;
import me.aap.lce.LogFinderFactory;

/**
 * @author Andrey Pavlenko
 */
public class Log4jFinderFactory extends LogFinderFactory {
    private volatile Log4jFinder finder;

    public LogFinder getLogFinder() {
        return finder;
    }

    public void setInitializationData(final IConfigurationElement config,
            final String propertyName, final Object data) {
        super.setInitializationData(config, propertyName, data);
        finder = new Log4jFinder(this);
    }
}
