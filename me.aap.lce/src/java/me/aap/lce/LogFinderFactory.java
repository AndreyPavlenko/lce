package me.aap.lce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.ui.cleanup.CleanUpOptions;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;


/**
 * @author Andrey Pavlenko
 */
public abstract class LogFinderFactory implements IExecutableExtension {
    private static volatile LogFinderFactory[] cache;
    protected volatile IConfigurationElement config;
    protected volatile CleanUpOptions cleanUpOptions;
    protected volatile String id;
    protected volatile String name;
    protected volatile String description;
    protected volatile String enabledPreview;
    protected volatile String disabledPreview;
    protected volatile boolean enabled;
    protected volatile Option[] options;

    public abstract LogFinder getLogFinder();

    public static LogFinderFactory[] getInstalled() {
        LogFinderFactory[] c = cache;

        if (c == null) {
            final List l = loadInstalled();
            cache = c = (LogFinderFactory[]) l.toArray(new LogFinderFactory[l
                    .size()]);
        }

        return c;
    }

    public static LogFinderFactory[] getInstalled(
            final CleanUpOptions cleanUpOptions) {
        final LogFinderFactory[] c = cache = getInstalled();

        for (int i = 0; i < c.length; i++) {
            c[i].setCleanUpOptions(cleanUpOptions);
        }

        return c;
    }

    public void setInitializationData(final IConfigurationElement config,
            final String propertyName, final Object data) {
        IConfigurationElement[] c = config.getChildren("option");
        final Option[] options = new Option[c.length];

        this.config = config;
        id = config.getAttribute("id");
        name = config.getAttribute("name");
        enabled = getBooleanAttr(getConfig(), "enabled", true);

        for (int i = 0; i < c.length; i++) {
            options[i] = createOption(c[i]);
        }

        this.options = options;
        c = config.getChildren("description");
        description = (c.length > 0) ? c[0].getValue() : "Enable " + name;

        c = config.getChildren("enabledPreview");
        enabledPreview = (c.length > 0) ? c[0].getValue() : "";

        c = config.getChildren("disabledPreview");
        disabledPreview = (c.length > 0) ? c[0].getValue() : "";
    }

    public void setCleanUpOptions(final CleanUpOptions cleanUpOptions) {
        final Option[] ops = getOptions();
        final Set keys = cleanUpOptions.getKeys();
        this.cleanUpOptions = cleanUpOptions;

        if (ops != null) {
            for (int i = 0; i < ops.length; i++) {
                if (keys.contains(ops[i].qname)) {
                    ops[i].enabled = cleanUpOptions.isEnabled(ops[i].qname);
                }
            }
        }
        if (keys.contains(getId())) {
            enabled = cleanUpOptions.isEnabled(getId());
        }
    }

    public IConfigurationElement getConfig() {
        return config;
    }

    public CleanUpOptions getCleanUpOptions() {
        return cleanUpOptions;
    }

    public String getId() {
        return id;
    }

    public int getCleanUpCount() {
        return getOptions().length;
    }

