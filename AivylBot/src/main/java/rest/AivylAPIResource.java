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
package rest;

import ink.lanky.aivyl.config.AivylConfiguration;
import ink.lanky.aivyl.controller.Action;
import ink.lanky.aivyl.domain.ApiAiResponse;
import ink.lanky.aivyl.domain.apiai.ApiAiPostBody;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AivylAPIResource {
    
    private static final Logger LOGGER = Logger.getLogger(AivylAPIResource.class.getName());
    
    @Autowired
    private AivylConfiguration config;
    
    @RequestMapping(
            value = "/post",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiAiResponse> handleApiAiRequest(
            @RequestBody ApiAiPostBody body) {
        if (body.getResult().isActionIncomplete()) {
            return ResponseEntity.ok(null);
        }
        String intentName = body.getResult().getMetadata().getIntentName();
        String actionName = body.getResult().getAction();
        Action action = config.getPluginConfiguration(intentName)
                                .getAction(actionName);
        return ResponseEntity.ok(
                action.execute(
                        body.getSessionId(),
                        body.getResult().getParameters()));
    }
    
}
