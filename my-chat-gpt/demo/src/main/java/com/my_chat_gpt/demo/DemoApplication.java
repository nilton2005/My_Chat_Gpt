package com.my_chat_gpt.demo;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.Array;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.my_chat_gpt.demo.Service.GeminiService;
import org.springframework.http.MediaType;

import jakarta.ws.rs.core.Application;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		

		GeminiService service = context.getBean(GeminiService.class);
		
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(service.llamadaApi("que año estomos", "AIzaSyBX3SMoSxzqWsAQddIapUn_Pno7krv9gD4"));

		
	}

}
