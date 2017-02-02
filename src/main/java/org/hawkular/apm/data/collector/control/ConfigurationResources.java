/*
 * Copyright 2017 Juraci Paixão Kröhling
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.apm.data.collector.control;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

/**
 * @author Juraci Paixão Kröhling
 */
public class ConfigurationResources {
    private static ConfigurationResources INSTANCE;

    private String[] parameters;
    private CommandLine cmd;
    private Configuration configuration;

    private ConfigurationResources(String[] parameters) {
        this.parameters = parameters;
        prepareCommandLineOptions();
    }

    public static ConfigurationResources build(String[] parameters) {
        INSTANCE = new ConfigurationResources(parameters);
        return INSTANCE;
    }

    private void prepareCommandLineOptions() {
        Options options = new Options();
        options.addOption("b", "bind", true, "IP address to bind to");
        options.addOption("p", "port", true, "Port to bind to");
        options.addOption("c", "conf", true, "Configuration file to use");
        options.addOption("hb", "healthcheck-bind", true, "IP address to bind the health check service to");
        options.addOption("hp", "healthcheck-port", true, "Port to bind the health check service to");

        try {
            cmd = new DefaultParser().parse(options, parameters, false);
        } catch (ParseException e) {
            throw new RuntimeException("Could not parse configuration file", e);
        }

        Reader configurationReader;
        String configurationFilePath = "conf/configuration.yml";
        if (cmd.hasOption("c")) {
            configurationFilePath = cmd.getOptionValue("c");
        }

        if (!Files.exists(Paths.get(configurationFilePath))) {
            throw new IllegalStateException(String.format("The configuration file [%s] does not exist.", configurationFilePath));
        }

        try {
            configurationReader = new FileReader(configurationFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not load configuration file", e);
        }

        YamlReader reader = new YamlReader(configurationReader);
        try {
            configuration = reader.read(Configuration.class);
        } catch (YamlException e) {
            throw new RuntimeException("Could not read configuration file", e);
        }

        if (cmd.hasOption("b")) {
            configuration.setBind(cmd.getOptionValue("b"));
        }

        if (cmd.hasOption("p")) {
            configuration.setPort(Integer.parseInt(cmd.getOptionValue("p")));
        }

        if (cmd.hasOption("hb")) {
            configuration.setHealthcheckBind(cmd.getOptionValue("b"));
        }

        if (cmd.hasOption("hp")) {
            configuration.setHealthcheckPort(Integer.parseInt(cmd.getOptionValue("p")));
        }
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public static ConfigurationResources getInstance() {
        return INSTANCE;
    }

}
