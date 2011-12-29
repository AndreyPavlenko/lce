package me.aap.lce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.refactoring.CompilationUnitChange;
import org.eclipse.jdt.ui.cleanup.CleanUpContext;
import org.eclipse.jdt.ui.cleanup.CleanUpOptions;
import org.eclipse.jdt.ui.cleanup.CleanUpRequirements;
import org.eclipse.jdt.ui.cleanup.ICleanUp;
import org.eclipse.jdt.ui.cleanup.ICleanUpFix;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;

/**
 * @author Andrey Pavlenko
 */
public class LogCleanUp implements ICleanUp {
    public static final String EXT_POINT_ID = "logFinder";
    private CleanUpOptions options;

    public void setOptions(final CleanUpOptions options) {
        this.options = options;
    }

    public CleanUpOptions getOptions() {
        return options;
    }

    public String[] getStepDescriptions() {
        final CleanUpOptions ops = getOptions();

        if ((ops == null) || ops.getKeys().isEmpty()) {
            return null;
        }

        final LogFinderFactory[] cache = LogFinderFactory
                .getInstalled(getOptions());

        if (cache.length == 0) {
            return null;
        } else {
            final String[] s = new String[cache.length];

            for (int i = 0; i < cache.length; i++) {
                s[i] = cache[i].getDescription();
            }

            return s;
        }
    }

    public CleanUpRequirements getRequirements() {
        return new CleanUpRequirements(true, false, false, null);
    }

    public RefactoringStatus checkPreConditions(final IJavaProject project,
            final ICompilationUnit[] compilationUnits,
            final IProgressMonitor monitor) throws CoreException {
        return new RefactoringStatus();
    }

    public ICleanUpFix createFix(final CleanUpContext context)
            throws CoreException {
        final CompilationUnit u = context.getAST();

        if (u != null) {
            List edits;
            final LogVisitor v = new LogVisitor(getLogFinders());

            context.getAST().accept(v);
            edits = v.getTextEdits();

            if (!edits.isEmpty()) {
                final CompilationUnitChange c = new CompilationUnitChange(
                        "LogCleanUp", context.getCompilationUnit());
                c.setEdit(new MultiTextEdit());

                for (final Iterator it = edits.iterator(); it.hasNext();) {
                    c.addEdit((TextEdit) it.next());
                }

                return new ICleanUpFix() {
                    public CompilationUnitChange createChange(
                            final IProgressMonitor progressMonitor)
                            throws CoreException {
                        return c;
                    }
                };
            }
        }

        return null;
    }

    public RefactoringStatus checkPostConditions(final IProgressMonitor monitor)
            throws CoreException {
        return new RefactoringStatus();
    }

    private List getLogFinders() {
        final LogFinderFactory[] cache = LogFinderFactory
                .getInstalled(getOptions());

        if (cache.length > 0) {
            final List l = new ArrayList(cache.length);

            for (int i = 0; i < cache.length; i++) {
                final LogFinder f = cache[i].getLogFinder();
                if (f != null) {
                    l.add(f);
                }
            }

            return l;
        }

        return Collections.EMPTY_LIST;
    }
}
