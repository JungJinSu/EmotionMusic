package kr.co.test;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class HomeController {
	public static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//	@RequestMapping(value = "/", method = RequestMethod.GET)				
//	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
//		
//		return "home";
//	}
	
	// 결과 확인 http://localhost:9090/test/index.do
	//@RequestMapping(value="index.do", method = RequestMethod.GET)
	@RequestMapping("/index.do")								// .do가 안됬던 이유 : 패키지명 test를 제외한 경로 입력
	public ModelAndView index() {
		logger.debug("인터셉터 테스트");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");								// .jsp 는 suffix 에 지정했으므로 제외시켜도 된다.
		return mav;
	} // index() end
}
