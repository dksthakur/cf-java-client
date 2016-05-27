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

package org.cloudfoundry.uaa.authorizations;

import org.junit.Test;

public final class AuthorizeByAuthorizationCodeGrantApiRequestTest {

    @Test(expected = IllegalStateException.class)
    public void noClientId() {
        AuthorizeByAuthorizationCodeGrantApiRequest.builder()
            .responseType(ResponseType.CODE)
            .state("test-state")
            .build();
    }

    @Test(expected = IllegalStateException.class)
    public void noResponseType() {
        AuthorizeByAuthorizationCodeGrantApiRequest.builder()
            .clientId("test-client-id")
            .state("test-state")
            .build();
    }

    @Test(expected = IllegalStateException.class)
    public void noState() {
        AuthorizeByAuthorizationCodeGrantApiRequest.builder()
            .clientId("test-client-id")
            .responseType(ResponseType.CODE)
            .build();
    }

    @Test
    public void valid() {
        AuthorizeByAuthorizationCodeGrantApiRequest.builder()
            .clientId("test-client-id")
            .responseType(ResponseType.CODE)
            .state("test-state")
            .build();
    }

}
