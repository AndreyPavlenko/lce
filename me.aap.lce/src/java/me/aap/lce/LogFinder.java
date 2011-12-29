package me.aap.lce;

import java.util.List;

import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * @author Andrey Pavlenko
 */
public class LogFinder {
    private final boolean defaultReturn;

    public LogFinder() {
        this(true);
    }

    public LogFinder(final boolean defaultReturn) {
        this.defaultReturn = defaultReturn;
    }

    public boolean getDefaultReturn() {
        return defaultReturn;
    }

    public boolean visit(final PackageDeclaration node,
            final LogVisitor visitor, final List/* <TextEdit> */edits) {
        return getDefaultReturn();
    }

    public boolean visit(final MethodInvocation node, final LogVisitor visitor,
            final List/* <TextEdit> */edits) {
        return getDefaultReturn();
    }

    public boolean visit(final ImportDeclaration node,
            final LogVisitor visitor, final List/* <TextEdit> */edits) {
        return getDefaultReturn();
    }

    public boolean visit(final FieldDeclaration node, final LogVisitor visitor,
            final List/* <TextEdit> */edits) {
        return getDefaultReturn();
    }

    public boolean visit(final FieldAccess node, final LogVisitor visitor,
            final List/* <TextEdit> */edits) {
        return getDefaultReturn();
    }

    public boolean visit(final VariableDeclarationStatement node,
            final LogVisitor visitor, final List/* <TextEdit> */edits) {
        return getDefaultReturn();
    }

    public boolean visit(final VariableDeclarationExpression node,
            final LogVisitor visitor, final List/* <TextEdit> */edits) {
        return getDefaultReturn();
    }

    public boolean visit(final VariableDeclarationFragment node,
            final LogVisitor visitor, final List/* <TextEdit> */edits) {
        return getDefaultReturn();
    }

}
