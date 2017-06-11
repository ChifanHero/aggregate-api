package com.chifanhero.api.tasks;

import java.util.TimerTask;

/**
 * Created by shiyan on 6/10/17.
 */
public abstract class Task extends TimerTask {

    abstract void executeTask();
}
