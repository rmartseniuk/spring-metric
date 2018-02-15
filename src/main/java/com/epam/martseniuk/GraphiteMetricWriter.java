package com.epam.martseniuk;

import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Roman_Martseniuk
 */
public class GraphiteMetricWriter implements MetricWriter {

    private final String prefix;
    private final String host;
    private final int port;

    public GraphiteMetricWriter(String host, int port) {
        this(null, host, port);
    }

    public GraphiteMetricWriter(String prefix, String host, int port) {
        this.prefix = removeTrailingDots(prefix);
        this.host = host;
        this.port = port;
    }

    private static String removeTrailingDots(String prefix) {
        String trimmedPrefix = StringUtils.hasText(prefix) ? prefix : null;
        while (trimmedPrefix != null && trimmedPrefix.endsWith(".")) {
            trimmedPrefix = trimmedPrefix.substring(0, trimmedPrefix.length() - 1);
        }

        return trimmedPrefix;
    }

    @Override
    public void increment(Delta<?> delta) {
        sendMetric(delta);
    }

    @Override
    public void reset(String s) {
        // Not implemented
    }

    @Override
    public void set(Metric<?> metric) {
        sendMetric(metric);
    }

    private void sendMetric(Metric<?> metric) {
        String fullMetric = metric.getName();

        if (this.prefix != null) {
            fullMetric = this.prefix + "." + metric.getName();
        }

        try (Socket socket = new Socket(this.host, this.port);
             OutputStream stream = socket.getOutputStream()) {
            String payload = String.format("%s %d %d%n", fullMetric,
                    metric.getValue().intValue(), metric.getTimestamp().getTime() / 1000);
            System.err.println(payload);
            stream.write(payload.getBytes());
        } catch (IOException e) {
            System.err.println("Failed to write metric.");
        }
    }
}
