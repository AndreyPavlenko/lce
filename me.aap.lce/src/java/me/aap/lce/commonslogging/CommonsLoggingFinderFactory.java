package me.aap.lce.commonslogging;

import org.eclipse.core.runtime.IConfigurationElement;

import me.aap.lce.LogFinder;
import me.aap.lce.LogFinderFactory;

public class CommonsLoggingFinderFactory extends LogFinderFactory {
    private volatile CommonsLoggingFinder finder;

    public LogFinder getLogFinder() {
        return finder;
    }

    public void setInitializationData(final IConfigurationElement config,
            final String propertyName, final Object data) {
        super.setInitializationData(config, propertyName, data);
        finder = new CommonsLoggingFinder(this);
    }
}
