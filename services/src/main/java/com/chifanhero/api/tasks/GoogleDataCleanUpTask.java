package com.chifanhero.api.tasks;

import com.chifanhero.api.services.chifanhero.ChifanheroRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * This task periodically check expire_at of mongodb documents and delete data for expired documents
 */
@Component
public class GoogleDataCleanUpTask extends Task {

    private final ChifanheroRestaurantService chifanheroRestaurantService;

    @Autowired
    public GoogleDataCleanUpTask(ChifanheroRestaurantService chifanheroRestaurantService) {
        this.chifanheroRestaurantService = chifanheroRestaurantService;
    }

    @PostConstruct
    @Override
    void executeTask() {
        Timer timer = new Timer();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 11);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 0);
        timer.schedule(this, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    }

    @Override
    public void run() {
        chifanheroRestaurantService.expireData();
    }
}
