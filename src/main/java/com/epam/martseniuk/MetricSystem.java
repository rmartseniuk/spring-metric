package com.epam.martseniuk;

import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Roman_Martseniuk
 */
@Component
@ExportMetricWriter
public class MetricSystem {

//    @Autowired
//    private MetricRegistry metricRegistry;

//    private Counter geolocationWriteRequestCount;

    @PostConstruct
    public void init() {
//        GraphiteReporter consoleReporter =
//                GraphiteReporter.forRegistry(metricRegistry)
//                        .convertRatesTo(TimeUnit.SECONDS)
//                        .convertDurationsTo(TimeUnit.MILLISECONDS)
//                        .build(new Graphite("localhost", 2003));
//        consoleReporter.start(10, TimeUnit.SECONDS);
//        geolocationWriteRequestCount =
//                metricRegistry.counter("geolocationWriteRequestCount");
    }

//    public Counter geolocationWriteRequestCount() {
//        return geolocationWriteRequestCount;
//    }
}
