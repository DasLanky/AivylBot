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
package ink.lanky.aivyl.plugin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherReportDay {
    private long dt;
    private List<WeatherReportStatus> weather;
    private WeatherReportWind wind;
    private WeatherReportMain main;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public WeatherReportMain getMain() {
        return main;
    }

    public void setMain(WeatherReportMain main) {
        this.main = main;
    }

    public WeatherReportWind getWind() {
        return wind;
    }

    public void setWind(WeatherReportWind wind) {
        this.wind = wind;
    }

    public List<WeatherReportStatus> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherReportStatus> weather) {
        this.weather = weather;
    }
}
