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
package ink.lanky.aivyl.plugin.auth;

import ink.lanky.aivyl.controller.Authenticator;
import java.util.Properties;

/**
 *
 * @author Gcube
 */
public class WeatherAuthenticator extends Authenticator {

    @Override
    public Properties getAuthHeaders() {
        return new Properties();
    }

    @Override
    public Properties getAuthParams() {
        Properties params = new Properties();
        params.setProperty("APPID",
                            config.getProperty("APIKEY"));
        return params;
    }

    @Override
    public void reset() {
        //No action
    }
    
}
