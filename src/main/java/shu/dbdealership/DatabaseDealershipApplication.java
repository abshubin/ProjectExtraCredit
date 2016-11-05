package shu.dbdealership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DatabaseDealershipApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseDealershipApplication.class, args);
	}
}
