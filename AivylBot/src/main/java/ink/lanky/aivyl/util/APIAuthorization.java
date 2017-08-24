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

import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;
import org.springframework.data.annotation.Transient;

/**
 *
 * @author Gcube
 */
public class APIAuthorization {
    
    @Transient
    private final Logger LOGGER = Logger.getLogger(APIAuthorization.class.getName());
    
    private String clientID;
    private String clientSecret;

    public APIAuthorization(String clientID, String clientSecret) {
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }
    
    public String digest() throws UnsupportedEncodingException {
        String result = "Basic"
                + javax.xml.bind.DatatypeConverter.printBase64Binary(
                        (clientID + ":" + clientSecret).getBytes("UTF-8"));
        return result;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    
}
