package com.epam.martseniuk;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author Roman_Martseniuk
 */
public class MetricFilter implements Filter {

    @Autowired
    private MetricSystem metricSystem;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        metricSystem.geolocationWriteRequestCount().inc();
    }

    @Override
    public void destroy() {

    }
}
