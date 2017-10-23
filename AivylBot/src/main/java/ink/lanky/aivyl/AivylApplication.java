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
package ink.lanky.aivyl;

import ink.lanky.aivyl.config.AivylConfiguration;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gcube
 */

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
public class AivylApplication {
    
    private static final Logger LOGGER = Logger.getLogger(AivylApplication.class.getName());
    
    @Autowired
    private AivylConfiguration config;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AivylApplication.class, args);
    }
    
    public void printHeaderText() throws FileNotFoundException {
        Scanner s = new Scanner(new File(this.config.getProperties().getBaseConfigURL() + "/aivyl.txt"));
        while (s.hasNextLine()) {
            System.out.println(s.nextLine());
        }
        s.close();
    }
    
    @Autowired
    public AivylApplication(AivylConfiguration config) throws FileNotFoundException {
        this.config = config;
        this.printHeaderText();
        LOGGER.info("Configuration complete");
    }

}
