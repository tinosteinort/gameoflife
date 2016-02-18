package gol.rule;

import gol.Status;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tino on 17.01.2016.
 */
public class StatusCalculator {

    private final List<Rule> rules = Arrays.asList(
            new RecreationRule(),
            new SurvivalRule(),
            new UnderPopulationRule(),
            new OverPopulationRule()
    );

    public Status calculateStatus(final Status currentStatus, final int neighbourCount) {

        for (Rule rule : rules) {

            if (rule.matches(currentStatus, neighbourCount)) {
                return rule.getStatus();
            }
        }

        throw new IllegalStateException("No Rule matches");
    }
}
