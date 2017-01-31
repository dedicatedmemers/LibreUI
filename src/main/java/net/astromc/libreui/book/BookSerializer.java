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

import net.astromc.libreui.book.page.Page;

import java.util.stream.Collectors;

/**
 * A {@link Book} seralizer which formats books
 * to a JSON-like String format. This format is accepted
 * by the game to convert it into item NBT data.
 */
public enum BookSerializer {;

    /**
     * Mandatory <tt>author</tt> attribute with an empty value.
     */
    private static final String AUTHOR_ATTRIBUTE = "author:\"\"";
    /**
     * Mandatory <tt>title</tt> attribute with an empty value.
     */
    private static final String TITLE_ATTRIBUTE = "title:\"\"";
    /**
     * The <tt>pages</tt> key used to serialize the book to a
     * JSON-like String format, which the game may accept to
     * convert it to NBT data.
     */
    private static final String PAGES_KEY = "pages";
    /**
     * The JSON-like formatting scheme.
     */
    private static final String BOOK_SERIALIZED_FORMAT =
            "{" + AUTHOR_ATTRIBUTE + ","
                    + TITLE_ATTRIBUTE + ","
                    + PAGES_KEY + ":[%s]}";

    /**
     * The String used to separate <tt>pages</tt> values
     * in the serialized String.
     */
    private static final String PAGES_DELIMITER = ",";

    /**
     * Returns a serialized {@link Book} in a JSON-like
     * String format from the specified <tt>book</tt> object.
     *
     * @param book the book being serialized to a String
     * @return serialized JSON-like String format of the specified book
     */
    public static String serializeToString(Book book) {
        String pages = book.getPagesAsStream()
                .map(Page::getJsonRepresentation)
                .collect(Collectors.joining(PAGES_DELIMITER));

        return String.format(BOOK_SERIALIZED_FORMAT, pages);
    }
}
