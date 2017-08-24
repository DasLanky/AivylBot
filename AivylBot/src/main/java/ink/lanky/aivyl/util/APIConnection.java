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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ink.lanky.aivyl.controller.Authenticator;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Gcube
 */
public class APIConnection {
    
    private static final Logger LOGGER = Logger.getLogger(APIConnection.class.getName());
    
    private Authenticator authenticator;
    private String endpointURL;
    
    private boolean autoReset = false;
    
    public APIConnection(String endpointURL, boolean shouldAutoRefresh) {
        this.endpointURL = endpointURL;
        this.autoReset = shouldAutoRefresh;
    }
    
    public APIConnection(String endpointURL) {
        this.endpointURL = endpointURL;
    }
    
    public static String httpRequest(
                            String baseURL,
                            String relativeURL,
                            String method,
                            Properties headers,
                            Properties params,
                            String data) throws Exception {
        String link = baseURL + "/" + relativeURL + "?";
        for (String key : params.stringPropertyNames()) {
            link += (key + "=" + (String)params.get(key) + "&");
        }
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        for (String key : headers.stringPropertyNames()) {
            connection.setRequestProperty(key, (String)headers.get(key));
        }
        connection.setRequestProperty("Accept", MediaType.APPLICATION_JSON_VALUE);
        connection.setRequestMethod(method);
        
        connection.setDoOutput(true);
        
        if (data != null && !data.equals("")) {
            BufferedWriter connectionWriter = new BufferedWriter(
                                                new OutputStreamWriter(
                                                    connection.getOutputStream()));
            connectionWriter.write(data);
            connectionWriter.close();
        }
        
        Scanner inputScanner = new Scanner(connection.getInputStream());
        StringBuilder outputBuilder = new StringBuilder();
        
        if (inputScanner.hasNextLine()) {
            outputBuilder.append(inputScanner.nextLine());
        }
        
        while(inputScanner.hasNextLine()) {
            outputBuilder.append("\n");
            outputBuilder.append(inputScanner.nextLine());
        }
        
        return outputBuilder.toString();
    }
    
    private Object exchange(String relativeLink,
                            HttpMethod method,
                            Object bodyObject,
                            Properties headers,
                            Properties params,
                            Class ReturnType)
            throws Exception {
        LOGGER.info("AribaAPIConnection exchange: " + method.name());

        UriComponentsBuilder builder = UriComponentsBuilder
                                            .fromHttpUrl(
                                                this.endpointURL + relativeLink);

        if (params != null) {
            for (String propertyName : params.stringPropertyNames()) {
                builder.queryParam(propertyName, params.getProperty(propertyName));
            }
        }

        String link = builder.toUriString();
        
        if (this.autoReset) {
            this.authenticator.reset();
        }

        HttpHeaders _headers = new HttpHeaders();
        if (headers != null) {
            for (String propertyName : headers.stringPropertyNames()) {
                _headers.set(propertyName, headers.getProperty(propertyName));
            }
        }
        _headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String body = "";
        if (method == HttpMethod.POST) {
            ObjectWriter JSONWriter
                    = new ObjectMapper().writer().withDefaultPrettyPrinter();
            body = JSONWriter.writeValueAsString(bodyObject);
            _headers.setContentType(MediaType.APPLICATION_JSON);
            LOGGER.info(body);
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(body, _headers);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(link, method, httpEntity, ReturnType).getBody();
    }

    public Object get(String relativeLink,
                        Properties headers,
                        Properties params,
                        Class ReturnType)
            throws Exception {
        return this.exchange(relativeLink,
                            HttpMethod.GET, 
                            null, 
                            headers,
                            params,
                            ReturnType);
    }

    public Object post(String relativeLink,
                        Object body,
                        Properties headers,
                        Properties params,
                        Class ReturnType)
            throws Exception {
        return this.exchange(relativeLink,
                            HttpMethod.GET, 
                            body, 
                            headers,
                            params,
                            ReturnType);
    }
}
