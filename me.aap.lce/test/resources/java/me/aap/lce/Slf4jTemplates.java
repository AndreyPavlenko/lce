import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTemplates {

    private void f() {
        final Logger log = LoggerFactory.getLogger("SampleLogger");

        log.trace("Simple message");
        log.trace("Complex message: " + this);

        log.debug("Simple message");
        log.debug("Complex message: " + this);

        log.info("Simple message");
        log.info("Complex message: " + this);

        log.warn("Simple message");
        log.warn("Complex message: " + this);

        log.error("Simple message");
        log.error("Complex message: " + this);

        if (log.isTraceEnabled()) { 
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
        }

        if (log.isDebugEnabled()) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
        }

        if (log.isInfoEnabled()) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
        }

        if (log.isWarnEnabled()) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
        }

        if (log.isErrorEnabled()) {
            log.trace("Complex message: " + this);
            log.debug("Complex message: " + this);
            log.info("Complex message: " + this);
            log.warn("Complex message: " + this);
            log.error("Complex message: " + this);
        }
    }
}