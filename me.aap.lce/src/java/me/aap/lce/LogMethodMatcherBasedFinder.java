package me.aap.lce;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.text.edits.TextEdit;


/**
 * @author Andrey Pavlenko
 */
public abstract class LogMethodMatcherBasedFinder extends LogFinder {

    public LogMethodMatcherBasedFinder() {
        super();
    }

    public LogMethodMatcherBasedFinder(final boolean defaultReturn) {
        super(defaultReturn);
    }

    public abstract Map getMatchers();

    public boolean visit(final MethodInvocation node, final LogVisitor visitor,
            final List edits) {
        final IMethodBinding b = node.resolveMethodBinding();

        if ((b != null) && Utils.hasComplexArgs(node)) {
            final LogMethodMatcher m = getMatcher(node, b);

            if ((m != null) && isEnabled(m)) {
                final TextEdit e = m.createEdit(node, visitor);

                if (e != null) {
                    edits.add(e);
                }
            }
        }

        return false;
    }

    protected LogMethodMatcher getMatcher(final MethodInvocation node,
            final IMethodBinding b) {
        return (LogMethodMatcher) getMatchers().get(
            new LogMethodMatcher(b.getDeclaringClass().getBinaryName(), b
                    .getName()));
    }

    protected boolean isEnabled(final LogMethodMatcher m) {
        return true;
    }
}
