/**
 * Name: ScheduleManager.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;

/**
 * The type Schedule manager.
 */
public class ScheduleManager {

    private int schedNumThreads;
    private int repeatingThreads;
    private int loginNumThreads;
    private ScheduledExecutorService scheduler;
    private ExecutorService executor;

    /**
     * Instantiates a new Schedule manager.
     */
    public ScheduleManager() {

        schedNumThreads = 2;
        scheduler = Executors.newScheduledThreadPool(schedNumThreads);
        startExecutor();
    }

    private void startExecutor() {
        if (executor == null || executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(2);
            System.out.println("Fixed Executor Starting");
        }
    }

    /**
     * Gets sched num threads.
     * 
     * @return the sched num threads
     */
    public int getSchedNumThreads() {
        return schedNumThreads;
    }

    /**
     * Sets sched num threads.
     * 
     * @param schedNumThreads the sched num threads
     */
    public void setSchedNumThreads(int schedNumThreads) {
        this.schedNumThreads = schedNumThreads;
    }

    /**
     * Gets login num threads.
     * 
     * @return the login num threads
     */
    public int getLoginNumThreads() {
        return loginNumThreads;
    }

    /**
     * Sets login num threads.
     * 
     * @param loginNumThreads the login num threads
     */
    public void setLoginNumThreads(int loginNumThreads) {
        this.loginNumThreads = loginNumThreads;
    }

    /**
     * Gets repeating threads.
     * 
     * @return the repeating threads
     */
    public int getRepeatingThreads() {
        return repeatingThreads;
    }

    /**
     * Sets repeating threads.
     * 
     * @param repeatingThreads the repeating threads
     */
    public void setRepeatingThreads(int repeatingThreads) {
        this.repeatingThreads = repeatingThreads;
    }

    /**
     * Get Scheduler for repeating tasks
     * {@link java.util.concurrent.ScheduledExecutorService}
     * <p>
     * details can be found here
     * 
     * @return the scheduled executor service
     */
    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    /**
     * Get ExecutorService instance {@link java.util.concurrent.ExecutorService}
     * 
     * @return the executor service
     */
    public ExecutorService getExecutor() {
        return executor;
    }

    /**
     * Shutdown all thread pools and services
     */
    public void shutdownAll() {

        this.executor.shutdown();
        if (!this.scheduler.isShutdown()) {
            this.scheduler.shutdown();
        }
        if (!this.executor.isShutdown()) {
            this.executor.shutdown();
        }

        SGApi.getPlugin().getLogger().info("All threads have been successfully shutdown");
    }

    /**
     * Submit a Callable Task and receive the result back.
     * <p>
     * This {@link java.util.concurrent.Callable} will run straight away and
     * return the result. Best used for quick tasks but there might be lots of
     * them.
     * <p>
     * This will throw null if your callable returns null
     * 
     * @param callable the callable
     * @return the object This is the object that your callable returns
     */
    public Object runNow(Callable callable) {

        this.startExecutor();

        Object result = null;
        try {
            result = executor.submit(callable).get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
        if (result == null)
            return null;
        else
            return result;
    }

    /**
     * Execute a Runnable straight away.
     * <p>
     * This takes a {@link java.lang.Runnable} and is managed by an
     * {@link java.util.concurrent.ExecutorService} so it is queued if busy Used
     * for quick repetitive tasks.
     * 
     * @param runnable the runnable
     * @throws RejectedExecutionException the rejected execution exception
     * @throws NullPointerException the null pointer exception
     */
    public void runNow(Runnable runnable) throws RejectedExecutionException, NullPointerException {

        this.startExecutor();

        executor.execute(runnable);
    }
}
