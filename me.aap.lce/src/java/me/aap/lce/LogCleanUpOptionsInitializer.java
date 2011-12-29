package me.aap.lce;

import org.eclipse.jdt.ui.cleanup.CleanUpOptions;
import org.eclipse.jdt.ui.cleanup.ICleanUpOptionsInitializer;

import me.aap.lce.LogFinderFactory.Option;

/**
 * @author Andrey Pavlenko
 */
public class LogCleanUpOptionsInitializer implements ICleanUpOptionsInitializer {

    public void setDefaultOptions(final CleanUpOptions cleanUpOptions) {
        final LogFinderFactory[] cache = LogFinderFactory.getInstalled();

        for (int i = 0; i < cache.length; i++) {
            final LogFinderFactory f = cache[i];
            final Option[] ops = f.getOptions();

            cleanUpOptions.setOption(f.getId(),
                f.isEnabled() ? CleanUpOptions.TRUE : CleanUpOptions.FALSE);

            for (int n = 0; n < ops.length; n++) {
                cleanUpOptions.setOption(ops[n].getQname(),
                    ops[n].isEnabled() ? CleanUpOptions.TRUE
                            : CleanUpOptions.FALSE);
            }
        }
    }
}
