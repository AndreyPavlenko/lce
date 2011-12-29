package me.aap.lce.jul;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;

import me.aap.lce.LogMethodMatcher;
import me.aap.lce.LogMethodMatcherBasedFinder;
import me.aap.lce.LogVisitor;
import me.aap.lce.LogFinderFactory.Option;

/**
 * @author Andrey Pavlenko
 */
public class JulFinder extends LogMethodMatcherBasedFinder {
    private static final Map/* <LogMethodMatcher, LogMethodMatcher> */matchers = new HashMap(
            28);
    private final Option[] options;
    private final JulFinderFactory factory;

    static {
        final String[] logMethods = new String[] { "log", "logp", "logrb" };
        final Level[] levels = new Level[] { Level.FINEST, Level.FINER,
                Level.FINE, Level.CONFIG, Level.INFO, Level.WARNING,
                Level.SEVERE };

        for (int i = 0; i < levels.length; i++) {
            final String name = levels[i].getName();
            final JulMethodMatcher matcher = new JulMethodMatcher(
                    name, new Integer(i));

            for (int n = i; n >= 0; n--) {
                matcher.addMatchException("java.util.logging.Logger",
                    "isLoggable",
                    new String[] { "Level." + levels[n].getName() });
                matcher.addMatchException("java.util.logging.Logger",
                    "isLoggable", new String[] { "java.util.logging.Level."
                            + levels[n].getName() });
            }

            matchers.put(matcher, matcher);
        }

        for (int m = 0; m < logMethods.length; m++) {
            for (int i = 0; i < levels.length; i++) {
                final String name = levels[i].getName();
                final JulLogMethodMatcher matcher = new JulLogMethodMatcher(
                        logMethods[m], name, new Integer(i));

                for (int n = i; n >= 0; n--) {
                    matcher.addMatchException("java.util.logging.Logger",
                        "isLoggable",
                        new String[] { "Level." + levels[n].getName() });
                    matcher.addMatchException("java.util.logging.Logger",
                        "isLoggable", new String[] { "java.util.logging.Level."
                                + levels[n].getName() });
                }

                matchers.put(matcher, matcher);
            }
        }
    }

    public JulFinder(final JulFinderFactory factory) {
        super(false);
        final Option[] options = new Option[7];

        options[0] = factory.getOption("FINEST");
        options[1] = factory.getOption("FINER");
        options[2] = factory.getOption("FINE");
        options[3] = factory.getOption("CONFIG");
        options[4] = factory.getOption("INFO");
        options[5] = factory.getOption("WARNING");
        options[6] = factory.getOption("SEVERE");
        this.options = options;
        this.factory = factory;
    }

    public Map getMatchers() {
        return matchers;
    }

    public JulFinderFactory getFactory() {
        return factory;
    }

    protected LogMethodMatcher getMatcher(final MethodInvocation node,
            final IMethodBinding b) {
        if ("java.util.logging.Logger".equals(b.getDeclaringClass()
                .getBinaryName())) {
            final ITypeBinding[] t = b.getParameterTypes();

            if (t.length == 0) {
                return null;
            }

            if ((t.length > 1)
                    && ("java.util.logging.Level".equals(t[0].getBinaryName()))) {
                final Iterator it = node.arguments().iterator();

                if (it.hasNext()) {
                    final Object arg = it.next();

                    if (arg instanceof QualifiedName) {
                        return (LogMethodMatcher) getMatchers().get(
                            new JulLogMethodMatcher(
                                    b.getName(), ((QualifiedName) arg)
                                            .getName().getIdentifier()));
                    }
                }

                return null;
            }

            return (LogMethodMatcher) getMatchers().get(
                new JulMethodMatcher(b.getName()));
        }

        return null;
    }

    public boolean isEnabled(final LogMethodMatcher m) {
        if (getFactory().isEnabled()) {
            final Option o = options[((Integer) m.getMarker()).intValue()];
            return (o != null) && o.isEnabled();
        }
        return false;
    }

    private static final class JulMethodMatcher extends LogMethodMatcher {
        private final MessageFormat noimportEditExpr;

        JulMethodMatcher(final String methodName) {
            super("java.util.logging.Logger", methodName, (MessageFormat) null,
                    null);
            noimportEditExpr = null;
        }

        JulMethodMatcher(final String level, final Object marker) {
            super("java.util.logging.Logger", level.toLowerCase(),
                    "if ({0}.isLoggable(Level." + level + ")) ", marker);
            noimportEditExpr = new MessageFormat(
                    "if ({0}.isEnabledFor(org.apache.log4j.Level." + level
                            + ")) ");
        }

        public MessageFormat getInsertEditExprFor(final MethodInvocation node,
                final LogVisitor visitor) {
            if (visitor
                    .hasSimpleNameImport(".Level", "java.util.logging.Level")) {
                return noimportEditExpr;
            }
            return super.getInsertEditExprFor(node, visitor);
        }
    }

    private static final class JulLogMethodMatcher extends LogMethodMatcher {
        private final String level;
        private final int hash;
        private final MessageFormat noimportEditExpr;

        JulLogMethodMatcher(final String methodName, final String level) {
            super("java.util.logging.Logger", methodName, (MessageFormat) null,
                    null);
            this.level = level;
            noimportEditExpr = null;
            hash = super.hashCode() + level.hashCode();
        }

        JulLogMethodMatcher(final String methodName, final String level,
                final Object marker) {
            super("java.util.logging.Logger", methodName,
                    "if ({0}.isLoggable(Level." + level + ")) ", marker);
            this.level = level;
            noimportEditExpr = new MessageFormat(
                    "if ({0}.isLoggable(java.util.logging.Level." + level
                            + ")) ");
            hash = super.hashCode() + level.hashCode();
        }

        public MessageFormat getInsertEditExprFor(final MethodInvocation node,
                final LogVisitor visitor) {
            if (visitor
                    .hasSimpleNameImport(".Level", "java.util.logging.Level")) {
                return noimportEditExpr;
            }

            final Iterator it = node.arguments().iterator();

            if (it.hasNext()
                    && it.next().toString()
                            .startsWith("java.util.logging.Level.")) {
                return noimportEditExpr;
            }

            visitor.addImport("java.util.logging", "java.util.logging.Level");
            return super.getInsertEditExprFor(node, visitor);
        }

        public int hashCode() {
            return hash;
        }

        public boolean equals(final Object obj) {
            if ((obj instanceof JulLogMethodMatcher) && super.equals(obj)) {
                return level.equals(((JulLogMethodMatcher) obj).level);
            }
            return false;
        }
    }
}
