package gol.rule;

import gol.Status;

/**
 * Created by Tino on 17.01.2016.
 */
interface Rule {

    boolean matches(Status currentStatus, int neighbourCount);
    Status getStatus();
}
