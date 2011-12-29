package me.aap.lce;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.TextEdit;


/**
 * @author Andrey Pavlenko
 */
public class LogMethodMatcher {
    private final String className;
    private final String methodName;
    private final MessageFormat insertEditExpr;
    private final Object marker;
    private final int hash;
    private Map exceptions;
    private Map imports;

    LogMethodMatcher(final String className, final String methodName) {
        this(className, methodName, (MessageFormat) null, null);
    }

    public LogMethodMatcher(final String className, final String methodName,
            final String insertEditExpr, final Object marker) {
        this(className, methodName, new MessageFormat(insertEditExpr), marker);
    }

    public LogMethodMatcher(final String className, final String methodName,
            final MessageFormat insertEditExpr, final Object marker) {
        this.className = className;
        this.methodName = methodName;
        this.insertEditExpr = insertEditExpr;
        this.marker = marker;
        hash = className.hashCode() + methodName.hashCode();
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public MessageFormat getInsertEditExpr() {
        return insertEditExpr;
    }

    public MessageFormat getInsertEditExprFor(final MethodInvocation node,
            final LogVisitor visitor) {
        return getInsertEditExpr();
    }

    public Object getMarker() {
        return marker;
    }

    public void addMatchException(final String className,
            final String methodName, final String[] args) {
        if (exceptions == null) {
            exceptions = new HashMap();
        }
        exceptions.put(new MatchException(className, methodName, args), "");
    }

    public void addImport(final String packageName, final String className) {
        if (imports == null) {
            imports = new HashMap();
        }
        imports.put(packageName, className);
    }

    public int hashCode() {
        return hash;
    }

    public boolean equals(final Object obj) {
        if (obj instanceof LogMethodMatcher) {
            final LogMethodMatcher m = (LogMethodMatcher) obj;
            return getClassName().equals(m.getClassName())
                    && getMethodName().equals(m.getMethodName());
        }
        return super.equals(obj);
    }

    public TextEdit createEdit(final MethodInvocation node,
            final LogVisitor visitor) {
        final String obj = LogVisitor.getObject(node);

        if ((obj != null) && matches(obj, node, visitor)) {
            if (imports != null) {
                for (final Iterator it = imports.entrySet().iterator(); it
                        .hasNext();) {
                    final Map.Entry e = (Map.Entry) it.next();
                    visitor.addImport((String) e.getKey(),
                        (String) e.getValue());
                }
            }
            return new InsertEdit(
                    node.getStartPosition(),
                    getInsertEditExprFor(node, visitor).format(
                        new Object[] { obj }));
        }

        return null;
    }

    protected boolean matches(final String obj, final MethodInvocation node,
            final LogVisitor visitor) {
        final List l = visitor.getIfExprMethodInvocations();
        final int size = l.size();

        for (int i = 0; i < size; i++) {
            final MethodInvocation m = (MethodInvocation) l.get(i);
            final IMethodBinding b = m.resolveMethodBinding();

            if ((b != null)
                    && obj.equals(LogVisitor.getObject(m))
                    && hasException(b.getDeclaringClass().getBinaryName(),
                        b.getName(), Utils.getArgs(m))) {
                return false;
            }
        }

        return true;
    }

    protected boolean hasException(final String className,
            final String methodName, final String[] args) {
        return (exceptions != null)
                && exceptions.containsKey(new MatchException(
                        className, methodName, args));
    }

    private final static class MatchException {
        private final String className;
        private final String methodName;
        private final String[] args;
        private final int hash;

        public MatchException(final String className, final String methodName,
                final String[] args) {
            this.className = className;
            this.methodName = methodName;
            this.args = (args == null) || (args.length == 0) ? Utils.EMPTY_STRING_ARRAY
                    : args;
            hash = className.hashCode() + methodName.hashCode()
                    + arrayHash(this.args);
        }

        public boolean equals(final Object obj) {
            final MatchException ex = (MatchException) obj;
            return className.equals(ex.className)
                    && methodName.equals(ex.methodName)
                    && Arrays.equals(args, ex.args);
        }

        public int hashCode() {
            return hash;
        }

        private static int arrayHash(final String[] a) {
            int h = Utils.EMPTY_STRING_ARRAY.hashCode();
            for (int i = 0; i < a.length; i++) {
                h += (a[i] == null) ? 0 : a[i].hashCode();
            }
            return h;
        }
    }
}
