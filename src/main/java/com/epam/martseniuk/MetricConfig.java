package com.epam.martseniuk;

import com.netflix.servo.BasicMonitorRegistry;
import com.netflix.servo.MonitorRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.statsd.StatsdMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Roman_Martseniuk
 */
@Configuration
public class MetricConfig {

    @Value("${metrics.graphite.prefix}")
    private String prefix;

    @Value("${metrics.graphite.host}")
    private String host;

    @Value("${metrics.graphite.port}")
    private int port;

    @Bean
    public MonitorRegistry metricRegistry() {
        return new BasicMonitorRegistry();
    }

    @Bean
    @ExportMetricWriter
    MetricWriter metricWriter() {
        return new GraphiteMetricWriter(prefix, host, port);
//        return new StatsdMetricWriter(prefix, host, port);
    }
}
