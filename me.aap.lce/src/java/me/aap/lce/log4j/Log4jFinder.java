package me.aap.lce.log4j;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.MethodInvocation;

import me.aap.lce.LogMethodMatcher;
import me.aap.lce.LogMethodMatcherBasedFinder;
import me.aap.lce.LogVisitor;
import me.aap.lce.LogFinderFactory.Option;

/**
 * @author Andrey Pavlenko
 */
public class Log4jFinder extends LogMethodMatcherBasedFinder {
    private static final Map/* <Log4jMethodMatcher, Log4jMethodMatcher> */matchers = new HashMap(
            6);
    private final Option[] options;
    private final Log4jFinderFactory factory;

    static {
        // Trace
        Log4jMethodMatcher m = new Log4jMethodMatcher(
                "org.apache.log4j.Logger", "trace",
                "if ({0}.isTraceEnabled()) ", new Integer(0));
        m.addMatchException("org.apache.log4j.Logger", "isTraceEnabled", null);
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.TRACE" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.TRACE" });
        matchers.put(m, m);

        // Debug
        m = new Log4jMethodMatcher(
                "org.apache.log4j.Category", "debug",
                "if ({0}.isDebugEnabled()) ", new Integer(1));
        m.addMatchException("org.apache.log4j.Category", "isDebugEnabled", null);
        m.addMatchException("org.apache.log4j.Logger", "isTraceEnabled", null);
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.TRACE" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.TRACE" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.DEBUG" });
        matchers.put(m, m);

        // Info
        m = new Log4jMethodMatcher(
                "org.apache.log4j.Category", "info",
                "if ({0}.isInfoEnabled()) ", new Integer(2));
        m.addMatchException("org.apache.log4j.Category", "isInfoEnabled", null);
        m.addMatchException("org.apache.log4j.Category", "isDebugEnabled", null);
        m.addMatchException("org.apache.log4j.Logger", "isTraceEnabled", null);
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.TRACE" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.TRACE" });
        matchers.put(m, m);

        // Warning
        m = new Log4jMethodMatcher(
                "org.apache.log4j.Category", "warn",
                "if ({0}.isEnabledFor(Level.WARN)) ", new Integer(3));
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isInfoEnabled", null);
        m.addMatchException("org.apache.log4j.Category", "isDebugEnabled", null);
        m.addMatchException("org.apache.log4j.Logger", "isTraceEnabled", null);
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.TRACE" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.TRACE" });
        matchers.put(m, m);

        // Error
        m = new Log4jMethodMatcher(
                "org.apache.log4j.Category", "error",
                "if ({0}.isEnabledFor(Level.ERROR)) ", new Integer(4));
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.ERROR" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.ERROR" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.ERROR" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.ERROR" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isInfoEnabled", null);
        m.addMatchException("org.apache.log4j.Category", "isDebugEnabled", null);
        m.addMatchException("org.apache.log4j.Logger", "isTraceEnabled", null);
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.TRACE" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.TRACE" });
        matchers.put(m, m);

        // Fatal
        m = new Log4jMethodMatcher(
                "org.apache.log4j.Category", "fatal",
                "if ({0}.isEnabledFor(Level.FATAL)) ", new Integer(5));
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.FATAL" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.FATAL" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.FATAL" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.FATAL" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.ERROR" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.ERROR" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.ERROR" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.ERROR" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.WARN" });
        m.addMatchException("org.apache.log4j.Category", "isInfoEnabled", null);
        m.addMatchException("org.apache.log4j.Category", "isDebugEnabled", null);
        m.addMatchException("org.apache.log4j.Logger", "isTraceEnabled", null);
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.INFO" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Priority.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Priority.DEBUG" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "Level.TRACE" });
        m.addMatchException("org.apache.log4j.Category", "isEnabledFor",
            new String[] { "org.apache.log4j.Level.TRACE" });
        matchers.put(m, m);
    }

    public Log4jFinder(final Log4jFinderFactory factory) {
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

    public Log4jFinderFactory getFactory() {
        return factory;
    }

    protected boolean isEnabled(final LogMethodMatcher m) {
        if (getFactory().isEnabled()) {
            final Option o = options[((Integer) m.getMarker()).intValue()];
            return (o != null) && o.isEnabled();
        }
        return false;
    }

    private static final class Log4jMethodMatcher extends LogMethodMatcher {
        private final MessageFormat noimportEditExpr;

        public Log4jMethodMatcher(final String className,
                final String methodName, final String insertEditExpr,
                final Object marker) {
            super(className, methodName, insertEditExpr, marker);

            if ("warn".equals(methodName)) {
                noimportEditExpr = new MessageFormat(
                        "if ({0}.isEnabledFor(org.apache.log4j.Level.WARN)) ");
            } else if ("error".equals(methodName)) {
                noimportEditExpr = new MessageFormat(
                        "if ({0}.isEnabledFor(org.apache.log4j.Level.ERROR)) ");
            } else if ("fatal".equals(methodName)) {
                noimportEditExpr = new MessageFormat(
                        "if ({0}.isEnabledFor(org.apache.log4j.Level.FATAL)) ");
            } else {
                noimportEditExpr = null;
            }
        }

        public MessageFormat getInsertEditExprFor(final MethodInvocation node,
                final LogVisitor visitor) {
            if (noimportEditExpr != null) {
                if (visitor.hasSimpleNameImport(".Level",
                    "org.apache.log4j.Level")) {
                    return noimportEditExpr;
                }
                visitor.addImport("org.apache.log4j", "org.apache.log4j.Level");
            }
            return super.getInsertEditExprFor(node, visitor);
        }
    }
}
