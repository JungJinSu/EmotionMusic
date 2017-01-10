package kr.co.playlist;

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
public class PlaylistController {
	public static Logger logger = LoggerFactory.getLogger(PlaylistController.class);
	
	// ��� Ȯ�� http://localhost:9090/test/list.do
	//@RequestMapping(value="index.do", method = RequestMethod.GET)
	@RequestMapping("/playlist/toplist.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Toplist() {
		logger.debug("�˻� �׽�Ʈ");
		System.out.println("ž100");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlist/toplist");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		return mav;
	} // Toplist() end
	
	@RequestMapping("/playlist/randomplay.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Randomplay() {
		logger.debug("�˻� �׽�Ʈ");
		System.out.println("�������");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlist/randomplay");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		return mav;
	} // Randomplay() end
	
	@RequestMapping("/playlist/emotion.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Emotion() {
		logger.debug("��������");
		System.out.println("����׽�Ʈ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlist/emotion");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		return mav;
	} // Emotion() end
}
