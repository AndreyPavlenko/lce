import java.util.logging.Level;
import java.util.logging.Logger;

public class JulTemplate {
    private static final Logger log = Logger.getLogger("SampleLogger");

    public static Logger getLogger() {
        return log;
    }

    public void f() {
        Throwable ex = new Throwable();

        log.finest("Simple message");
        log.finest("Complex message: " + this);

        log.finer("Simple message");
        log.finer("Complex message: " + this);

        log.fine("Simple message");
        log.fine("Complex message: " + this);

        log.config("Simple message");
        log.config("Complex message: " + this);

        log.info("Simple message");
        log.info("Complex message: " + this);

        log.warning("Simple message");
        log.warning("Complex message: " + this);

        log.severe("Simple message"); 
        log.severe("Complex message: " + this);

        log.log(Level.FINEST, "Simple message");
        log.log(Level.FINEST, "Complex message: " + this);
        log.log(Level.FINEST, "Simple message", ex);
        log.log(Level.FINEST, "Simple message", new Throwable());
        getLogger().log(Level.FINEST, "Simple message", new Throwable());

        log.log(Level.FINER, "Simple message");
        log.log(Level.FINER, "Complex message: " + this);
        log.log(Level.FINER, "Simple message", ex);
        log.log(Level.FINER, "Simple message", new Throwable());

        log.log(Level.FINE, "Simple message");
        log.log(Level.FINE, "Complex message: " + this);
        log.log(Level.FINE, "Simple message", ex);
        log.log(Level.FINE, "Simple message", new Throwable());

        log.log(Level.CONFIG, "Simple message");
        log.log(Level.CONFIG, "Complex message: " + this);
        log.log(Level.CONFIG, "Simple message", ex);
        log.log(Level.CONFIG, "Simple message", new Throwable());

        log.log(Level.INFO, "Simple message");
        log.log(Level.INFO, "Complex message: " + this);
        log.log(Level.INFO, "Simple message", ex);
        log.log(Level.INFO, "Simple message", new Throwable());

        log.log(Level.WARNING, "Simple message");
        log.log(Level.WARNING, "Complex message: " + this);
        log.log(Level.WARNING, "Simple message", ex);
        log.log(Level.WARNING, "Simple message", new Throwable());

        log.log(Level.SEVERE, "Simple message");
        log.log(Level.SEVERE, "Complex message: " + this);
        log.log(Level.SEVERE, "Simple message", ex);
        log.log(Level.SEVERE, "Simple message", new Throwable());

        if (log.isLoggable(Level.FINEST)) {
            log.finest("Complex message: " + this);
            log.finer("Complex message: " + this);
            log.fine("Complex message: " + this);
            log.config("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warning("Complex message: " + this);
            log.severe("Complex message: " + this);
            log.log(Level.FINEST, "Complex message: " + this);
            log.log(Level.FINER, "Complex message: " + this);
            log.log(Level.FINE, "Complex message: " + this);
            log.log(Level.CONFIG, "Complex message: " + this);
            log.log(Level.INFO, "Complex message: " + this);
            log.log(Level.WARNING, "Complex message: " + this);
            log.log(Level.SEVERE, "Complex message: " + this);
        }

        if (log.isLoggable(Level.FINER)) {
            log.finest("Complex message: " + this);
            log.finer("Complex message: " + this);
            log.fine("Complex message: " + this);
            log.config("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warning("Complex message: " + this);
            log.severe("Complex message: " + this);
            log.log(Level.FINEST, "Complex message: " + this);
            log.log(Level.FINER, "Complex message: " + this);
            log.log(Level.FINE, "Complex message: " + this);
            log.log(Level.CONFIG, "Complex message: " + this);
            log.log(Level.INFO, "Complex message: " + this);
            log.log(Level.WARNING, "Complex message: " + this);
            log.log(Level.SEVERE, "Complex message: " + this);
        }

        if (log.isLoggable(Level.FINE)) {
            log.finest("Complex message: " + this);
            log.finer("Complex message: " + this);
            log.fine("Complex message: " + this);
            log.config("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warning("Complex message: " + this);
            log.severe("Complex message: " + this);
            log.log(Level.FINEST, "Complex message: " + this);
            log.log(Level.FINER, "Complex message: " + this);
            log.log(Level.FINE, "Complex message: " + this);
            log.log(Level.CONFIG, "Complex message: " + this);
            log.log(Level.INFO, "Complex message: " + this);
            log.log(Level.WARNING, "Complex message: " + this);
            log.log(Level.SEVERE, "Complex message: " + this);
        }

        if (log.isLoggable(Level.CONFIG)) {
            log.finest("Complex message: " + this);
            log.finer("Complex message: " + this);
            log.fine("Complex message: " + this);
            log.config("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warning("Complex message: " + this);
            log.severe("Complex message: " + this);
            log.log(Level.FINEST, "Complex message: " + this);
            log.log(Level.FINER, "Complex message: " + this);
            log.log(Level.FINE, "Complex message: " + this);
            log.log(Level.CONFIG, "Complex message: " + this);
            log.log(Level.INFO, "Complex message: " + this);
            log.log(Level.WARNING, "Complex message: " + this);
            log.log(Level.SEVERE, "Complex message: " + this);
        }

        if (log.isLoggable(Level.INFO)) {
            log.finest("Complex message: " + this);
            log.finer("Complex message: " + this);
            log.fine("Complex message: " + this);
            log.config("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warning("Complex message: " + this);
            log.severe("Complex message: " + this);
            log.log(Level.FINEST, "Complex message: " + this);
            log.log(Level.FINER, "Complex message: " + this);
            log.log(Level.FINE, "Complex message: " + this);
            log.log(Level.CONFIG, "Complex message: " + this);
            log.log(Level.INFO, "Complex message: " + this);
            log.log(Level.WARNING, "Complex message: " + this);
            log.log(Level.SEVERE, "Complex message: " + this);
        }

        if (log.isLoggable(Level.WARNING)) {
            log.finest("Complex message: " + this);
            log.finer("Complex message: " + this);
            log.fine("Complex message: " + this);
            log.config("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warning("Complex message: " + this);
            log.severe("Complex message: " + this);
            log.log(Level.FINEST, "Complex message: " + this);
            log.log(Level.FINER, "Complex message: " + this);
            log.log(Level.FINE, "Complex message: " + this);
            log.log(Level.CONFIG, "Complex message: " + this);
            log.log(Level.INFO, "Complex message: " + this);
            log.log(Level.WARNING, "Complex message: " + this);
            log.log(Level.SEVERE, "Complex message: " + this);
        }

        if (log.isLoggable(Level.SEVERE)) {
            log.finest("Complex message: " + this);
            log.finer("Complex message: " + this);
            log.fine("Complex message: " + this);
            log.config("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warning("Complex message: " + this);
            log.severe("Complex message: " + this);
            log.log(Level.FINEST, "Complex message: " + this);
            log.log(Level.FINER, "Complex message: " + this);
            log.log(Level.FINE, "Complex message: " + this);
            log.log(Level.CONFIG, "Complex message: " + this);
            log.log(Level.INFO, "Complex message: " + this);
            log.log(Level.WARNING, "Complex message: " + this);
            log.log(Level.SEVERE, "Complex message: " + this);
        }
    }
}
