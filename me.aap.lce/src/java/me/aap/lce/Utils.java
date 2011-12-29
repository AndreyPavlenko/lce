package me.aap.lce;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 * @author Andrey Pavlenko
 */
public class Utils {
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final String LINE_SEPARATOR;

    static {
        String s;

        try {
            s = System.getProperty("line.separator");
        } catch (final SecurityException ex) {
            s = "\n";
        }

        LINE_SEPARATOR = s;
    }

    public static boolean hasComplexArgs(final MethodInvocation node) {
        for (final Iterator it = node.arguments().iterator(); it.hasNext();) {
            final Object o = it.next();
            if ((o instanceof InfixExpression)
                    || (o instanceof ClassInstanceCreation)
                    || (o instanceof MethodInvocation)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getArgs(final MethodInvocation node) {
        final List args = node.arguments();
        int i = args.size();

        if (i != 0) {
            final String[] a = new String[i];
            i = 0;
            for (final Iterator it = args.iterator(); it.hasNext(); i++) {
                a[i] = it.next().toString();
            }
            return a;
        }

        return Utils.EMPTY_STRING_ARRAY;
    }

    public static int arrayHash(final Object[] a) {
        int h = a.hashCode();
        for (int i = 0; i < a.length; i++) {
            h += (a[i] == null) ? 0 : a[i].hashCode();
        }
        return h;
    }
}
