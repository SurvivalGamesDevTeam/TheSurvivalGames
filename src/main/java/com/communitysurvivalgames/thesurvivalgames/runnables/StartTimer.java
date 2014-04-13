/**
 * Name: StartTimer.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.runnables;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * The type Start timer. Just for testing use makeTimer method THIS IS ONLY A
 * TEST
 * 
 */
public class StartTimer {

    private static final boolean DONT_INTERRUPT_IF_RUNNING = false;

    private final long fInitialDelay;

    private final long fDelayPeriod;

    private final long fShutDownAfter;

    private static ScheduledExecutorService scheduler = SGApi.getScheduler().getScheduler();

    public Integer timeleft;

    public StartTimer(long initial, long period, Integer tl) {

        fInitialDelay = initial;
        fDelayPeriod = period;
        timeleft = tl;
        fShutDownAfter = tl.longValue();
        StartTimerAndStop();
    }

    private static void log(String aMsg) {
        System.out.println(aMsg);
    }

    /**
     * Make timer. Used to create a new instance of Start Game Countdown
     * 
     * @param initial the initial delay before starting - seconds
     * @param period the period between executions - seconds
     * @param tl the max time the timer will run before being shutdown - seconds
     */
    public static void makeTimer(long initial, long period, Integer tl) {
        new StartTimer(initial, period, tl);
    }

    /**
     * Basically Initialise the object
     */
    void StartTimerAndStop() {

        Runnable GameStartTask = new StartGameTimerTask((int) fShutDownAfter);
        ScheduledFuture<?> StartGameFuture = scheduler.scheduleAtFixedRate(GameStartTask, fInitialDelay, fDelayPeriod, TimeUnit.SECONDS);
        Runnable stopStartGameTask = new StopGameStartTask(StartGameFuture);
        scheduler.schedule(stopStartGameTask, fShutDownAfter, TimeUnit.SECONDS);
    }

    /**
     * The actual Task for the Game Timer
     */
    private static final class StartGameTimerTask implements Runnable {

        private Integer timeleft;

        StartGameTimerTask(int i) {
            timeleft = i;

        }

        @Override
        public void run() {

            --timeleft;

            if (this.timeleft.equals(30)) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game is about to start in " + this.timeleft);
            } else if (this.timeleft.equals(25)) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game is about to start in " + this.timeleft);
            } else if (this.timeleft.equals(20)) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game is about to start in " + this.timeleft);
            } else if (this.timeleft.equals(15)) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game is about to start in " + this.timeleft);
            } else if (this.timeleft.equals(10)) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game is about to start in " + this.timeleft);
            } else if (timeleft < 10 && timeleft > 0) {
                Bukkit.broadcastMessage(ChatColor.BLUE + "The game is about to start in " + this.timeleft);
            } else if (timeleft == 0) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "GO GO GO Game Started");
            }

        }
    }

    /**
     * The Task for the future eg ending of the task.
     */
    private final class StopGameStartTask implements Runnable {

        StopGameStartTask(ScheduledFuture<?> aSchedFeature) {

            fScheduledFuture = aSchedFeature;

        }

        @Override
        public void run() {
            log("Stopping the Game Start Timer");

            fScheduledFuture.cancel(DONT_INTERRUPT_IF_RUNNING);

            // scheduler.shutdown();
        }

        private ScheduledFuture<?> fScheduledFuture;
    }

}
