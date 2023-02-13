package lt.techin.schedule;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ScheduleApplication extends SpringBootServletInitializer {
//	Logger logger = LoggerFactory.getLogger(ScheduleApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ScheduleApplication.class, args);
	}

//	@EventListener
//	public void handleContextRefresh(ContextRefreshedEvent event) {
//		ApplicationContext applicationContext = event.getApplicationContext();
//		RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
//				.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
//		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
//				.getHandlerMethods();
//		map.forEach((key, value) -> logger.info("{} {}", key, value));
//	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ScheduleApplication.class);
	}

}
