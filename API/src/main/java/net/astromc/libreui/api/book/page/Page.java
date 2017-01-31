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

package net.astromc.libreui.api.book.page;

import com.google.common.base.Preconditions;
import net.astromc.libreui.api.book.Book;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Arrays;
import java.util.function.UnaryOperator;
import java.util.stream.StreamSupport;

/**
 * A {@link Book} page representation. A page is a functional
 * and visual component description. A page object wraps around
 * a backing {@link BaseComponent component}, which describes
 * it's functionality and appearance.
 * <p/>
 *
 * Much like {@link Book} objects, pages also cached their own
 * JSON representations. And the relation to their backing component
 * is like the {@link Book} objects relation to their pages. If the
 * backing component is exposed, the page is unable to invalidate
 * its cached accordingly to component modifications. All component
 * modifying methods will implicitly invalidate the cache for the
 * page object.
 * <p/>
 *
 * The page object has two ways to modifying its backing component,
 * it can either override it entirely, or modify it using the build
 * -in modify method. {@link Page#setBackingComponent(BaseComponent)}
 * and {@link Page#modifyBackingComponent(UnaryOperator)} respectively.
 */
public interface Page {
    /**
     * Returns a copy of the backing component. A copy
     * of the backing component must be used, because if
     * the component is exposed the <tt>Page</tt> no longer
     * has full control over its modifications, making it
     * impossible to invalidate the potentially outdated
     * JSON representation cache.
     *
     * @return a copy of the backing component
     */
    BaseComponent getBackingComponent();

    /**
     * Sets the backing component to a copy of the
     * specified <tt>backingComponent</tt>, to assure
     * control over modifications. This method will
     * invalidate the cached JSON representation, if
     * any is set.
     *
     * @param backingComponent the new backing component
     */
    void setBackingComponent(BaseComponent backingComponent);

    /**
     * Sets the backing component to a copy of the result
     * of the <tt>componentOperator</tt>.
     *
     * @param componentOperator the component operator
     */
    void modifyBackingComponent(UnaryOperator<BaseComponent> componentOperator);

    /**
     * Returns a JSON representation of this page. The
     * JSON representation will reflection the backing
     * component, not the actual <tt>Page</tt> object.
     * The JSON representation is cached for increased
     * performance, if there is no cache, a new JSON
     * representation will be generated using {@link
     * PageSerializer#serializeToString(Page)}, and
     * cache the return value.
     *
     * @return JSON representation of this page
     */
    String getJsonRepresentation();

    /**
     * Creates a new immutable copy of this page. The
     * copy will have an identical copy of the current
     * JSON representation and backing component.
     *
     * @return immutable copy of this page
     */
    Page immutableCopy();


    /**
     * Instantiates a new {@link Page} object with its backing
     * component set to <tt>component</tt>. The returned page
     * is mutable.
     *
     * @param component the backing component for the page
     * @return a new mutable page backed by the specified component
     */
    static Page newInstance(BaseComponent component) {
        return new SimplePage(component.duplicate());
    }

    /**
     * Creates a new {@link Page} object with the specified components
     * combined as its backing component. The components is combined
     * into a {@link TextComponent}. The first component in the
     * <tt>components</tt> array set as the base component for the
     * new {@link TextComponent}, the remaining (in any) is added as
     * extra components. The returned page is mutable.
     *
     * @param component the first backing components for the page
     * @param components the other backing components for the page
     * @return a new mutable page backed by the specified components
     * @throws IllegalArgumentException if components is empty}
     */
    static Page newInstance(BaseComponent component, Iterable<BaseComponent> components) throws IllegalArgumentException {
        component = Preconditions.checkNotNull(component).duplicate();
        StreamSupport.stream(components.spliterator(), false)
                .map(BaseComponent::duplicate)
                .peek(Preconditions::checkNotNull)
                .forEach(component::addExtra);

        return new SimplePage(component);
    }

    /**
     * Creates a new {@link Page} from the specified builder.
     * This method takes the returned components from
     * {@link ComponentBuilder#create()} and delegates it to
     * the {@link Page#newInstance(BaseComponent, Iterable)}
     * method. The returned page is mutable.
     *
     * @param builder the builder creating the page from
     * @return a new mutable page constructed from the specified builder
     */
    static Page newInstance(ComponentBuilder builder) {
        BaseComponent[] components = builder.create();
        Preconditions.checkElementIndex(0, components.length, "builder is required to have at least one element");
        return newInstance(components[0], Arrays.asList(components).subList(1, components.length));
    }
}
