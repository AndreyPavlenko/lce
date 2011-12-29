package me.aap.lce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.text.edits.InsertEdit;


/**
 * @author Andrey Pavlenko
 */
public class LogVisitor extends ASTVisitor {
    private final List/* <LogFinder> */logFinders;
    private final List/* <TextEdit> */textEdits;
    private final List/* <ImportDeclaration> */imports;
    private final Set/* <String> */importedClasses;
    private final Set/* <String> */importedPackages;
    private IfStatementWrapper ifStatement;
    private List/* <MethodInvocation> */ifExprMethodInvocations;
    private ASTVisitor ifStatementVisitor;
    private PackageDeclaration packageDeclaration;
    private int importOffset;

    public LogVisitor() {
        this(null);
    }

    public LogVisitor(final List/* <LogFinder> */logFinders) {
        this.logFinders = logFinders == null ? new ArrayList() : logFinders;
        this.textEdits = new ArrayList();
        this.imports = new ArrayList();
        this.importedClasses = new HashSet();
        this.importedPackages = new HashSet();
    }

    public boolean visit(final PackageDeclaration node) {
        boolean visit = false;
        final int size = logFinders.size();
        packageDeclaration = node;
        importOffset = node.getStartPosition() + node.getLength();

        for (int i = 0; i < size; i++) {
            final LogFinder v = (LogFinder) logFinders.get(i);
            if (v.visit(node, this, textEdits)) {
                visit = true;
            }
        }

        return visit;
    }

    public boolean visit(final ImportDeclaration node) {
        boolean visit = false;
        final int size = logFinders.size();
        imports.add(node);
        importOffset = node.getStartPosition() + node.getLength();

        if (node.isOnDemand()) {
            importedPackages.add(node.getName().getFullyQualifiedName());
        } else {
            importedClasses.add(node.getName().getFullyQualifiedName());
        }

        for (int i = 0; i < size; i++) {
            final LogFinder v = (LogFinder) logFinders.get(i);
            if (v.visit(node, this, textEdits)) {
                visit = true;
            }
        }

        return visit;
    }

    public boolean visit(final MethodInvocation node) {
        boolean visit = false;
        final int size = logFinders.size();

        for (int i = 0; i < size; i++) {
            final LogFinder v = (LogFinder) logFinders.get(i);
            if (v.visit(node, this, textEdits)) {
                visit = true;
            }
        }

        return visit;
    }

    public boolean visit(final FieldDeclaration node) {
        boolean visit = false;
        final int size = logFinders.size();

        for (int i = 0; i < size; i++) {
            final LogFinder v = (LogFinder) logFinders.get(i);
            if (v.visit(node, this, textEdits)) {
                visit = true;
            }
        }

        return visit;
    }

    public boolean visit(final FieldAccess node) {
        boolean visit = false;
        final int size = logFinders.size();

        for (int i = 0; i < size; i++) {
            final LogFinder v = (LogFinder) logFinders.get(i);
            if (v.visit(node, this, textEdits)) {
                visit = true;
            }
        }

        return visit;
    }

    public boolean visit(final VariableDeclarationStatement node) {
        boolean visit = false;
        final int size = logFinders.size();

        for (int i = 0; i < size; i++) {
            final LogFinder v = (LogFinder) logFinders.get(i);
            if (v.visit(node, this, textEdits)) {
                visit = true;
            }
        }

        return visit;
    }

    public boolean visit(final VariableDeclarationExpression node) {
        boolean visit = false;
        final int size = logFinders.size();

        for (int i = 0; i < size; i++) {
            final LogFinder v = (LogFinder) logFinders.get(i);
            if (v.visit(node, this, textEdits)) {
                visit = true;
            }
        }

        return visit;
    }

    public boolean visit(final VariableDeclarationFragment node) {
        boolean visit = false;
        final int size = logFinders.size();

        for (int i = 0; i < size; i++) {
            final LogFinder v = (LogFinder) logFinders.get(i);
            if (v.visit(node, this, textEdits)) {
                visit = true;
            }
        }

        return visit;
    }

