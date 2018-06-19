package com;

import com.rest.Application;
import org.springframework.boot.SpringApplication;

/**
 * Created by sergiubulzan on 17/06/2017.
 */
public class StartServer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        IServer server = new RabMQServer();
        server.start();
    }
}
