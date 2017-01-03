package kr.co.music;

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
public class MusicController {
	public static Logger logger = LoggerFactory.getLogger(MusicController.class);
	
	// ��� Ȯ�� http://localhost:9090/test/list.do
	//@RequestMapping(value="index.do", method = RequestMethod.GET)
	@RequestMapping("/music/Replay.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Replay() {
		logger.debug("�˻� �׽�Ʈ");
		System.out.println("�ٽõ��");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("music/Replay");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		return mav;
	} // Replay() end
	
	@RequestMapping("/music/Emotionlist.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Emotionlist() {
		logger.debug("�˻� �׽�Ʈ");
		System.out.println("�����׷���");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("music/Emotionlist");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		return mav;
	} // Emotionlist() end
}
