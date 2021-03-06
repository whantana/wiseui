/**
 * Copyright (C) 2011 Universität zu Lübeck, Institut für Telematik (ITM),
 *                             Research Academic Computer Technology Institute (RACTI)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.wisebed.wiseui.client.testbedselection.common;

/**
 * Constants for the testbed-selection-view and -place.
 *
 * @author Sönke Nommensen
 */
public enum TestbedSelectionParams {

    TESTBED_SELECTION_STRING("s"),
    TESTBED_VIEW_STRING("v"),
    MAP_VIEW("m"),
    DETAIL_VIEW("d"),
    RAW_WISEML_VIEW("r");

    private String value;

    TestbedSelectionParams(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }
}
