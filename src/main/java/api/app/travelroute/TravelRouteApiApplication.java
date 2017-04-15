package api.app.travelroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages="net.kaczmarzyk")
@ComponentScan(basePackages="api.base")
public class TravelRouteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelRouteApiApplication.class, args);
	}
}
