package com.chifanhero.api.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Takes multiple futures, let them run in parallel and returns the results within a specific time bond.
 * If any future fails to finish on time, the returned results will not contain the result of that future.
 * Created by shiyan on 4/30/17.
 */
@Component
public class FutureResolver {

    private final ExecutorService executorService;

    @Autowired
    public FutureResolver(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public <T> FutureObserverTask<T> resolve(Future<T>... futures) {
        FutureObserverTask<T> futureObserverTask = new FutureObserverTask<>(executorService);
        for (Future future : futures) {
            ObservableFutureTask observableTask = new ObservableFutureTask(future);
            futureObserverTask.addDependency(observableTask);
        }
        return futureObserverTask;
    }

    public <T> FutureObserverTask<T> resolve(List<Future<T>> futures) {
        FutureObserverTask<T> futureObserverTask = new FutureObserverTask<>(executorService);
        for (Future future : futures) {
            ObservableFutureTask observableTask = new ObservableFutureTask(future);
            futureObserverTask.addDependency(observableTask);
        }
        return futureObserverTask;
    }

    private class ObservableFutureTask extends Observable implements Runnable {

        Future future;

        ObservableFutureTask(Future future) {
            this.future = future;
        }

        @Override
        public void run() {
            try {
                Object result = future.get();
                setChanged();
                notifyObservers(result);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class FutureObserverTask<T> implements Callable<List<T>>, Observer {
        private List<T> results = new ArrayList<>();
        private AtomicInteger dependencyCounter = new AtomicInteger(0);
        private ExecutorService executorService;

        FutureObserverTask(ExecutorService executorService) {
            this.executorService = executorService;
        }

        @Override
        public void update(Observable o, Object arg) {
            results.add((T)arg);
            int count = dependencyCounter.decrementAndGet();
            if (count == 0) {
                notify();
            }
        }

        @Override
        public List<T> call() throws Exception {
            while(dependencyCounter.get() > 0) {
                synchronized (this) {
                    this.wait();
                }
            }
            return results;
        }

        private void addDependency(ObservableFutureTask dependency) {
            dependency.addObserver(this);
            dependencyCounter.incrementAndGet();
            executorService.submit(dependency);
        }

        public List<T> get(Long timeout, TimeUnit timeUnit) {
            Future<List<T>> resultFuture = executorService.submit(this);
            try {
                return resultFuture.get(timeout, timeUnit);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                return results;
            }
        }

        public List<T> get() {
            Future<List<T>> resultFuture = executorService.submit(this);
            try {
                return resultFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
