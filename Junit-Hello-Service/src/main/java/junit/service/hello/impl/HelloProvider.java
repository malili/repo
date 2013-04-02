package junit.service.hello.impl;

import junit.service.hello.HelloService;

public class HelloProvider implements HelloService {

    public String getHelloMessage() {
        return "hello";
    }

}
