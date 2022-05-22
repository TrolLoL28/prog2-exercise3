package weather.ctrl;


import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.DailyDataPoint;
import tk.plogitech.darksky.forecast.model.Forecast;
import tk.plogitech.darksky.forecast.model.HourlyDataPoint;

import java.util.ArrayList;
import java.util.List;

public class WeatherController {

    public static String apiKey = "ab5c55091bfde0864c41b337f1c66af5";



    public void process(GeoCoordinates location) throws MyExeption {
        System.out.println("process "+location); //$NON-NLS-1$
        Forecast data = getData(location);

        List<DailyDataPoint> dailyDataPoints = data.getDaily().getData(); //Für Streams - Täglich (Für Temp)
        List<HourlyDataPoint> hourlyDataPoints = data.getHourly().getData(); //Für Streams - Stündlich (Für Wind)

        //TODO implement Error handling
        //Exception e weiter unten

        //TODO implement methods for
        // count the daily values
        int dailyData = dailyDataPoints.size();
        System.out.println("Anzahl der Datensätze: " + dailyData);
        // highest temperature
        double highestTemp = dailyDataPoints.stream().mapToDouble(DailyDataPoint::getTemperatureHigh).max().orElseThrow();
        System.out.println("Höchste Temperatur der letzten " + dailyData + " Tage : " + highestTemp +"°C");
        // average temperature
        double averageTemp = dailyDataPoints.stream().mapToDouble(DailyDataPoint::getTemperatureHigh).average().orElseThrow();
        System.out.println("Durchschnittliche Temperatur der letzten " + dailyData + " Tage : " + averageTemp + "°C");
        // implement a Comparator for the Windspeed
        //double maxWindspeed = hourlyDataPoints.stream().mapToDouble(HourlyDataPoint::getWindSpeed).max().orElseThrow();
    }


    public Forecast getData(GeoCoordinates location) throws MyExeption {
        ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey(apiKey))
                .location(location)
                .build();

        Forecast forecast = null;
        DarkSkyJacksonClient client = new DarkSkyJacksonClient();
        //Forecast forecast = client.forecast(request);
        try {
            forecast = client.forecast(request);
        }
        catch (Exception e) {
            throw new MyExeption("getData error");
        }
        return forecast;
    }
}