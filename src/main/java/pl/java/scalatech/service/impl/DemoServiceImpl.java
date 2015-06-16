package pl.java.scalatech.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import pl.java.scalatech.service.DemoService;

import com.google.common.base.Joiner;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String doSomething(String message, Collection<String> str) {
        return message + ", " + Joiner.on(", ") + "!";
    }
}