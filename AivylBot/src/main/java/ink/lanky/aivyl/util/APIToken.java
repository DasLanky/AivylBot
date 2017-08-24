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

import org.apache.log4j.Logger;
import org.springframework.data.annotation.Transient;

/**
 *
 * @author Gcube
 */
public class APIToken {
    
    @Transient
    private final Logger LOGGER = Logger.getLogger(APIToken.class.getName());
    
    private long timeLastRefreshed = 0;
    private long expirationTime = 0;
    
    private String accessToken;
    private String refreshToken;

    public APIToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public APIToken(String accessToken, String refreshToken, long expirationTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expirationTime = expirationTime;
    }
    
    public boolean isExpired() {
        return (System.currentTimeMillis() / 1000) > (this.timeLastRefreshed + this.expirationTime);
    }
    
    public boolean isActive() {
        return (System.currentTimeMillis() / 1000) <= (this.timeLastRefreshed + this.expirationTime);
    }

    public long getTimeLastRefreshed() {
        return timeLastRefreshed;
    }

    public void setTimeLastRefreshed(long timeLastRefreshed) {
        this.timeLastRefreshed = timeLastRefreshed;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
}
