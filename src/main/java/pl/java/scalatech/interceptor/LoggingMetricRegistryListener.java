package pl.java.scalatech.interceptor;

import lombok.extern.slf4j.Slf4j;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistryListener;
import com.codahale.metrics.Timer;

@Slf4j
public class LoggingMetricRegistryListener implements MetricRegistryListener {

    @Override
    public void onGaugeAdded(String s, Gauge<?> gauge) {
        log.debug("Gauge added: {}", s);
    }

    @Override
    public void onGaugeRemoved(String s) {
        log.debug("Gauge removed: {}", s);
    }

    @Override
    public void onCounterAdded(String s, Counter counter) {
        log.debug("Counter added: {}", s);
    }

    @Override
    public void onCounterRemoved(String s) {
        log.debug("Counter removed: {}", s);
    }

    @Override
    public void onHistogramAdded(String s, Histogram histogram) {
        log.debug("Histogram added: {}", s);
    }

    @Override
    public void onHistogramRemoved(String s) {
        log.debug("Histogram removed: {}", s);
    }

    @Override
    public void onMeterAdded(String s, Meter meter) {
        log.debug("Meter added: {}", s);
    }

    @Override
    public void onMeterRemoved(String s) {
        log.debug("Meter removed: {}", s);
    }

    @Override
    public void onTimerAdded(String s, Timer timer) {
        log.debug("Timer added: {}", s);
    }

    @Override
    public void onTimerRemoved(String s) {
        log.debug("Timer removed: {}", s);
    }
}