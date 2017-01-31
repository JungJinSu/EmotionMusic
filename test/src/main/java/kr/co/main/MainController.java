package kr.co.main;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Random;

import javax.management.AttributeList;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sun.javafx.collections.SetAdapterChange;

import kr.co.dao.DictionaryDAO;
import kr.co.dao.MediaDAO;
import kr.co.dao.SentShareDAO;
import kr.co.dto.DictionaryDTO;
import kr.co.dto.MediaDTO;
import kr.co.dto.SearchDTO;
import kr.co.dto.SentShareDTO;
import kr.co.utility.Utility;
 
@Controller
public class MainController {
	public static Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private DictionaryDAO dicDAO;
	@Autowired
	private MediaDAO mediaDAO;
	@Autowired
	private SentShareDAO dao;
	
	// ��� Ȯ�� http://localhost:9090/test/list.do
	//@RequestMapping(value="index.do", method = RequestMethod.GET)
	@RequestMapping(value="/main/search.do", produces = "application/json; charset=utf8")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Search() {
		ModelAndView mav = new ModelAndView();
		List<MediaDTO> musicList= mediaDAO.list();												// media ���̺� ��ü �뷡 ����, �����ܾ� �˻����� ���
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 				// bubbleChar data : jsonEmotion 
		List<DictionaryDTO> emotionDICList = dicDAO.selectList("selectList");					// emotionDIC ���̺� ���� �ܾ� ����
		JSONObject jsonBubbleMenu = Utility.getJsonBubbleMenu(emotionDICList);			// BubbleMenu data : jsonEmotion 
		
		
		mav.setViewName("main/search");															// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		mav.addObject("jsonEmotion",jsonEmotion);
		mav.addObject("jsonBubbleMenu",jsonBubbleMenu);
		
		return mav;
	} // Search() end
	
