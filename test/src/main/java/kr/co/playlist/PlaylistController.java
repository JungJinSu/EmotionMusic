package kr.co.playlist;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
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
		JSONArray jsonTopTen = Utility.getJsonTopMusic(topList);	 
		mav.addObject("jsonTopTen",jsonTopTen);											// DashBoard JsonObj
		mav.addObject("topList", topList);														// top 10 ����Ʈ 
		
		
		return mav;
	} // Toplist() end
	@RequestMapping(value="/playlist/toplistplay.do", method=RequestMethod.GET)
	public ModelAndView play(MediaDTO mediaDTO) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlist/toplist");											// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		
		mediaDAO.playcnt(mediaDTO.getLyricsNo()); 									// �÷��� Ƚ�� ���� 
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		List<MediaDTO> topList=mediaDAO.toplist(); 
		mediaDTO = mediaDAO.read(mediaDTO.getLyricsNo());
		String url[]=mediaDTO.getUrl().split("=");
		mav.addObject("videoId", url[1]);
		mav.addObject("lyrics", mediaDTO.getLyrics());
		JSONArray jsonTopTen = Utility.getJsonTopMusic(topList);	 
		mav.addObject("jsonTopTen",jsonTopTen);											// DashBoard JsonObj
		mav.addObject("topList", topList);
		
		return mav;
	} // play() end
	
	@RequestMapping("/playlist/randomplaylist.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Randomplaylist() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlist/randomplaylist");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		List<MediaDTO> randomList= mediaDAO.randomList();										//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		mav.addObject("randomList", randomList);														// Random ����Ʈ 
		
		
		return mav;
	} // Randomplaylist() end
	
	@RequestMapping(value="/playlist/randomplay.do", method=RequestMethod.GET)								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Randomplay(MediaDTO mediaDTO) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlist/randomplaylist");								// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		System.out.println("asdf");
		System.out.println(mediaDTO.getLyricsNo());

		mediaDAO.playcnt(mediaDTO.getLyricsNo()); 									// �÷��� Ƚ�� ���� 
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String userId = "Ciel Lu";
		String title = mediaDTO.getTitle();
		String emotion = mediaDTO.getEmotion();
		hashMap.put("userId", userId);
		hashMap.put("title", title);
		hashMap.put("emotion", emotion);
		mediaDAO.dateinsert(hashMap);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(title);
		System.out.println(emotion);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		List<MediaDTO> randomList= mediaDAO.randomList();										//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		mav.addObject("randomList", randomList);														// Random ����Ʈ 
		
		mediaDTO = mediaDAO.read(mediaDTO.getLyricsNo());
		String url[]=mediaDTO.getUrl().split("=");
		mav.addObject("videoId", url[1]);
		mav.addObject("lyrics", mediaDTO.getLyrics());
		
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
