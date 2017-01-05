package kr.co.main;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	public static Logger logger = LoggerFactory.getLogger(MainController.class);
	
	
		@Autowired
		private SentShareDAO dao;
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
	
	// ��� Ȯ�� http://localhost:9090/test/list.do
	//@RequestMapping(value="index.do", method = RequestMethod.GET)
	@RequestMapping("/main/Search.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Search() {
		logger.debug("�˻� �׽�Ʈ");
		System.out.println("����׽�Ʈ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/Search");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		return mav;
	} // Search() end
	
	@RequestMapping("/main/Share.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView list(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/list"); // /main/list.jsp
		ArrayList<SentShareDTO> list = dao.list();
		mav.addObject("list", list);
		return mav;
	} // Share() end
	
	@RequestMapping(value="/main/create.do", method=RequestMethod.GET)
	public ModelAndView createForm(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/createForm");
		return mav;
	}
	
	@RequestMapping(value="/main/create.do", method=RequestMethod.POST)
	public ModelAndView createProc(SentShareDTO dto){
		ModelAndView mav = new ModelAndView();
		int cnt = dao.create(dto);
		mav.setViewName("redirect:/main/list.do"); // /mediagroup/msgView.jsp		
		return mav;
	}
	
	@RequestMapping(value="/main/read.do", method=RequestMethod.GET)
	public ModelAndView read(int bbsno){
		dao.readcnt(bbsno);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/read");
		SentShareDTO dto = dao.read(bbsno);
		mav.addObject("dto", dto);
		return mav;
	}//read() end
	
	@RequestMapping(value="/main/delete.do", method=RequestMethod.GET)
	public ModelAndView deleteForm(int bbsno){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/deleteForm");
		SentShareDTO dto = dao.read(bbsno);
		mav.addObject("dto", dto);
		return mav;
	}
	
	@RequestMapping(value="/main/delete.do", method=RequestMethod.POST)
	public ModelAndView deleteProc(SentShareDTO dto){
		ModelAndView mav = new ModelAndView();
		int cnt = dao.delete(dto);
		mav.setViewName("redirect:/main/list.do");
		return mav;
	}
	
	@RequestMapping(value="/main/update.do", method=RequestMethod.GET)
	public ModelAndView updateForm(int bbsno){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/updateForm");
		SentShareDTO dto = dao.read(bbsno);
		mav.addObject("dto", dto);
		return mav;
	}
	
	@RequestMapping(value="/main/update.do", method=RequestMethod.POST)
	public ModelAndView updateProc(SentShareDTO dto){
		ModelAndView mav = new ModelAndView();
		int cnt = dao.update(dto);
		mav.setViewName("redirect:/main/list.do");
		return mav;
	}
}
