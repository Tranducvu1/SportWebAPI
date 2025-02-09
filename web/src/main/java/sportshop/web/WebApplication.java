	package sportshop.web;
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
	@SpringBootApplication
	@EnableConfigurationProperties
	@EnableCaching
	public class WebApplication {
		public static void main(String[] args) {
			SpringApplication.run(WebApplication.class, args);	
		}
	}
