package com.chifanhero.api.factories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by shiyan on 4/30/17.
 */
@Configuration
public class TaskExecutorFactory {

    @Bean
    public TaskExecutor createTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
