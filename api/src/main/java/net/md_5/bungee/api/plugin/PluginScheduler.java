package net.md_5.bungee.api.plugin;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.inject.Inject;

import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.avicus.minecraft.api.scheduler.Scheduler;
import net.avicus.minecraft.api.scheduler.Task;
import net.avicus.minecraft.api.scheduler.Tickable;

public class PluginScheduler implements Scheduler {

    private final Plugin plugin;
    private final TaskScheduler scheduler;

    @Inject public PluginScheduler(Plugin plugin, TaskScheduler scheduler) {
        this.plugin = plugin;
        this.scheduler = scheduler;
    }

    @Override
    public ScheduledTask schedule(boolean sync, @Nullable Duration delay, @Nullable Duration period, Runnable task) {
        return scheduler.schedule(
            plugin,
            task,
            delay == null ? 0 : delay.toNanos(),
            period == null ? -1 : period.toNanos(),
            TimeUnit.NANOSECONDS
        );
    }

    @Override
    public Task schedule(Tickable tickable) {
        final Duration period = tickable.tickPeriod();
        if(period.isZero()) {
            throw new IllegalArgumentException("Bungee tick periods must be non-zero");
        }
        return schedule(tickable.isSynchronous(), tickable.initialDelay(), period, tickable::tick);
    }
}
