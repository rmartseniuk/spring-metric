package com.epam.martseniuk;

import com.netflix.servo.MonitorRegistry;
import com.netflix.servo.monitor.Pollers;
import com.netflix.servo.publish.AsyncMetricObserver;
import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.CounterToRateMetricTransform;
import com.netflix.servo.publish.JvmMetricPoller;
import com.netflix.servo.publish.MetricObserver;
import com.netflix.servo.publish.MetricPoller;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.PollScheduler;
import com.netflix.servo.publish.graphite.GraphiteMetricObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Roman_Martseniuk
 */
@Component
public class ServoInitializer {

    @Autowired
    private MonitorRegistry servoMonitorRegistry;

    @Value("${servo.graphite.observerPrefix}")
    private String observerPrefix;

    @Value("${servo.graphite.address}")
    private String address;

    private static long getPollInterval() {
        return Pollers.getPollingIntervals().get(0) / 1000L;
    }

    private static void schedule(MetricPoller poller, List<MetricObserver> observers) {
        final PollRunnable task = new PollRunnable(poller, BasicMetricFilter.MATCH_ALL, true, observers);
        PollScheduler.getInstance().addPoller(task, getPollInterval(), TimeUnit.SECONDS);
    }

    @PostConstruct
    public void initServo() {
        if (!PollScheduler.getInstance().isStarted()) {
            final List<MetricObserver> observers = new ArrayList<>();

            final long heartbeat = 2 * getPollInterval();
            final long expireTime = 2000 * getPollInterval();
            final int queueSize = 10;

            MetricObserver o = new CounterToRateMetricTransform(new AsyncMetricObserver("graphite",
                    new GraphiteMetricObserver(observerPrefix, address), queueSize, expireTime), heartbeat,
                    TimeUnit.SECONDS);
            observers.add(o);

            PollScheduler.getInstance().start();

            schedule(new MonitorRegistryMetricPoller(servoMonitorRegistry), observers);
            schedule(new JvmMetricPoller(), observers);
        }
    }
}
