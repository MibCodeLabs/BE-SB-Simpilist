package com.mib.simpilist;

import org.springframework.boot.SpringApplication;

public class TestSimpilistApplication {

	public static void main(String[] args) {
		SpringApplication.from(SimpilistApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
