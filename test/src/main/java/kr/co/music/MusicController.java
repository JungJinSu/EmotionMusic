package kr.co.music;

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
public class MusicController {
	public static Logger logger = LoggerFactory.getLogger(MusicController.class);
	@Autowired
	private MediaDAO mediaDAO;
	
	
	// ��� Ȯ�� http://localhost:9090/test/list.do
	//@RequestMapping(value="index.do", method = RequestMethod.GET)
	@RequestMapping("/music/replay.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Replay() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("music/replay");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		
		return mav;
	} // Replay() end
	
	@RequestMapping("/music/emotionlist.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Emotionlist() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("music/emotionlist");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		return mav;
	} // Emotionlist() end
}
