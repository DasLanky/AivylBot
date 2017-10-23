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
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Gcube
 */
public class PluginConfiguration {
    
    private static final Logger LOGGER = Logger.getLogger(PluginConfiguration.class.getName());
    
    private String id;
    private HashMap<String, Action> actions;
    private HashMap<String, String> properties;
    
    public static PluginConfiguration fromFile(Path file, AivylConfiguration config)
            throws IOException {
        LOGGER.info("Loading plugin from file: " + file.toString());
        PluginConfiguration tempConfig = new PluginConfiguration();
        String fname = file.getFileName().toString();
        tempConfig.setId(fname.substring(0, fname.indexOf(".props")));
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
            LOGGER.info(split[0] + ": " + split[1]);
        }
        LOGGER.info("Loading actions for plugin " + file.toString());
        while (s.hasNextLine()) {
            line = s.nextLine();
            split = line.replaceAll(" ", "").split("=");
            try {
//                Action tempAction = Class.forName(split[1])
//                                .asSubclass(Action.class)
//                                .newInstance();
                Action tempAction = PluginConfiguration
                                            .class
                                            .getClassLoader()
                                            .loadClass(split[1])
                                            .asSubclass(Action.class)
                                            .getConstructor(AivylConfiguration.class)
                                            .newInstance(config);
                acts.put(split[0], tempAction);
            }
            catch (Exception e) {
                LOGGER.fatal("Plugin class not found");
                e.printStackTrace();
            }
            LOGGER.info(split[0] + ": " + split[1]);
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
