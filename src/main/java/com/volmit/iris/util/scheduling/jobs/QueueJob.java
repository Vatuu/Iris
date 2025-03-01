/*
 * Iris is a World Generator for Minecraft Bukkit Servers
 * Copyright (c) 2022 Arcane Arts (Volmit Software)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.volmit.iris.util.scheduling.jobs;

import com.volmit.iris.util.collection.KList;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class QueueJob<T> implements Job {
    final KList<T> queue;
    private final AtomicInteger completed;
    protected int totalWork;

    public QueueJob() {
        totalWork = 0;
        completed = new AtomicInteger(0);
        queue = new KList<>();
    }

    public QueueJob queue(T t) {
        queue.add(t);
        totalWork++;
        return this;
    }

    public QueueJob queue(KList<T> f) {
        queue.addAll(f);
        totalWork += f.size();
        return this;
    }

    public abstract void execute(T t);

    @Override
    public void execute() {
        totalWork = queue.size();
        while(queue.isNotEmpty()) {
            execute(queue.pop());
            completeWork();
        }
    }

    @Override
    public void completeWork() {
        completed.incrementAndGet();
    }

    @Override
    public int getTotalWork() {
        return totalWork;
    }

    @Override
    public int getWorkCompleted() {
        return completed.get();
    }
}