    public boolean visit(final IfStatement node) {
        final Expression expr = node.getExpression();
        ifStatement = new IfStatementWrapper(node, ifStatement);

        if (expr != null) {
            if (ifStatementVisitor == null) {
                ifStatementVisitor = new ASTVisitor() {
                    public boolean visit(final MethodInvocation node) {
                        if (ifExprMethodInvocations == null) {
                            ifExprMethodInvocations = new ArrayList();
                        }
                        ifStatement.methodsCounter++;
                        ifExprMethodInvocations.add(node);
                        LogVisitor.this.visit(node);
                        return true;
                    }
                };
            }

            expr.accept(ifStatementVisitor);
        }

        return true;
    }

    public void endVisit(final IfStatement node) {
        if (ifStatement != null) {
            if ((ifExprMethodInvocations != null)
                    && !ifExprMethodInvocations.isEmpty()
                    && (ifExprMethodInvocations.size() >= ifStatement.methodsCounter)) {
                for (int i = ifExprMethodInvocations.size(); ifStatement.methodsCounter > 0; ifStatement.methodsCounter--) {
                    ifExprMethodInvocations.remove(--i);
                }
            }
            ifStatement = ifStatement.prev;
        }
    }

    public IfStatement getIfStatement() {
        return ifStatement != null ? ifStatement.ifStatement : null;
    }

    public PackageDeclaration getPackageDeclaration() {
        return packageDeclaration;
    }

    public List/* <ImportDeclaration> */getImports() {
        return imports;
    }

    public Set/* <String> */getImportedClasses() {
        return importedClasses;
    }

    public Set/* <String> */getImportedPackages() {
        return importedPackages;
    }

    public List/* <MethodInvocation> */getIfExprMethodInvocations() {
        return ifExprMethodInvocations == null ? Collections.EMPTY_LIST
                : ifExprMethodInvocations;
    }

    public List/* <LogFinder> */getLogFinders() {
        return logFinders;
    }

    public List/* <TextEdit> */getTextEdits() {
        return textEdits;
    }

    public boolean isInsideIf() {
        return getIfStatement() != null;
    }

    public boolean hasIfExprMethodInvocation(final String className,
            final String methodName, final String object) {
        return getIfExprMethodInvocation(className, methodName, object) != null;
    }

    public MethodInvocation getIfExprMethodInvocation(final String className,
            final String methodName, final String object) {
        final List l = getIfExprMethodInvocations();
        final int size = l.size();

        for (int i = 0; i < size; i++) {
            final MethodInvocation m = (MethodInvocation) l.get(i);
            final IMethodBinding b = m.resolveMethodBinding();

            if (b != null) {
                if (!b.getDeclaringClass().getBinaryName().equals(className)) {
                    continue;
                }
                if (!b.getName().equals(methodName)) {
                    continue;
                }
                if (object != null) {
                    if (!object.equals(getObject(m))) {
                        continue;
                    }
                }
                return m;
            }
        }

        return null;
    }

    public static String getObject(final MethodInvocation node) {
        final Expression expr = node.getExpression();
        return (expr != null) ? expr.toString() : null;
    }

    public boolean addImport(final String packageName, final String className) {
        final Set importedClasses = getImportedClasses();

        if (!importedClasses.contains(className)
                && !getImportedPackages().contains(packageName)) {
            final String text;

            if (importOffset == 0) {
                text = "import " + className + ";";
            } else {
                text = Utils.LINE_SEPARATOR + "import " + className + ";";
            }

            getTextEdits().add(new InsertEdit(importOffset, text));
            importedClasses.add(className);
            return true;
        }

        return false;
    }

    public boolean hasSimpleNameImport(final String simpleClassName,
            final String except) {
        final Set imports = getImportedClasses();

        if (!imports.isEmpty()) {
            for (final Iterator it = imports.iterator(); it.hasNext();) {
                final String n = (String) it.next();
                if (n.endsWith(simpleClassName) && !n.equals(except)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static final class IfStatementWrapper {
        final IfStatement ifStatement;
        final IfStatementWrapper prev;
        int methodsCounter;

        public IfStatementWrapper(final IfStatement ifStatement,
                final IfStatementWrapper prev) {
            this.ifStatement = ifStatement;
            this.prev = prev;
        }
    }
}
