/*
 * Copyright 2016 Abstraction
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.astromc.libreui.utils;

import java.util.function.Supplier;

/**
 * @author DarkSeraphim.
 */
public final class CachedSupplier<T> implements Supplier<T> {

    private final Supplier<T> supplier;

    private T value;

    private CachedSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public void invalidate() {
        this.value = null;
    }

    public T get() {
        if (this.value == null) {
            this.value = supplier.get();
        }
        return this.value;
    }

    public static <T> CachedSupplier<T> of(Supplier<T> supplier) {
        return supplier instanceof CachedSupplier ? (CachedSupplier<T>) supplier : new CachedSupplier<>(supplier);
    }
}
