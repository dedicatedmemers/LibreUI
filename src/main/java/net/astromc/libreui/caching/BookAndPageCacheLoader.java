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

package net.astromc.libreui.caching;

import com.google.common.cache.CacheLoader;
import net.astromc.libreui.book.Book;
import net.astromc.libreui.book.BookSerializer;
import net.astromc.libreui.book.page.Page;
import net.astromc.libreui.book.page.PageSerializer;

//TODO: Javadoc
public final class BookAndPageCacheLoader extends CacheLoader<Object, String> {

    @Override
    public String load(Object object) {
        if (object instanceof Book) {
            return BookSerializer.serializeToString((Book) object);
        } else if (object instanceof Page) {
            return PageSerializer.serializeToString((Page) object);
        }

        return "";
    }
}