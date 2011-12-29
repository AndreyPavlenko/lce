import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class Log4jTemplates {

    private static final Logger log = Logger.getLogger("SampleLogger");

    public static Logger getLogger() {
        return log;
    }

    private static String str(String s) {
        return s;
    }

    private void f() {
        final Logger log1 = Logger.getLogger("SampleLogger");
        final Throwable ex = new Throwable();

        log.trace("Simple message");
        log.trace("Complex message: " + this);
        log.trace("Simple message", ex);
        log.trace("Simple message", new Throwable());
        getLogger().trace("Simple message", new Throwable());
        log.trace(str("Complex message"));

        log.debug("Simple message");
        log.debug("Complex message: " + this);
        log.debug("Simple message", ex);
        log.debug("Simple message", new Throwable());

        log.info("Simple message");
        log.info("Complex message: " + this);
        log.info("Simple message", ex);
        log.info("Simple message", new Throwable());

        log.warn("Simple message");
        log.warn("Complex message: " + this);
        log.warn("Simple message", ex);
        log.warn("Simple message", new Throwable());

        log.error("Simple message");
        log.error("Complex message: " + this);
        log.error("Simple message", ex);
        log.error("Simple message", new Throwable());

        log.fatal("Simple message");
        log.fatal("Complex message: " + this);
        log.fatal("Simple message", ex);
        log.fatal("Simple message", new Throwable());

        if (log.isTraceEnabled()) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isTraceEnabled()) {
            if (log.isDebugEnabled()) {
                log.debug("Complex message: " + this);
            }
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log1.isTraceEnabled()) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isDebugEnabled()) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isInfoEnabled()) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Level.TRACE)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Level.TRACE)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(org.apache.log4j.Level.TRACE)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Level.DEBUG)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(org.apache.log4j.Level.DEBUG)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Priority.DEBUG)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(org.apache.log4j.Priority.DEBUG)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Level.INFO)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(org.apache.log4j.Level.INFO)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Priority.INFO)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(org.apache.log4j.Priority.INFO)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Level.WARN)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(org.apache.log4j.Level.WARN)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Priority.WARN)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(org.apache.log4j.Priority.WARN)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Level.ERROR)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(org.apache.log4j.Level.ERROR)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Priority.ERROR)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(org.apache.log4j.Priority.ERROR)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Level.FATAL)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(org.apache.log4j.Level.FATAL)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(Priority.FATAL)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }

        if (log.isEnabledFor(org.apache.log4j.Priority.FATAL)) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }
    }
}