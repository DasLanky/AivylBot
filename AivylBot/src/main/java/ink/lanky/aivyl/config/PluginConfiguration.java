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
package ink.lanky.aivyl.config;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Gcube
 */
public class PluginConfiguration {
    private String id;
    private HashMap<String, String> actions;
    private HashMap<String, String> properties;
    
    public static PluginConfiguration fromFile(File file) throws Exception {
        PluginConfiguration tempConfig = new PluginConfiguration();
        HashMap<String, String> props = new HashMap();
        HashMap<String, String> acts = new HashMap();
        Scanner s = new Scanner(file);
        String line = "";
        String[] split;
        while (s.hasNextLine()) {
            line = s.nextLine();
            if (line.equals("actions")) break;
            split = line.split(":");
            props.put(split[0], split[1]);
        }
        while (s.hasNextLine()) {
            line = s.nextLine();
            split = line.split(":");
            acts.put(split[0], split[1]);
        }
        tempConfig.setProperties(props);
        tempConfig.setActions(acts);
        return tempConfig;
    }

    public HashMap<String, String> getActions() {
        return actions;
    }

    public void setActions(HashMap<String, String> actions) {
        this.actions = actions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }
    
    public String getProperty(String name) {
        return properties.get(name);
    }
}
