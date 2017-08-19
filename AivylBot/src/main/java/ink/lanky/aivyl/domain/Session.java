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
package ink.lanky.aivyl.domain;

import ink.lanky.aivyl.controller.Action;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.annotation.Id;

/**
 *
 * @author Gcube
 */
public class Session {
    @Id
    private String id;
    private String userName;
    private List<Action> actionHistory;
    private HashMap<String, Object> savedData;
    
    public void save(String objectId, Object object) {
        savedData.put(objectId, object);
    }
    
    public Object load(String objectId) {
        return savedData.get(objectId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Action> getActionHistory() {
        return actionHistory;
    }

    public void setActionHistory(List<Action> actionHistory) {
        this.actionHistory = actionHistory;
    }
}
