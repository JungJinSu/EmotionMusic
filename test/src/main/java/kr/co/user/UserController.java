package kr.co.user;

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
public class UserController {
	public static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	// ��� Ȯ�� http://localhost:9090/test/list.do
	//@RequestMapping(value="index.do", method = RequestMethod.GET)
	@RequestMapping("/user/Private.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Private() {
		logger.debug("�˻� �׽�Ʈ");
		System.out.println("���� ����");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/Private");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		return mav;
	} // Private() end
	
	@RequestMapping("/user/Customer.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Customer() {
		logger.debug("�˻� �׽�Ʈ");
		System.out.println("�� ����");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/Customer");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		return mav;
	} // index() end
}
