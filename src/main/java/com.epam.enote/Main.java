package com.epam.enote;

import com.epam.enote.config.DatabaseConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DatabaseConfig.class);
        context.refresh();
    }

}
