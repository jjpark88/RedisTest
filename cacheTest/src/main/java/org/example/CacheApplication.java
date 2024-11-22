package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CacheApplication implements ApplicationRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CacheApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		userRepository.save(User.builder().name("jjpark").email("jjpark.email.com").build());
		userRepository.save(User.builder().name("jjpark2").email("jjpark2.email.com").build());
		userRepository.save(User.builder().name("jjpark3").email("jjpark3.email.com").build());
		userRepository.save(User.builder().name("jjpark4").email("jjpark4.email.com").build());

	}
}
