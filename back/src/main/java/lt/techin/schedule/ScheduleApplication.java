package lt.techin.schedule;

import lt.techin.schedule.schedules.toexcel.ScheduleToExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ScheduleApplication extends SpringBootServletInitializer {
	Logger logger = LoggerFactory.getLogger(ScheduleApplication.class);
	private final ScheduleToExcelService excel;

	public ScheduleApplication(ScheduleToExcelService excel) {
		this.excel = excel;
	}

	public static void main(String[] args) {
		SpringApplication.run(ScheduleApplication.class, args);
	}

	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) throws Exception {
//		ApplicationContext applicationContext = event.getApplicationContext();
//		RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
//				.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
//		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
//				.getHandlerMethods();
//		map.forEach((key, value) -> logger.info("{} {}", key, value));
		excel.drawCalendar();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ScheduleApplication.class);
	}
}