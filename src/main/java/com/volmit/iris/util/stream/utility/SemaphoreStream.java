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

package com.volmit.iris.util.stream.utility;

import com.volmit.iris.util.stream.BasicStream;
import com.volmit.iris.util.stream.ProceduralStream;

import java.util.concurrent.Semaphore;

public class SemaphoreStream<T> extends BasicStream<T> {
    private final Semaphore semaphore;

    public SemaphoreStream(ProceduralStream<T> stream, int permits) {
        super(stream);
        this.semaphore = new Semaphore(permits);
    }

    @Override
    public double toDouble(T t) {
        return getTypedSource().toDouble(t);
    }

    @Override
    public T fromDouble(double d) {
        return getTypedSource().fromDouble(d);
    }

    @Override
    public T get(double x, double z) {
        try {
            semaphore.acquire();
            T t = getTypedSource().get(x, z);
            semaphore.release();
            return t;
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public T get(double x, double y, double z) {
        try {
            semaphore.acquire();
            T t = getTypedSource().get(x, y, z);
            semaphore.release();
            return t;
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
