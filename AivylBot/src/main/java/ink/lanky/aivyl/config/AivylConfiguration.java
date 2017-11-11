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
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Stream;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gcube
 */
@Configuration
public class AivylConfiguration {
    private static final Logger LOGGER = Logger.getLogger(AivylConfiguration.class.getName());
    
    @Autowired
    private AivylProperties properties;
    
    private HashMap<String, PluginConfiguration> plugins;
    
    @Autowired
    public AivylConfiguration(AivylProperties properties) throws Exception {
        this.properties = properties;
        this.plugins = new HashMap();
        LOGGER.info("Loading plugins");
        /*
        File pluginDirectory = new File(AivylConfiguration.class.getResource("/plugins").toURI());
        for (File pluginFile : pluginDirectory.listFiles()) {
            PluginConfiguration tempConfig = PluginConfiguration.fromFile(pluginFile, this);
            plugins.put(tempConfig.getId(), tempConfig);
        }
        */
        URI pluginURI = AivylConfiguration.class.getResource("/plugins").toURI();
        try (FileSystem fileSystem = (pluginURI.getScheme().equals("jar")
                                        ? FileSystems.newFileSystem(pluginURI, Collections.<String, Object>emptyMap())
                                        : null)) {
            Path pluginPath = Paths.get(pluginURI);
            List<Path> paths = new ArrayList();
            Files.walkFileTree(pluginPath, new SimpleFileVisitor<Path>() { 
                @Override
                public FileVisitResult visitFile(Path pluginFile, BasicFileAttributes attrs) throws IOException {
                    paths.add(pluginFile);
                    return FileVisitResult.CONTINUE;
                }
            });
            for (Path pluginFile : paths) {
                PluginConfiguration tempConfig = PluginConfiguration.fromFile(pluginFile, this);
                plugins.put(tempConfig.getId(), tempConfig);
            }
        }
    }

    public AivylProperties getProperties() {
        return properties;
    }

    public void setProperties(AivylProperties properties) {
        this.properties = properties;
    }
    
    public PluginConfiguration getPluginConfiguration(String name) {
        return plugins.get(name);
    }
}
