package ink.lanky.aivyl.plugin.action;

import ink.lanky.aivyl.config.AivylConfiguration;
import ink.lanky.aivyl.config.PluginConfiguration;
import ink.lanky.aivyl.controller.Action;
import ink.lanky.aivyl.domain.ApiAiResponse;
import ink.lanky.aivyl.plugin.auth.WeatherAuthenticator;
import ink.lanky.aivyl.plugin.domain.*;
import ink.lanky.aivyl.util.APIConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Component;

@Component
public class WeatherAction extends Action {
    
    private static Logger LOGGER = Logger.getLogger(WeatherAction.class.getName());
    
    @Transient
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Transient
    private static DecimalFormat numberFormat = new DecimalFormat("####.00");
    
    private PluginConfiguration weatherConfig = null;
    
    private APIConnection weatherAPIConnection;
    
    private HashMap<String, WeatherReportDay> weatherHistory;
    
    private WeatherAuthenticator authenticator = new WeatherAuthenticator();
    
    @Autowired
    public WeatherAction(AivylConfiguration config) {
        super(config);
    }

    @Override
    public ApiAiResponse execute(String sessionId, HashMap<String, Object> args) 
        throws Exception {
        //Configure if not already done
        if (weatherConfig == null) {
            weatherConfig = this.config.getPluginConfiguration("weather");
            authenticator.setConfig(weatherConfig);
            weatherAPIConnection = new APIConnection(
                    weatherConfig.getProperty("baseURL"),
                    authenticator,
                    false
            );
            weatherHistory = new HashMap();
        }
        
        ApiAiResponse response = new ApiAiResponse();
        
        response.setSource("Aivyl (OpenWeatherMap Plugin)");
        String date, city;
        
        city = args.get("geo-city").toString();
        
        if (args.get("date") == null || args.get("date").equals("")) {
            date = dateFormat.format(Date.from(Instant.now()));
        }
        else {
            date = args.get("date").toString();
        }
        LOGGER.info(city + " " + date);
        
        StringBuilder displayTextBuilder = new StringBuilder();
        displayTextBuilder.append("Weather in ");
        displayTextBuilder.append(city);
        displayTextBuilder.append(" on ");
        displayTextBuilder.append(date);
        displayTextBuilder.append('\n');
        
        String cityCode = city + ",US"; //TODO: ADD MORE COUNTRIES
        
        if (weatherHistory.containsKey(cityCode + date)) {
            LOGGER.info("Found city-code " + cityCode + " in cache");
        }
        else {
            LOGGER.info("Building forecast from API");
            Properties params = new Properties();
            params.setProperty("q", cityCode);
            WeatherReport report = 
                    (WeatherReport) weatherAPIConnection.get(
                                                            "forecast", 
                                                            null, 
                                                            params, 
                                                            WeatherReport.class);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            
            for (WeatherReportDay day : report.getList()) {
                if (!weatherHistory.containsKey(cityCode + dateFormat.format(Date.from(Instant.ofEpochSecond(day.getDt()))))) {
                    weatherHistory.put(cityCode + dateFormat.format(calendar.getTime()),
                                        day); //Save for later
                    calendar.add(Calendar.DATE, 1);
                }
            }
            if (!weatherHistory.containsKey(cityCode + date)) {
                LOGGER.fatal("Date out of range, should be within range of cached days");
                throw new Exception("Date out of range: should be within cached days");
            }
        }
        WeatherReportDay report = weatherHistory.get(cityCode + date);
        
        displayTextBuilder.append("Skies: " + report.getWeather().get(0).getMain() + "\n");
        displayTextBuilder.append("Wind: " + numberFormat.format(report.getWind().getSpeed()) + " mph at "
                                    + Integer.toString(report.getWind().getDeg()) + " degrees\n");
        displayTextBuilder.append("Temperature: "
                                + numberFormat.format(report.getMain().getTemp() - 273) + "C average, high of "
                                + numberFormat.format(report.getMain().getTemp_max() - 273) + "C and low of "
                                + numberFormat.format(report.getMain().getTemp_min() - 273) + "C\n");
        
        String displayText = displayTextBuilder.toString();
        
        response.setSpeech(displayText);
        
        response.setDisplayText(displayText);
        
        response.setFollowupEvent(null);
        response.setData(null);
        return response;
    }
}
