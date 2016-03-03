package gol.rule;

import gol.Status;

/**
 * Created by Tino on 17.01.2016.
 */
class OverPopulationRule implements Rule {

    @Override
    public boolean matches(final Status currentStatus, final int neighbourCount) {
        return neighbourCount > 3;
    }

    @Override
    public Status getStatus() {
        return Status.DEAD;
    }
}
