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

/**
 * @author Juraci Paixão Kröhling
 */
public class Configuration {
    private String bind;
    private int port;

    private String dataPublisherHostname;
    private int dataPublisherPort;

    private String healthcheckBind;
    private int healthcheckPort;

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDataPublisherHostname() {
        return dataPublisherHostname;
    }

    public void setDataPublisherHostname(String dataPublisherHostname) {
        this.dataPublisherHostname = dataPublisherHostname;
    }

    public int getDataPublisherPort() {
        return dataPublisherPort;
    }

    public void setDataPublisherPort(int dataPublisherPort) {
        this.dataPublisherPort = dataPublisherPort;
    }

    public String getHealthcheckBind() {
        return healthcheckBind;
    }

    public void setHealthcheckBind(String healthcheckBind) {
        this.healthcheckBind = healthcheckBind;
    }

    public int getHealthcheckPort() {
        return healthcheckPort;
    }

    public void setHealthcheckPort(int healthcheckPort) {
        this.healthcheckPort = healthcheckPort;
    }
}
