package com.andrew121410.mc.ccminecraftbot.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

public class SimpleScheduler {

    private static int seconds = 0;
    private static int taskCounter = 0;
    private static Map<Integer, Task> taskMap = new HashMap<>();

    public static void onTick() {
        if (taskMap.isEmpty()) return;
        seconds++;
        Map.Entry<Integer, Task> taskEntry = taskMap.entrySet().stream().filter(set -> set.getValue().getSeconds() + 1 == seconds).findFirst().orElse(null);
        if (taskEntry == null) {
            if (seconds >= 10000 && taskMap.isEmpty()) seconds = 0;
            return;
        }
        Task task = taskEntry.getValue();
        task.getRunnable().run();
        taskMap.remove(task.getTaskId());
        if (task.isRepeating())
            taskMap.put(task.getTaskId(), new Task(task.getSeconds(), task.getTaskId(), true, task.getRunnable()));
    }

    public static int schedule(int seconds, Runnable runnable) {
        taskCounter++;
        taskMap.put(taskCounter, new Task(seconds, taskCounter, false, runnable));
        return taskCounter;
    }

    public static int scheduleRepeating(int ticks, Runnable runnable) {
        taskCounter++;
        taskMap.put(taskCounter, new Task(ticks, taskCounter, true, runnable));
        return taskCounter;
    }

    public static void cancelTask(int taskId) {
        taskMap.remove(taskId);
    }
}

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
class Task {
    private final long seconds;
    private final int taskId;
    private final boolean isRepeating;
    private final Runnable runnable;
}