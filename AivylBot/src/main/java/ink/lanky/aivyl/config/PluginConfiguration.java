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

import ink.lanky.aivyl.controller.Action;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.log4j.Logger;

/**
 *
 * @author Gcube
 */
public class PluginConfiguration {
    
    private static final Logger LOGGER = Logger.getLogger(PluginConfiguration.class.getName());
    
    private String id;
    private HashMap<String, Action> actions;
    private HashMap<String, String> properties;
    
    public static PluginConfiguration fromFile(File file) throws Exception {
        LOGGER.info("Loading plugin from file: " + file.getName());
        PluginConfiguration tempConfig = new PluginConfiguration();
        tempConfig.setId(file.getName().substring(0, file.getName().indexOf(".props")));
        HashMap<String, String> props = new HashMap();
        HashMap<String, Action> acts = new HashMap();
        Scanner s = new Scanner(file);
        String line = "";
        String[] split;
        while (s.hasNextLine()) {
            line = s.nextLine();
            if (line.equals("actions")) break;
            split = line.replaceAll(" ", "").split("=");
            props.put(split[0], split[1]);
        }
        while (s.hasNextLine()) {
            line = s.nextLine();
            split = line.replaceAll(" ", "").split("=");
            acts.put(split[0], 
                    Class.forName(split[1])
                            .asSubclass(Action.class)
                            .newInstance());
        }
        tempConfig.setProperties(props);
        tempConfig.setActions(acts);
        return tempConfig;
    }

    public HashMap<String, Action> getActions() {
        return actions;
    }

    public void setActions(HashMap<String, Action> actions) {
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
    
    public Action getAction(String name) {
        return actions.get(name);
    }
}
