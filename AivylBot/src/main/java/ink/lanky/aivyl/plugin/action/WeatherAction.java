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
import ink.lanky.aivyl.domain.ApiAiResponse;
import ink.lanky.aivyl.util.APIConnection;
import java.util.HashMap;

/**
 *
 * @author Gcube
 */
public class WeatherAction extends Action {
    
    private APIConnection weatherAPIConnection;

    @Override
    public ApiAiResponse execute(String sessionId, HashMap<String, Object> args) {
        ApiAiResponse response = new ApiAiResponse();
        
        response.setSource("Aivyl (OpenWeatherMap Plugin)");
        response.setSpeech("Here's the weather in " + args.get("geo-city") + ".");
        //TODO: Actually implement
        //Remember to cache weather data for 10 minutes or longer per city!
        response.setDisplayText("Placeholder: weather in " 
                                    + args.get("geo-city")
                                    + ", date is "
                                    + args.get("date"));
        response.setFollowupEvent(null);
        response.setData(null);
        return response;
    }

    public APIConnection getWeatherAPIConnection() {
        return weatherAPIConnection;
    }

    public void setWeatherAPIConnection(APIConnection weatherAPIConnection) {
        this.weatherAPIConnection = weatherAPIConnection;
    }
    
}
