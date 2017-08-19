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
package ink.lanky.aivyl.util;

import ink.lanky.aivyl.controller.Authenticator;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 *
 * @author Gcube
 */
public class APIConnection {
    private Authenticator authenticator;
    private String endpointURL;
    
    public HttpEntity get(String baseURL,
                            Properties headers,
                            Properties params) throws Exception {
        String link = baseURL + "?";
        for (String key : params.stringPropertyNames()) {
            link += (key + "=" + (String)params.get(key) + "&");
        }
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        for (String key : headers.stringPropertyNames()) {
            connection.setRequestProperty(key, (String)headers.get(key));
        }
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", MediaType.APPLICATION_JSON_VALUE);
        //TODO: Complete
    }
}