	@RequestMapping(value="/main/searchplay.do", method=RequestMethod.GET)
	public ModelAndView play(MediaDTO mediaDTO) {
		ModelAndView mav = new ModelAndView();
		mediaDAO.playcnt(mediaDTO.getLyricsNo()); 														// ���Ƚ�� ����
		List<MediaDTO> musicList= mediaDAO.list();														//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		List<DictionaryDTO> emotionDICList = dicDAO.selectList("selectList");						// emotionDIC ���̺� ���� �ܾ� ����
		JSONObject jsonBubbleMenu = Utility.getJsonBubbleMenu(emotionDICList);				// BubbleMenu data : jsonEmotion
		mediaDTO = mediaDAO.read(mediaDTO.getLyricsNo());
		String url[]=mediaDTO.getUrl().split("=");
		
		
		mav.setViewName("main/search");	
		mav.addObject("jsonEmotion",jsonEmotion);
		mav.addObject("jsonBubbleMenu",jsonBubbleMenu);										// for bubbleMenu
		mav.addObject("videoId", url[1]);
		mav.addObject("lyrics", mediaDTO.getLyrics());
		
		
		return mav;
	} // play() end
	
	
	@RequestMapping(value="/main/search.do", produces = "application/json; charset=utf8", method=RequestMethod.POST )								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView Search(String word1, String word2, String word3 ) {
		// �˻� �������� �ʿ��� �⺻ ������  
		ModelAndView mav = new ModelAndView();
		List<MediaDTO> musicList= mediaDAO.list();													// media ���̺� ��ü �뷡 ����, �����ܾ� �˻�, ��õ���� ���
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 					// bubbleChar data : jsonEmotion 
		List<DictionaryDTO> emotionDICList = dicDAO.selectList("selectList");						// emotionDIC ���̺� ���� �ܾ� ����
		JSONObject jsonBubbleMenu = Utility.getJsonBubbleMenu(emotionDICList);				// BubbleMenu data : jsonEmotion 
		ArrayList<DictionaryDTO> searchList = new ArrayList<DictionaryDTO>();					// �˻��� ���� �������� ����,   AttributeList() ��� List<DictionaryDTO> �Ҵ���? -> ArrayList ���
		String inputWord[] = {word1,word2,word3};
		
		/*���� �˻��� ���� �ܾ��� ����Ÿ���� ã�Ƽ� ����(searchList)*/
		for(DictionaryDTO dicDTO :emotionDICList){
			String dicWord;
			if(dicDTO.getWord().length()<4){				// 3���� �̻� '~�ϴ�' �����ߴ�  ���ڿ� �� ex> ���ϴ�		
				dicWord = dicDTO.getWord();
			}else{
				dicWord = dicDTO.getWord().replace("�ϴ�","");
			}
			for(String word: inputWord){
				if(dicWord.equals(word)){									// ���� : ���� �ܾ ���� ������ �ߺ� ����.
					logger.debug("���� : " + dicDTO.getEmotion());
					logger.debug("�ܾ� : " + dicDTO.getWord());
					searchList.add(dicDTO);
				}
			}
		}
		
		//!!!! �̺κ� �˰��� ���� �ʿ� -> �������� �������� �������� ������ ��� ������ �뷡�� ����ؼ� ����õ���� ����!!!
		// ������ ���ĵ� �����Ϳ���  �뷡��õ�� ���� ����  
		ArrayList<MediaDTO> recommendList = new ArrayList<MediaDTO>();						// ��õ �� ����Ʈ
		ArrayList<MediaDTO> mediaDTOeachEmotionType[] =Utility.categorizeEmotionType(mediaDAO);	 // �������� ���ĵ��ִ� ����Ʈ �ٸ� Ŭ�������� DB ������ ���� dao �ѱ�
		String emotionTypeArray[] ={"���","����","����","���","����","����","�г�"};				// ���ĵǾ��ִ� ����Ÿ�� ���� 
		//logger.debug(String.valueOf(searchList.size()) );
		
		// �˻� �� ����� �ߺ��� ����Ÿ���� ��� ����		
		for(int index=0; index<searchList.size(); index++){
			for(int index2=0; index2<index; index2++){
				if(searchList.get(index).getEmotion().equals(searchList.get(index2).getEmotion())){
					logger.debug("�ߺ��� ����Ÿ�� ���� : " +searchList.get(index).getEmotion() );
					searchList.remove(index);		// �ߺ� ���� ����
					index--;							// ���� ��ġ �� ���� �ٽ� ��
					
				}
			}
		}
		
		int randomNumbers[] = Utility.randomNumber(searchList.size(), 20);						// ���� Ÿ�Ժ��� ���� 20��� ������ ��õ searchList.size() = ������ �ܾ��� ���� �� 
		//logger.debug(String.valueOf(searchList.size()) );
		for(int num : randomNumbers){
			logger.debug("���� ���� : "+ String.valueOf(num));
		}
		
		/* ã�� ����Ÿ��(searchList)���� ������ ���� �� ��õ */
		for(DictionaryDTO dicDTO : searchList ){
			logger.debug("�ش� ���� : "+dicDTO.getEmotion());
			for(int emotionIndex=0; emotionIndex<emotionTypeArray.length; emotionIndex++){	//emotionTypeArray.length		// �ּ� 3ȸ~ �ִ� 7ȸ �̵�
				if(dicDTO.getEmotion().equals(emotionTypeArray[emotionIndex]))								// ����ڰ� ������ ������ ���� ������ ����
					for(int random:randomNumbers){																// ��õ ��� ��ŭ �ݺ�
						MediaDTO dto = mediaDTOeachEmotionType[emotionIndex].get(random);			// �������� ���� ��õ
						dto.setEmotion(dicDTO.getEmotion());
						recommendList.add(dto);
					}
			}
		}
		mav.setViewName("main/search");															// .jsp �� suffix �� ���������Ƿ� ���ܽ��ѵ� �ȴ�.
		mav.addObject("jsonEmotion",jsonEmotion);												// for bubbleChart 	
		mav.addObject("jsonBubbleMenu",jsonBubbleMenu);										// for bubbleMenu
		mav.addObject("recommendList", recommendList);											// for recommendList 
		return mav;
	} // Search() end
	
