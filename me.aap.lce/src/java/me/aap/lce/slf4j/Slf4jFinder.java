package me.aap.lce.slf4j;

import java.util.HashMap;
import java.util.Map;

import me.aap.lce.LogMethodMatcher;
import me.aap.lce.LogMethodMatcherBasedFinder;
import me.aap.lce.LogFinderFactory.Option;

/**
 * @author Andrey Pavlenko
 */
public class Slf4jFinder extends LogMethodMatcherBasedFinder {
    private static final Map/* <LogMethodMatcher, LogMethodMatcher> */matchers = new HashMap(
            5);
    private final Option[] options;
    private final Slf4jFinderFactory factory;

    static {
        final String[] levels = new String[] { "trace", "debug", "info",
                "warn", "error" };

        for (int i = 0; i < levels.length; i++) {
            final String level = levels[i];
            final String isMethod = "is" + level.substring(0, 1).toUpperCase()
                    + level.substring(1, level.length()) + "Enabled";
            final LogMethodMatcher matcher = new LogMethodMatcher(
                    "org.slf4j.Logger", level, "if ({0}." + isMethod + "()) ",
                    new Integer(i));
            matcher.addMatchException("org.slf4j.Logger", isMethod, null);

            for (int n = i - 1; n >= 0; n--) {
                matcher.addMatchException(
                    "org.slf4j.Logger",
                    "is" + levels[n].substring(0, 1).toUpperCase()
                            + levels[n].substring(1, levels[n].length())
                            + "Enabled", null);
            }

            matchers.put(matcher, matcher);
        }
    }

    public Slf4jFinder(final Slf4jFinderFactory factory) {
        super(false);
        final Option[] options = new Option[5];

        options[0] = factory.getOption("TRACE");
        options[1] = factory.getOption("DEBUG");
        options[2] = factory.getOption("INFO");
        options[3] = factory.getOption("WARN");
        options[4] = factory.getOption("ERROR");
        this.options = options;
        this.factory = factory;
    }

    public Map getMatchers() {
        return matchers;
    }

    public Slf4jFinderFactory getFactory() {
        return factory;
    }

    public boolean isEnabled(final LogMethodMatcher m) {
        if (getFactory().isEnabled()) {
            final Option o = options[((Integer) m.getMarker()).intValue()];
            return (o != null) && o.isEnabled();
        }
        return false;
    }

}
