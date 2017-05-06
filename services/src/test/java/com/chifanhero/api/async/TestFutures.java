package com.chifanhero.api.async;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;


public class TestFutures {

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Test
    public void testNoFuture() throws InterruptedException, ExecutionException, TimeoutException {
        FutureResolver futureResolver = new FutureResolver(executorService);
        List results = futureResolver.resolve().get(1L, TimeUnit.SECONDS);
        Assert.assertTrue(results.isEmpty());
    }

    @Test
    public void testImmediateFutures() throws InterruptedException, ExecutionException, TimeoutException {
        FutureResolver futureResolver = new FutureResolver(executorService);
        FutureResolver.FutureObserverTask futureObserverTask = futureResolver.resolve(createShortFuture(1), createShortFuture(2), createShortFuture(3));
        List results = futureObserverTask.get(1L, TimeUnit.SECONDS);
        Assert.assertEquals(3, results.size());
        Assert.assertTrue(results.contains(1));
        Assert.assertTrue(results.contains(2));
        Assert.assertTrue(results.contains(3));
    }

    @Test
    public void testLongFutures() throws InterruptedException, ExecutionException, TimeoutException {
        FutureResolver futureResolver = new FutureResolver(executorService);
        FutureResolver.FutureObserverTask futureObserverTask = futureResolver.resolve(createLongFuture(1000, 1), createLongFuture(1500, 2), createLongFuture(2000, 3));
        List results = futureObserverTask.get(3L, TimeUnit.SECONDS);
        Assert.assertEquals(3, results.size());
        Assert.assertTrue(results.contains(1));
        Assert.assertTrue(results.contains(2));
        Assert.assertTrue(results.contains(3));
    }

    @Test
    public void testShortAndLongFutures() {
        FutureResolver futureResolver = new FutureResolver(executorService);
        FutureResolver.FutureObserverTask futureObserverTask = futureResolver.resolve(createShortFuture(1), createLongFuture(1000, 2));
        List results = futureObserverTask.get(2L, TimeUnit.SECONDS);
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.contains(1));
        Assert.assertTrue(results.contains(2));
    }

    @Test
    public void testTimeout() {
        FutureResolver futureResolver = new FutureResolver(executorService);
        FutureResolver.FutureObserverTask futureObserverTask = futureResolver.resolve(createShortFuture(1), createLongFuture(1500, 2), createLongFuture(2500, 3), createLongFuture(5000, 4));
        List results = futureObserverTask.get(2L, TimeUnit.SECONDS);
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.contains(1));
        Assert.assertTrue(results.contains(2));
    }

    private Future createShortFuture(int result) {
        Callable<Integer> shortTask = () -> result;
        return executorService.submit(shortTask);
    }

    private Future createLongFuture(long time, int result) {
        Callable<Integer> longTask = () -> {
            Thread.sleep(time);
            return result;
        };
        return executorService.submit(longTask);
    }
}
