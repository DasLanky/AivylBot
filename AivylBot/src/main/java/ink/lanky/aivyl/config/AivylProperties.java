/*
 * Copyright 2017 Pivotal Software, Inc..
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
package ink.lanky.aivyl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author Gcube
 */
@ConfigurationProperties(prefix="server")
public class AivylProperties {
    private String apiAiURL;
    private String apiAiKey;
    private String baseConfigURL;

    public String getBaseConfigURL() {
        return baseConfigURL;
    }

    public void setBaseConfigURL(String baseConfigURL) {
        this.baseConfigURL = baseConfigURL;
    }

    public String getApiAiURL() {
        return apiAiURL;
    }

    public void setApiAiURL(String apiAiURL) {
        this.apiAiURL = apiAiURL;
    }

    public String getApiAiKey() {
        return apiAiKey;
    }

    public void setApiAiKey(String apiAiKey) {
        this.apiAiKey = apiAiKey;
    }
    
}