    public int getSelectedCleanUpCount() {
        int count = 0;
        final Option[] o = getOptions();

        for (int i = 0; i < o.length; i++) {
            if (o[i].isEnabled()) {
                count++;
            }
        }

        return count;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEnabledPreview() {
        return enabledPreview;
    }

    public String getDisabledPreview() {
        return disabledPreview;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        final CleanUpOptions o = getCleanUpOptions();
        this.enabled = enabled;

        if (o != null) {
            o.setOption(getId(), enabled ? CleanUpOptions.TRUE
                    : CleanUpOptions.FALSE);
        }
    }

    public Option[] getOptions() {
        final Option[] ops = options;
        return (ops == null) ? new Option[0] : ops;
    }

    public Option getOption(final String id) {
        final Option[] options = getOptions();
        for (int i = 0; i < options.length; i++) {
            if (id.equals(options[i].getId())) {
                return options[i];
            }
        }
        return null;
    }

    public String getPreview() {
        if (!isEnabled()) {
            return getDisabledPreview();
        }

        final Option[] options = getOptions();

        if (options.length == 0) {
            return getEnabledPreview();
        }

        final StringBuffer sb = new StringBuffer(getEnabledPreview());

        if (sb.length() > 0) {
            sb.append("\n\n");
        }

        for (int i = 0; i < options.length; i++) {
            sb.append(options[i].getPreview());
            if (i + 1 < options.length) {
                sb.append("\n\n");
            }
        }

        return (sb.length() == 0) ? "" : sb.toString();
    }

    public Composite createOptionsView(final Composite parent) {
        final Group g = new Group(parent, SWT.NONE);
        final Button b = new Button(g, SWT.CHECK);
        final Label space = new Label(g, SWT.NONE);
        final Option[] o = getOptions();
        final Button[] ob = new Button[o.length];

        g.setLayout(new GridLayout(3, false));
        g.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        g.setText(getName());

        b.setText(getDescription());
        b.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
        b.setSelection(isEnabled());
        b.addSelectionListener(new SelectionListener() {
            public void widgetSelected(final SelectionEvent e) {
                final boolean enabled = b.getSelection();
                setEnabled(enabled);

                for (int i = 0; i < ob.length; i++) {
                    ob[i].setEnabled(enabled);
                }
            }

            public void widgetDefaultSelected(final SelectionEvent e) {
                widgetSelected(e);
            }
        });

        space.setText("        ");
        space.setLayoutData(new GridData(
                SWT.LEFT, SWT.CENTER, false, true, 1, rowspan(o.length, 2)));

        for (int i = 0; i < o.length; i++) {
            final int ind = i;
            ob[i] = new Button(g, SWT.CHECK);
            ob[i].setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
            ob[i].setText(o[i].getName());
            ob[i].setSelection(o[i].isEnabled());
            ob[i].setEnabled(isEnabled());
            ob[i].addSelectionListener(new SelectionListener() {
                public void widgetSelected(final SelectionEvent e) {
                    o[ind].setEnabled(ob[ind].getSelection());
                }

                public void widgetDefaultSelected(final SelectionEvent e) {
                    widgetSelected(e);
                }
            });
        }

        return g;
    }

    protected Option createOption(final IConfigurationElement c) {
        return new Option(c);
    }

    protected static boolean getBooleanAttr(final IConfigurationElement c,
            final String attrName, final boolean defaultValue) {
        final String attr = c.getAttribute(attrName);
        return (attr == null) ? defaultValue : Boolean.getBoolean(attr);
    }

    private static int rowspan(final int cells, final int rows) {
        return (cells % rows == 0) ? cells / rows : cells / rows + 1;
    }

    private static List loadInstalled() {
        final IExtension[] ext = Platform
                .getExtensionRegistry()
                .getExtensionPoint(LogCleanUpPlugin.PLUGIN_ID,
                    LogCleanUp.EXT_POINT_ID).getExtensions();

        if (ext != null) {
            final ArrayList l = new ArrayList();

            for (int i = 0; i < ext.length; i++) {
                try {
                    final IConfigurationElement[] c = ext[i]
                            .getConfigurationElements();

                    for (int n = 0; n < c.length; n++) {
                        if (!c[n].getName().equals("logFinder")) {
                            LogCleanUpPlugin.logErr(
                                "Invalid ConfigurationElement: " + c[n], null);
                        } else {
                            final LogFinderFactory f = (LogFinderFactory) c[n]
                                    .createExecutableExtension("class");
                            l.add(f);
                        }
                    }
                } catch (final Exception ex) {
                    LogCleanUpPlugin.logErr("Failed to initialized extension", ex);
                }
            }

            return l;
        }

        return Collections.EMPTY_LIST;
    }

    public class Option {
        private final String id;
        private final String name;
        private final String qname;
        private final String enabledPreview;
        private final String disabledPreview;
        private final IConfigurationElement config;
        private volatile boolean enabled;

        public Option(final IConfigurationElement config) {
            IConfigurationElement[] c;

            this.config = config;
            id = config.getAttribute("id");
            name = config.getAttribute("name");
            qname = LogFinderFactory.this.getId() + "." + id;
            enabled = getBooleanAttr(config, "enabled", true);

            c = config.getChildren("enabledPreview");
            enabledPreview = (c.length > 0) ? c[0].getValue() : "";

            c = config.getChildren("disabledPreview");
            disabledPreview = (c.length > 0) ? c[0].getValue() : "";
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(final boolean enabled) {
            final CleanUpOptions o = getCleanUpOptions();
            this.enabled = enabled;

            if (o != null) {
                o.setOption(getQname(), enabled ? CleanUpOptions.TRUE
                        : CleanUpOptions.FALSE);
            }
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getQname() {
            return qname;
        }

        public String getEnabledPreview() {
            return enabledPreview;
        }

        public String getDisabledPreview() {
            return disabledPreview;
        }

        public IConfigurationElement getConfig() {
            return config;
        }

        public String getPreview() {
            return isEnabled() ? getEnabledPreview() : getDisabledPreview();
        }
    }
}
