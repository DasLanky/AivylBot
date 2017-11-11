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
package ink.lanky.aivyl.rest;

import ink.lanky.aivyl.config.AivylConfiguration;
import ink.lanky.aivyl.controller.Action;
import ink.lanky.aivyl.domain.ApiAiResponse;
import ink.lanky.aivyl.domain.ApiAiPostBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("api")
@Api (value="Aivyl", description="API.AI")
public class AivylAPIResource {
    
    private static final Logger LOGGER = Logger.getLogger(AivylAPIResource.class.getName());
    
    @Autowired
    private AivylConfiguration config;
    
    @ApiOperation(value = "Query for parsed voice input and intent", response = ApiAiResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 401, message = "Resource access unauthorized"),
            @ApiResponse(code = 403, message = "Resource access forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Request handling unsuccessful")
    }
    )
    @RequestMapping(
            value = "/input",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiAiResponse> handleApiAiRequest(
        @RequestBody ApiAiPostBody body) 
        throws Exception {
        if (body.getResult().isActionIncomplete()) {
            return ResponseEntity.ok(null);
        }
        String actionName = body.getResult().getAction();
        Action action;
        if (actionName.contains(".")) {
            action = config.getPluginConfiguration(actionName.substring(0, actionName.indexOf('.')))
                                .getAction(actionName);
        }
        else {
            action = config.getPluginConfiguration(actionName)
                                    .getAction(actionName);
        }
        return ResponseEntity.ok(
                action.execute(
                        body.getSessionId(),
                        body.getResult().getParameters()));
    }
}
