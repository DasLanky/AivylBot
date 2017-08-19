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
package ink.lanky.aivyl.plugin.action;

import ink.lanky.aivyl.controller.Action;
import ink.lanky.aivyl.domain.Response;
import ink.lanky.aivyl.util.APIConnection;

/**
 *
 * @author Gcube
 */
public class WeatherAction extends Action {
    
    private APIConnection weatherAPIConnection;

    @Override
    public Response execute(String sessionId, Object[] args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public APIConnection getWeatherAPIConnection() {
        return weatherAPIConnection;
    }

    public void setWeatherAPIConnection(APIConnection weatherAPIConnection) {
        this.weatherAPIConnection = weatherAPIConnection;
    }
    
}
