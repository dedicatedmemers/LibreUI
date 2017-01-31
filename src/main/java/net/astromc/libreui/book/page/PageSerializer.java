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

import com.google.gson.JsonPrimitive;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

/**
 * A serializer used to serialize {@link Page} objects
 * into JSON formatted String representations, which is
 * accepted by the game when converted to NBT data.
 */
public enum PageSerializer {;

    /**
     * The maximum length of the JSON formatted page.
     */
    private static final int PAGE_JSON_FORMATTED_MAX_LENGTH = 32767;

    /**
     * Returns a String formatted serialized page. A plain
     * JSON representation is return if the backing component
     * of the page has no formatting {@link BaseComponent#hasFormatting()}.
     * If the component has formatting, the JSON serialized
     * backing component will be wrapped in a {@link JsonPrimitive}
     * and the returns the {@link JsonPrimitive#toString() toString}
     * of that object. This will serialize the JSON primitive
     * object, which escapes the special characters.
     *
     * @param page the page being serialized
     * @return a serialized String formatted page
     */
    public static String serializeToString(Page page) {
        return serializeToString(page.getBackingComponent());
    }

    /**
     * Serializes a {@link BaseComponent} as described in the
     * {@link PageSerializer#serializeToString(Page)} method
     * documentation.
     *
     * @param component component being serialized
     * @return a serialized String formatted component
     */
    public static String serializeToString(BaseComponent component) {
        String jsonRepresentation = ComponentSerializer.toString(component);

        jsonRepresentation = new JsonPrimitive(jsonRepresentation).toString();

        if (jsonRepresentation.length() > PAGE_JSON_FORMATTED_MAX_LENGTH) {
            throw new IllegalStateException(
                    "JSON formatted Page exceeds max length (" +
                            PAGE_JSON_FORMATTED_MAX_LENGTH + ")");
        }

        return jsonRepresentation;
    }
}
