package me.aap.lce.commonslogging;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.MethodInvocation;

import me.aap.lce.LogMethodMatcher;
import me.aap.lce.LogMethodMatcherBasedFinder;
import me.aap.lce.LogVisitor;
import me.aap.lce.LogFinderFactory.Option;


public class CommonsLoggingFinder extends LogMethodMatcherBasedFinder {
    private static final Map/* <CommonsLoggingMethodMatcher, CommonsLoggingMethodMatcher> */matchers = new HashMap(
            6);
    private final Option[] options;
    private final CommonsLoggingFinderFactory factory;

    static {
        // Trace
        CommonsLoggingMethodMatcher m = new CommonsLoggingMethodMatcher(
                "org.apache.commons.logging.Log", "trace",
                "if ({0}.isTraceEnabled()) ", new Integer(0));
        m.addMatchException("org.apache.commons.logging.Log", "isTraceEnabled", null);
        matchers.put(m, m);

        // Debug
        m = new CommonsLoggingMethodMatcher(
                "org.apache.commons.logging.Log", "debug",
                "if ({0}.isDebugEnabled()) ", new Integer(1));
        m.addMatchException("org.apache.commons.logging.Log", "isDebugEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isTraceEnabled", null);
        matchers.put(m, m);

        // Info
        m = new CommonsLoggingMethodMatcher(
                "org.apache.commons.logging.Log", "info",
                "if ({0}.isInfoEnabled()) ", new Integer(2));
        m.addMatchException("org.apache.commons.logging.Log", "isInfoEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isDebugEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isTraceEnabled", null);
        matchers.put(m, m);

        // Warning
        m = new CommonsLoggingMethodMatcher(
                "org.apache.commons.logging.Log", "warn",
                "if ({0}.isWarnEnabled()) ", new Integer(3));
        m.addMatchException("org.apache.commons.logging.Log", "isWarnEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isInfoEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isDebugEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isTraceEnabled", null);
        matchers.put(m, m);

        // Error
        m = new CommonsLoggingMethodMatcher(
                "org.apache.commons.logging.Log", "error",
                "if ({0}.isErrorEnabled()) ", new Integer(4));
        m.addMatchException("org.apache.commons.logging.Log", "isErrorEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isWarnEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isInfoEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isDebugEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isTraceEnabled", null);
        matchers.put(m, m);

        // Fatal
        m = new CommonsLoggingMethodMatcher(
                "org.apache.commons.logging.Log", "fatal",
                "if ({0}.isFatalEnabled()) ", new Integer(5));
        m.addMatchException("org.apache.commons.logging.Log", "isFatalEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isErrorEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isWarnEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isInfoEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isDebugEnabled", null);
        m.addMatchException("org.apache.commons.logging.Log", "isTraceEnabled", null);
        matchers.put(m, m);
    }

    public CommonsLoggingFinder(final CommonsLoggingFinderFactory factory) {
        super(false);
        final Option[] options = new Option[6];

        options[0] = factory.getOption("TRACE");
        options[1] = factory.getOption("DEBUG");
        options[2] = factory.getOption("INFO");
        options[3] = factory.getOption("WARN");
        options[4] = factory.getOption("ERROR");
        options[5] = factory.getOption("FATAL");
        this.options = options;
        this.factory = factory;
    }

    public Map getMatchers() {
        return matchers;
    }

    public CommonsLoggingFinderFactory getFactory() {
        return factory;
    }

    protected boolean isEnabled(final LogMethodMatcher m) {
        if (getFactory().isEnabled()) {
            final Option o = options[((Integer) m.getMarker()).intValue()];
            return (o != null) && o.isEnabled();
        }
        return false;
    }

    private static final class CommonsLoggingMethodMatcher extends LogMethodMatcher {
        public CommonsLoggingMethodMatcher(final String className,
                final String methodName, final String insertEditExpr,
                final Object marker) {
            super(className, methodName, insertEditExpr, marker);
        }

        public MessageFormat getInsertEditExprFor(final MethodInvocation node,
                final LogVisitor visitor) {
            return super.getInsertEditExprFor(node, visitor);
        }
    }
}
