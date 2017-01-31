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

package net.astromc.libreui.book.page;

import net.astromc.libreui.core.LibreUI;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.function.UnaryOperator;

/**
 * A simple implementation of the {@link Page} interface. All
 * the specifications described in the documentation of the
 * interface is implemented accordingly.
 */
public final class SimplePage implements Page {
    private BaseComponent backingComponent;

    /**
     * Constructs a new <tt>SimplePage</tt> instance with a backing
     * component assigned to the specified <tt>backingComponent</tt>.
     *
     * @param backingComponent the component representing this page
     */
    SimplePage(BaseComponent backingComponent) {
        this.backingComponent = backingComponent;
    }

    @Override
    public BaseComponent getBackingComponent() {
        return this.backingComponent.duplicate();
    }

    @Override
    public void setBackingComponent(BaseComponent backingComponent) {
        this.backingComponent = backingComponent.duplicate();
        this.invalidateCache();
    }

    @Override
    public void modifyBackingComponent(UnaryOperator<BaseComponent> operator) {
        this.setBackingComponent(operator.apply(this.backingComponent));
    }

    @Override
    public String getJsonRepresentation() {
        return LibreUI.getSerializedCache().getUnchecked(this);
    }

    @Override
    public Page immutableCopy() {
        return ImmutablePage.newInstance(this);
    }

    /**
     * Invalidates the cached JSON representation of this page.
     */
    private void invalidateCache() {
        LibreUI.getSerializedCache().invalidate(this);
    }
}
