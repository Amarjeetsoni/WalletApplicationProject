package com.cg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  
/* Consist of three annotation 
 * @Configuration, 
 * @EnableAutoConfiguration, 
 * @ComponentScan
 * */
public class WalletApplicationProjectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletApplicationProjectBackendApplication.class, args);
	}

}
