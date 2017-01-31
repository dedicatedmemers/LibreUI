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

package net.astromc.libreui.book;

import net.astromc.libreui.book.page.ImmutablePage;
import net.astromc.libreui.book.page.Page;
import net.astromc.libreui.book.page.PageSerializer;
import net.astromc.libreui.core.LibreUI;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Represents a book object acting as a collections of
 * {@link Page}s. This representation has built-in
 * caching for its JSON representation, this is done
 * in order to improve performance when reusing the
 * same book object. The JSON representation is lazily
 * generated when requested, if no cache is available.
 * The generated representation will automatically become
 * the new cached value, until it is invalidated.
 * <p/>
 *
 * All {@link Page}s added to this book will implicitly
 * be copied, to an immutable implementation as described
 * in the {@link Page#immutableCopy()} method documentation.
 * This is essential to assure full control of pages modifications,
 * and retain proper cache invalidation.
 * <p/>
 *
 * All modification operations such as adding, removing,
 * or modifying pages will effectively invalidate the JSON
 * representation of this book. Modifying pages in this
 * book may only be done using designated page modification
 * methods, as all methods returning pages will return
 * immutable pages, which cannot be modified.
 * <p/>
 *
 * All index based methods delegates to a {@link List},
 * therefore using indexes out of bounds will thrown an
 * {@link IndexOutOfBoundsException}. This is not mentioned
 * in the method documentation, as it explicitly declared
 * in this class specification.
 * <p/>
 *
 * Methods modifying the structure of this book, or its
 * contained {@link Page}s has a Builder like syntax,
 * returning the same book object. This is to allow chained
 * modifications to the book, making it easier to modify
 * books after their initial construction.
 *
 * @implNote The <tt>author</tt> and <tt>title</tt> book
 * attributes are not included as they are not displayed
 * in the actual book GUI on the client, therefore regarding
 * them entirely useless for our use. However the {@link PageSerializer}
 * does include them with empty values, as they are required
 * to fulfill the book data.
 */
public final class Book {
    private final List<Page> pages;

    private Book(List<Page> pages) {
        this.pages = pages;
    }

    /**
     * Adds a new <tt>page</tt> at the end of this book.
     *
     * @param page page to added
     * @return this book instance
     */
    public Book addPage(Page page) {
        Page assignedPage = page.immutableCopy();
        this.pages.add(assignedPage);

        this.invalidateCache();
        return this;
    }

    /**
     * Adds the new <tt>pages</tt> at the end of this book.
     *
     * @param pages pages to add
     * @return this book instance
     */
    public Book addPages(Page... pages) {
        boolean modified = false;

        for (Page page : pages) {
            Objects.requireNonNull(page, "page");

            Page assignedPage = page.immutableCopy();
            this.pages.add(assignedPage);

            modified = true;
        }

        if (modified) {
            this.invalidateCache();
        }

        return this;
    }

    /**
     * Companion utility method to the {@link Book#addPages(Page...)}
     * method. This method takes any {@link Collection} of {@link Page}s
     * and converts them into an array using {@link Collection#toArray(Object[])}
     * and delegates it to {@link Book#addPages(Page...)}.
     *
     * @param pages pages to add
     * @return this book instance
     */
    public Book addPages(Collection<Page> pages) {
        return this.addPages(pages.toArray(new Page[pages.size()]));
    }

    /**
     * Inserts a <tt>page</tt> at the given <tt>index</tt>.
     *
     * @param index index to insert at
     * @param page page inserted
     * @return this book instance
     */
    public Book insertPage(int index, Page page) {
        Page assignedPage = page.immutableCopy();
        this.pages.add(index, assignedPage);

        this.invalidateCache();
        return this;
    }

    /**
     * Removes a <tt>page</tt> at the given <tt>index</tt>.
     *
     * @param index index of page
     * @return this book instance
     */
    public Book removePage(int index) {
        this.pages.remove(index);

        this.invalidateCache();
        return this;
    }

    /**
     * Sets a <tt>page</tt> at the given <tt>index</tt>.
     *
     * @param index index to assign the page
     * @param page the page being set
     * @return this book instance
     */
    public Book setPage(int index, Page page) {
        Page assignedPage = page.immutableCopy();
        this.pages.set(index, assignedPage);

        this.invalidateCache();
        return this;
    }

    /**
     * Modifies the page at <tt>index</tt> using the <tt>componentOperator</tt>.
     *
     * @param index the index of the page
     * @param componentOperator the component operator
     * @return this book instance
     */
    public Book modifyPage(int index, UnaryOperator<BaseComponent> componentOperator) {
        Page page = this.pages.get(index);

        BaseComponent pageComponent = page.getBackingComponent();
        BaseComponent modifiedComponent = componentOperator.apply(pageComponent);

        Page assignedPage = ImmutablePage.newInstanceFromComponent(modifiedComponent);

        this.pages.set(index, assignedPage);

        this.invalidateCache();
        return this;
    }

    /**
     * Returns a {@link Stream} of the {@link Page}s registered in this book.
     *
     * @return a {@link Stream} of the {@link Page}s registered in this book
     */
    public Stream<Page> getPagesAsStream() {
        return pages.stream();
    }

    /**
     * Gets the page at the specific <tt>index</tt>.
     *
     * @param index index of page in book
     * @return the page at the index
     */
    public Page getPage(int index) {
        return pages.get(index);
    }

    public String getJsonRepresentation() {
        return LibreUI.getSerializedCache().getUnchecked(this);
    }

    /**
     * Invalidates the cached JSON representation of this book.
     */
    private void invalidateCache() {
        LibreUI.getSerializedCache().invalidate(this);
    }

    /**
     * A Builder pattern implementation for the {@link Book}
     * class. Unlike the {@link Book} class, the builder class
     * does not make pages immutable copies, until the {@link
     * Book} object is created.
     */
    public static class Builder {
        private Page frontPage;
        private List<Page> additionalPages;

        /**
         * Constructs a new {@link Book.Builder} with a predefined
         * <tt>frontPage</tt>.
         *
         * @param frontPage first page of the book
         */
        public Builder(Page frontPage) {
            this.frontPage(frontPage);
            this.additionalPages = new ArrayList<>();
        }

        /**
         * Sets the <tt>frontPage</tt> for this builder.
         *
         * @param frontPage the new front page
         * @return this builder instance
         */
        public Builder frontPage(Page frontPage) {
            this.frontPage = Objects.requireNonNull(frontPage);
            return this;
        }

        /**
         * Adds a page to the builder.
         *
         * @param page page to add
         * @return this builder instance
         */
        public Builder addPage(Page page) {
            this.additionalPages.add(Objects.requireNonNull(page));
            return this;
        }

        /**
         * Adds an array of pages to the builder.
         *
         * @param pages pages to add
         * @return this builder instance
         */
        public Builder addPages(Page... pages) {
            Arrays.asList(pages).forEach(this::addPage);
            return this;
        }

        /**
         * Sets the <tt>frontPage</tt> and <tt>additionalPages</tt>.
         * for this builder.
         *
         * @param frontPage new front page
         * @param additionalPages additional pages
         * @return this builder isntance
         */
        public Builder pages(Page frontPage, Page... additionalPages) {
            this.additionalPages.clear();

            this.frontPage(frontPage);
            this.addPages(additionalPages);

            return this;
        }

        /**
         * Constructs a new {@link Book} object, with the
         * <tt>frontPage</tt> and <tt>additionalPages</tt> of
         * this builder. All the pages assigned to the book,
         * is converted to immutable copies.
         *
         * @return a new {@link Book} object
         */
        public Book build() {
            List<Page> pages = new ArrayList<>();

            pages.add(this.frontPage);
            pages.addAll(this.additionalPages);

            Book book = new Book(pages);
            book.pages.replaceAll(Page::immutableCopy);

            return book;
        }
    }
}
