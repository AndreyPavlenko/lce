import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonsLoggingTemplates {

    private static final Log log = LoggerFactory.getLog("SampleLogger");

    public static Log getLogger() {
        return log;
    }

    private static String str(String s) {
        return s;
    }

    private void f() {
        final Log log1 = LoggerFactory.getLog("SampleLogger");
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

        if (log.isFatalEnabled()) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
            log.fatal("Complex message: " + this);
        }
    }
}