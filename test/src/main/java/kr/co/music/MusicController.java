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
 
import kr.co.dao.HistoryDAO;
import kr.co.dao.MediaDAO;
import kr.co.dto.HistoryDTO;
import kr.co.dto.MediaDTO;
import kr.co.utility.Utility;
@Controller
public class MusicController {
	public static Logger logger = LoggerFactory.getLogger(MusicController.class);
	@Autowired
	HistoryDAO historyDAO;

	@RequestMapping("/music/replay.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Replay(HistoryDTO historyDTO) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("music/replay");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		List<HistoryDTO> musicList= historyDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		//JSONObject jsonEmotion = Utility.getHistoryMusic(musicList);	 
		mav.addObject("list",musicList);

		return mav;
	} // Replay() end
	
	@RequestMapping("/music/emotionlist.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Emotionlist() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("music/emotionlist");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		List<HistoryDTO> musicList= historyDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		//JSONObject jsonEmotion = Utility.getHistoryMusic(musicList);	 
		mav.addObject("list",musicList);

		return mav;
	} // Emotionlist() end
}


