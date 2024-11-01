package com.my_chat_gpt.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.my_chat_gpt.demo.Service.GeminiService;

import jakarta.ws.rs.core.Application;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		

		GeminiService service = context.getBean(GeminiService.class);
		
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(service.llamadaApi("que año estomos", "{Your apikey }"));
	}

}
