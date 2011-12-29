package me.aap.lce;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author Andrey Pavlenko
 */
public class LogCleanUpPlugin extends AbstractUIPlugin {
    public static final String PLUGIN_ID = "me.aap.lce"; //$NON-NLS-1$
    private static LogCleanUpPlugin plugin;

    public void start(final BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    public static LogCleanUpPlugin getInstance() {
        return plugin;
    }

    public static void logInfo(final Object msg) {
        logInfo(msg, null);
    }

    public static void logInfo(final Object msg, final Throwable ex) {
        log(IStatus.INFO, msg, ex);
    }

    public static void logErr(final Object msg) {
        logErr(msg, null);
    }

    public static void logErr(final Object msg, final Throwable ex) {
        log(IStatus.ERROR, msg, ex);
    }

    public static void log(final int severity, final Object msg,
            final Throwable ex) {
        final Plugin plugin = getInstance();

        if (plugin != null) {
            plugin.getLog().log(
                new Status(severity, PLUGIN_ID, String.valueOf(msg), ex));
        } else {
            System.err.println(msg);
            if (ex != null) {
                ex.printStackTrace();
            }
        }
    }
}
