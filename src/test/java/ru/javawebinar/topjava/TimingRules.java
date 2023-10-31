package ru.javawebinar.topjava;

import java.util.concurrent.TimeUnit;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimingRules {

        private static final Logger log = LoggerFactory.getLogger("Test summary");

        private static final StringBuilder info = new StringBuilder();

        public static final Stopwatch STOPWATCH = new Stopwatch() {
            @Override
            protected void finished(long nanos, Description description) {
                String result = String.format("%-95s %7d", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
                info.append(result).append('\n');
                log.info("{" + TimeUnit.NANOSECONDS.toMillis(nanos) + " ms} - " + description.getClassName() + "." + description.getMethodName());
            }
        };

        private static final String DELIM = "-".repeat(103);

        public static final ExternalResource SUMMARY = new ExternalResource() {

            @Override
            protected void before() throws Throwable {
                info.setLength(0);
            }

            @Override
            protected void after() {
                log.info("\n" + DELIM +
                    "\nTest summary                                                                              Duration, ms" +
                    "\n" + DELIM + "\n" + info + DELIM + "\n");
            }
        };
    }