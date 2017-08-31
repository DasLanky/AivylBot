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
package ink.lanky.aivyl.controller;

import ink.lanky.aivyl.config.AivylConfiguration;
import ink.lanky.aivyl.domain.ApiAiResponse;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gcube
 */
@Component
public abstract class Action {
    
    @Autowired
    protected AivylConfiguration config;

    public void setConfig(AivylConfiguration config) {
        this.config = config;
    }
    
    private static HashMap<String, Action> actionMap;
    
    static {
        actionMap = new HashMap();
    }
    
    public static HashMap<String, Action> getActionMap() {
        return actionMap;
    }
    
    public abstract ApiAiResponse execute(String sessionId, 
                                          HashMap<String, Object> args)
            throws Exception;
}
