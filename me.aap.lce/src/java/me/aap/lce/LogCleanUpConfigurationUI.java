package me.aap.lce;

import org.eclipse.jdt.ui.cleanup.CleanUpOptions;
import org.eclipse.jdt.ui.cleanup.ICleanUpConfigurationUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Andrey Pavlenko
 */
public class LogCleanUpConfigurationUI implements ICleanUpConfigurationUI {
    private volatile CleanUpOptions options;

    public void setOptions(final CleanUpOptions options) {
        this.options = options;
    }

    public CleanUpOptions getOptions() {
        return options;
    }

    public int getCleanUpCount() {
        int count = 0;
        final LogFinderFactory[] f = getCache();

        for (int i = 0; i < f.length; i++) {
            count += f[i].getCleanUpCount();
        }

        return count;
    }

    public int getSelectedCleanUpCount() {
        int count = 0;
        final LogFinderFactory[] f = getCache();

        for (int i = 0; i < f.length; i++) {
            count += f[i].getSelectedCleanUpCount();
        }

        return count;
    }

    public String getPreview() {
        final LogFinderFactory[] f = getCache();

        if (f.length == 0) {
            return "";
        } else {
            final StringBuffer sb = new StringBuffer();
            for (int i = 0; i < f.length; i++) {
                if (f[i].isEnabled()) {
                    sb.append("// ");
                    sb.append(f[i].getName());
                    sb.append('\n');
                    sb.append(f[i].getPreview());

                    if (i + 1 < f.length) {
                        sb.append("\n\n");
                    }
                }
            }
            return (sb.length() == 0) ? "" : sb.toString();
        }
    }

    public Composite createContents(final Composite parent) {
        final Composite c = new Composite(parent, SWT.NONE);
        final LogFinderFactory[] f = getCache();

        c.setLayout(new GridLayout(1, false));
        c.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        for (int i = 0; i < f.length; i++) {
            f[i].createOptionsView(c);
        }

        return c;
    }

    private LogFinderFactory[] getCache() {
        return LogFinderFactory.getInstalled(getOptions());
    }
}
