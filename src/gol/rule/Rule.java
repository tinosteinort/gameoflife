package gol.rule;

import gol.Status;

/**
 * Created by Tino on 17.01.2016.
 */
public interface Rule {

    boolean matches(Status currentStatus, int neighbourCount);
    Status getStatus();
}
