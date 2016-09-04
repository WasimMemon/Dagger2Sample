/*
 * Copyright (c) 2015. Created by Wasim Memon
 */

package com.androprogrammer.dagger2sample.domain.network;

import java.util.concurrent.atomic.AtomicInteger;


public class UniqueNumberUtils {

    private static UniqueNumberUtils INSTANCE = new UniqueNumberUtils();

    private AtomicInteger seq;

    private UniqueNumberUtils() {
        seq = new AtomicInteger(0);
    }

    public static UniqueNumberUtils getInstance() {
        return INSTANCE;
    }

    public int getUniqueId() {
        return seq.incrementAndGet();
    }
}