package com.epam.martseniuk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roman_Martseniuk
 */
@RestController
public class MetricController {

    @RequestMapping(value = "/geo", method = RequestMethod.GET)
    public void create() throws InterruptedException {
        System.err.println("!!!!!!!!!!!!!!!!!!!");
    }

    @RequestMapping(value = "/geo1", method = RequestMethod.GET)
    public void create1() throws InterruptedException {
        System.err.println("geo1");
    }
}
