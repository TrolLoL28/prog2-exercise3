package weather.ctrl;

import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.GeoCoordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ParallelDownloader extends Downloader {
    @Override
    public int process(List<GeoCoordinates> koordinaten) throws ForecastException {
        long start = System.nanoTime();
        ExecutorService service = Executors.newFixedThreadPool(4);
        int zähler = 0;
        int i = 1;
        for (GeoCoordinates koordinate : koordinaten) {
            Future<String> zukünftige = service.submit(new Task());
            String fileName = save(koordinate, true, i);
            fileName += zukünftige;
            if(fileName != null){
                zähler++;
            }
            i++;
        }
        long end = System.nanoTime();
        long dauer = (end - start);
        System.out.println("Millisekunden: "+dauer/100000);
        return zähler;
    }

    static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "downloadfile" + System.currentTimeMillis();
        }
    }
}