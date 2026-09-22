package coding_challenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import service.Processor;

@SpringBootApplication(scanBasePackages = {"coding_challenge", "service"})
public class CodingChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingChallengeApplication.class, args);
	}

	@Bean
	CommandLineRunner transactionProcessorRunner(Processor processor) {
		return args -> processor.process();
	}
}
