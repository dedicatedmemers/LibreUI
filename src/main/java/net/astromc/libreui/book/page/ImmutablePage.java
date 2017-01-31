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

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.function.UnaryOperator;

/**
 * An immutable {@link Page} implementation. Once this
 * object has been created, its values may not change.
 */
public final class ImmutablePage implements Page {
    private final BaseComponent backingComponent;
    private final String jsonRepresentation;

    private ImmutablePage(BaseComponent backingComponent,
                          String jsonRepresentation) {
        this.backingComponent = backingComponent;
        this.jsonRepresentation = jsonRepresentation;
    }

    @Override
    public BaseComponent getBackingComponent() {
        return this.backingComponent.duplicate();
    }

    /**
     * Thrown an {@link UnsupportedOperationException} as
     * this method is not supported on immutable pages.
     */
    @Override
    public void setBackingComponent(BaseComponent backingComponent) {
        throw new UnsupportedOperationException();
    }

    /**
     * Thrown an {@link UnsupportedOperationException} as
     * this method is not supported on immutable pages.
     */
    @Override
    public void modifyBackingComponent(UnaryOperator<BaseComponent> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getJsonRepresentation() {
        return jsonRepresentation;
    }

    /**
     * Returns this instance, as it is already immutable.
     *
     * @return this instance
     */
    @Override
    public Page immutableCopy() {
        return this;
    }

    /**
     * Creates a new {@link ImmutablePage} instance with a
     * copy of the <tt>page</tt> backing component, and its
     * JSON representation.
     *
     * @param page page creating an immutable copy of
     * @return a new immutable copy
     */
    public static Page newInstance(Page page) {
        return new ImmutablePage(
                page.getBackingComponent().duplicate(),
                page.getJsonRepresentation());
    }

    /**
     * Creates a new {@link ImmutablePage} instance from
     * a {@link BaseComponent} object. The <tt>component</tt>
     * is copied and serialized, which will be used to create
     * the new immutable page instance.
     *
     * @param component component creating an immutable page from
     * @return a new immutable page from the component object
     */
    public static Page newInstanceFromComponent(BaseComponent component) {
        component = component.duplicate();
        String jsonRepresentation = PageSerializer.serializeToString(component);

        return new ImmutablePage(component, jsonRepresentation);
    }
}
