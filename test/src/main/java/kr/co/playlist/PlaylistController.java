package kr.co.playlist;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.simple.JSONArray;
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
public class PlaylistController {
	public static Logger logger = LoggerFactory.getLogger(PlaylistController.class);
	
	@Autowired
	MediaDAO mediaDAO;
	
	// ��� Ȯ�� http://localhost:9090/test/list.do
	//@RequestMapping(value="index.do", method = RequestMethod.GET)
	@RequestMapping("/playlist/toplist.do")												// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Toplist() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlist/toplist");												// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ
		List<MediaDTO> musicList= mediaDAO.list();										 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		 
		// dash board �κ� 
		List<MediaDTO> topList= mediaDAO.toplist();										//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		
		//logger.debug(String.valueOf(topList.size()));
		
		
		JSONArray jsonTopTen = Utility.getJsonTopMusic(topList);	 
		mav.addObject("jsonTopTen",jsonTopTen);
		mav.addObject("topList", topList);
		
		
		return mav;
	} // Toplist() end
	@RequestMapping(value="/playlist/toplistplay.do", method=RequestMethod.GET)
	public ModelAndView play(MediaDTO mediaDTO) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlist/toplist");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		List<MediaDTO> topList=mediaDAO.toplist(); 
		mediaDTO = mediaDAO.read(mediaDTO.getLyricsNo());
		String url[]=mediaDTO.getUrl().split("=");
		mav.addObject("videoId", url[1]);
		mav.addObject("lyrics", mediaDTO.getLyrics());
		mav.addObject("topList", topList);
		
		return mav;
	} // play() end
	
	@RequestMapping("/playlist/randomplay.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Randomplay() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlist/randomplay");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		
		return mav;
	} // Randomplay() end
	
	@RequestMapping("/playlist/emotion.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Emotion() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlist/emotion");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		return mav;
	} // Emotion() end
}
