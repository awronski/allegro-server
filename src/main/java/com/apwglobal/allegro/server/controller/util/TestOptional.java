package com.apwglobal.allegro.server.controller.util;

import java.util.Optional;

public class TestOptional {

    public static boolean eq(Optional o, Object v) {
        return !o.isPresent() || o.get().equals(v);
    }

    @SuppressWarnings("unchecked")
    public static boolean ge(Optional<? extends Comparable> o, Comparable v) {
        return !o.isPresent() || v.compareTo(o.get()) >= 0;
    }

    @SuppressWarnings("unchecked")
    public static boolean le(Optional<? extends Comparable> o, Comparable v) {
        return !o.isPresent() || v.compareTo(o.get()) <= 0;
    }

}
