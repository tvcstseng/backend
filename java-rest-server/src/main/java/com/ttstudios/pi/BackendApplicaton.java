package com.ttstudios.pi;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.ttstudios.pi.temperature.repository.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "com.ttstudios.pi" })
@EnableAutoConfiguration
public class BackendApplicaton
{
    @Autowired
    private BookRepository booksRepo;
    
    @Autowired
    private TemperatureRepository temperatureRepo;


    public static void main( String[] args ){
        SpringApplication.run(BackendApplicaton.class, args);

    }

    @PostConstruct
    public void initApplication() throws IOException {
        booksRepo.addBook(new Book("111-1","Java 8 Lamdas","Richard Warburton"));
        booksRepo.addBook(new Book("111-2","An Introduction to Programming in Go","Caleb Doxsey"));

    }
}
