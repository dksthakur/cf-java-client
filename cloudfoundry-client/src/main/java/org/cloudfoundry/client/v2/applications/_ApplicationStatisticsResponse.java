/*
 * Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudfoundry.client.v2.applications;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.cloudfoundry.AllowNulls;
import org.immutables.value.Value;

import java.io.IOException;
import java.util.Map;

/**
 * The response payload for the Get Application Statistics operation.
 */
@JsonDeserialize(using = _ApplicationStatisticsResponse.ApplicationStatisticsResponseDeserializer.class)
@Value.Immutable
abstract class _ApplicationStatisticsResponse {

    /**
     * The instances
     */
    @AllowNulls
    abstract Map<String, InstanceStatistics> getInstances();

    static final class ApplicationStatisticsResponseDeserializer extends StdDeserializer<ApplicationStatisticsResponse> {

        private static final long serialVersionUID = -2925663073415059473L;

        ApplicationStatisticsResponseDeserializer() {
            super(ApplicationStatisticsResponse.class);
        }

        @Override
        public ApplicationStatisticsResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return ApplicationStatisticsResponse.builder()
                .instances(p.readValueAs(new TypeReference<Map<String, InstanceStatistics>>() {

                }))
                .build();
        }
    }

}