	// ���� ���� ����Ʈ ��Ʈ�ѷ�
	@RequestMapping("/main/share.do")								// .do�� �ȉ�� ���� : ��Ű���� test�� ������ ��� �Է�
	public ModelAndView list(SearchDTO searchDTO, HttpServletRequest req){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/share"); // /main/share.jsp
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		//ArrayList<SentShareDTO> list = dao.list(searchDTO);
		int cnt = dao.getArticleCount(searchDTO);
		
		//����¡
		int numPerPage=10;
		int pagePerBlock=10;
				
		String pageNum=req.getParameter("pageNum");
		if(pageNum==null){
			pageNum="1";
		}
				
		int currentPage=Integer.parseInt(pageNum);
		int startRow=(currentPage-1)*numPerPage+1;
		int endRow=currentPage*numPerPage;
				
		//��������
		double totcnt = (double)cnt/numPerPage;
		int totalPage = (int)Math.ceil(totcnt);
				
		double d_page = (double)currentPage/pagePerBlock;
		int Pages = (int)Math.ceil(d_page)-1;
		int startPage = Pages*pagePerBlock;
		int endPage = startPage+pagePerBlock+1;
		
		String word = req.getParameter("word");
		String searchCondition = req.getParameter("searchCondition");
		
		hashMap.put("searchCondition", searchCondition);
		hashMap.put("word", word);
		hashMap.put("startRow", startRow);
		hashMap.put("endRow", endRow);
				
		List<SentShareDTO> articleList=null;
		if(cnt>0){
			articleList=dao.list(hashMap);
		}else{
			articleList=Collections.EMPTY_LIST;
		}
				
		int number=0;
		number=cnt-(currentPage-1)*numPerPage;
				
		//mav.addObject("list", list);
		mav.addObject("count", cnt); // �� ��ü ����
		
		mav.addObject("number", new Integer(number));
		mav.addObject("pageNum", new Integer(currentPage));
		mav.addObject("startRow", new Integer(startRow));
		mav.addObject("endRow", new Integer(endRow));
		mav.addObject("pageSize", new Integer(pagePerBlock));
		mav.addObject("totalPage", new Integer(totalPage));
		mav.addObject("startPage", new Integer(startPage));
		mav.addObject("endPage", new Integer(endPage));
		mav.addObject("articleList", articleList);
		mav.addObject("word", word);
		mav.addObject("searchCondition", searchCondition);
		
		System.out.println(Pages);
		return mav;
	} // Share() end
	
	// ���� ���� �ۼ� �� ��Ʈ�ѷ�
	@RequestMapping(value="/main/create.do", method=RequestMethod.GET)
	public ModelAndView createForm(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/createForm");
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		return mav;
	}
	
	// ���� ���� ó�� ��Ʈ�ѷ�
	@RequestMapping(value="/main/create.do", method=RequestMethod.POST)
	public ModelAndView createProc(SentShareDTO dto){
		ModelAndView mav = new ModelAndView();
		int cnt = dao.create(dto);
		mav.setViewName("redirect:/main/share.do"); // /mediagroup/msgView.jsp		
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		return mav;
	}
	
	// ���� ���� ��ȸ ��Ʈ�ѷ�
	@RequestMapping(value="/main/read.do", method=RequestMethod.GET)
	public ModelAndView read(int bbsno){
		dao.readcnt(bbsno);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/read");
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		
		SentShareDTO dto = dao.read(bbsno);
		mav.addObject("dto", dto);
		return mav;
	}//read() end
	
	// ���� ���� ���� �� ��Ʈ�ѷ� 
	@RequestMapping(value="/main/delete.do", method=RequestMethod.GET)
	public ModelAndView deleteForm(int bbsno){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/deleteForm");
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		SentShareDTO dto = dao.read(bbsno);
		mav.addObject("dto", dto);
		return mav;
	}
	
	// ���� ���� ���� ó�� ��Ʈ�ѷ�
	@RequestMapping(value="/main/delete.do", method=RequestMethod.POST)
	public ModelAndView deleteProc(SentShareDTO dto){
		ModelAndView mav = new ModelAndView();
		int cnt = dao.delete(dto);
		mav.setViewName("redirect:/main/share.do");
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		return mav;
	}
	
	// ���� ���� ���� �� ��Ʈ�ѷ�
	@RequestMapping(value="/main/update.do", method=RequestMethod.GET)
	public ModelAndView updateForm(int bbsno){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/updateForm");
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		SentShareDTO dto = dao.read(bbsno);
		mav.addObject("dto", dto);
		return mav;
	}
	
	// ���� ���� ���� ó�� ��Ʈ�ѷ�
	@RequestMapping(value="/main/update.do", method=RequestMethod.POST)
	public ModelAndView updateProc(SentShareDTO dto){
		ModelAndView mav = new ModelAndView();
		int cnt = dao.update(dto);
		mav.setViewName("redirect:/main/share.do");
		List<MediaDTO> musicList= mediaDAO.list();									//bubbleChart�� �����ֱ�����  ��ü �뷡 ���� ��ȸ 
		JSONObject jsonEmotion = Utility.getJsonAllEmotionMusic(musicList);	 
		mav.addObject("jsonEmotion",jsonEmotion);
		
		return mav;
	}
}
