package kr.co.user;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.dao.MediaDAO;
import kr.co.dto.MediaDTO;
import kr.co.utility.Utility;
@Controller
public class UserController {
	public static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MediaDAO mediaDAO;
	
	// ��� Ȯ�� http://localhost:9090/test/list.do
	//@RequestMapping(value="index.do", method = RequestMethod.GET)
	@RequestMapping("/user/private.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Private() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/private");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		
		return mav;
	} // Private() end
	
	@RequestMapping("/user/customer.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Customer() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/customer");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		return mav;
	} // index() end
}
