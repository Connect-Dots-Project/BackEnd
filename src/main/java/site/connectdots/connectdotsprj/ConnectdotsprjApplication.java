package site.connectdots.connectdotsprj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class ConnectdotsprjApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectdotsprjApplication.class, args);
	}


	@Controller
	public class MapController {

		@GetMapping("/map")
		public String showMapPage() {
			return "index"; // JSP 파일의 이름을 반환합니다 (확장자 .jsp는 생략합니다).
		}
	}
}
