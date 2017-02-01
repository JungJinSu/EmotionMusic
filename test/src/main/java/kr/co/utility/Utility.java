package kr.co.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.dao.MediaDAO;
import kr.co.dto.DictionaryDTO;
import kr.co.dto.HistoryDTO;
import kr.co.dto.MediaDTO;
import kr.co.test.HomeController;

public class Utility {
	private static final String root = "/test";
	private static final Logger logger = LoggerFactory.getLogger(Utility.class);

	public static synchronized String getRoot() {
		return root;
	}
	
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return sdf.format(new Date());
	} // getCurrentDate() end
	
	
	// ������ �˻���� title ���ϱ� : 3���� ���
	public void compareTitle(String subject, String[] youtubeTitle){
		String searchSubject = subject.replace(" ", "+");				// �˻���� ������ +�� 
		String compare="";		// ������� ���� �˻���
		String compareSource[];		// ������ �� �ܾ �־��
		
		// 1. ����տ� ���ڰ� �ٴ� ���
		int index = subject.indexOf(".");	
		if(index==1) {	
			searchSubject = "0"+ searchSubject.replace(".", "+");
			compare = searchSubject.substring(index+1, searchSubject.length()).replace("+", " ").toLowerCase().trim();
		} else if(index==2) {
			compare = searchSubject.substring(index+1, searchSubject.length()).replace("+", " ").toLowerCase().trim();
		} else {
			compare = subject;
		}
		compareSource = compare.split(" ");		
		// 2. ���� ��ü�� Ʋ�� ��� �ܾ�� ��Ȯ�� �����ϱ�
		// = titleAccuracy(urlTitle);
		// 3. �ٹ����� �뷡 ����� ���� ���
	}// compareTitle() end
	
	/* Headr ���� �޼ҵ�*/
	// ��ü �뷡 ����Ʈ�� ���� ������ ���� �ľ�
	public static JSONObject getJsonAllEmotionMusic(List<MediaDTO> musicList  ){				 
		Iterator<MediaDTO> iterator = musicList.iterator();
		String emotionType[] ={"happy","disgust","fear","interest","pain","rage","sad"};
		int count[] = new int[7];
		while(iterator.hasNext()){
			MediaDTO dto = iterator.next();
			if(dto.getHappy()>15)		{count[0]++;}		// 0 ���
			if(dto.getDisgust()>15)		{count[1]++;}		// 1 ����
			if(dto.getFear()>15)			{count[2]++;}		// 2 ����
			if(dto.getInterest()>15)		{count[3]++;}		// 3 ���
			if(dto.getPain()>15)			{count[4]++;}		// 4 ����
			if(dto.getRage()>15)		{count[5]++;}		// 5 �г�
			if(dto.getSad()>15)			{count[6]++;}		// 6 ����
		}
		// ���� �ϼ��� JSONObject ����(��ü)
		JSONObject jsonObject = new JSONObject();
		// person�� JSON������ ���� Array ����
		JSONArray jsonArray = new JSONArray();
		// person�� �Ѹ� ������ �� JSONObject ����
		JSONObject jsonEmotion = new JSONObject();
		for(int i=0; i<emotionType.length; i++){
			jsonEmotion.put(emotionType[i], count[i]);
			
		}
		//logger.debug(jsonEmotion.toString());
		return jsonEmotion;
	}
	
	public static ArrayList<ArrayList<MediaDTO>> getEmotionMusicList(List<MediaDTO> musicList  ){				 
		
		String emotionTypeArray[] ={"happy","disgust","fear","interest","pain","rage","sad"};
		ArrayList<ArrayList<MediaDTO>> emotionMusicArrayList = new ArrayList<ArrayList<MediaDTO>>();
		for(String emotionType:emotionTypeArray){
			ArrayList<MediaDTO> dtoArrayList  = new ArrayList<MediaDTO>();
			Iterator<MediaDTO> iterator = musicList.iterator();
			while(iterator.hasNext()){
				MediaDTO mediaDTO = iterator.next();
				if(mediaDTO.getHappy()>15		&& emotionType.equals("happy"))	{mediaDTO.setEmotion(emotionType); dtoArrayList.add(mediaDTO);}		// 0 ���
				if(mediaDTO.getDisgust()>15		&& emotionType.equals("disgust"))	{mediaDTO.setEmotion(emotionType); dtoArrayList.add(mediaDTO);}		// 1 ����
				if(mediaDTO.getFear()>15			&& emotionType.equals("fear"))		{mediaDTO.setEmotion(emotionType); dtoArrayList.add(mediaDTO);}		// 2 ����
				if(mediaDTO.getInterest()>15	&& emotionType.equals("interest")){mediaDTO.setEmotion(emotionType); dtoArrayList.add(mediaDTO);}		// 3 ���
				if(mediaDTO.getPain()>15			&& emotionType.equals("pain"))		{mediaDTO.setEmotion(emotionType); dtoArrayList.add(mediaDTO);}		// 4 ����
				if(mediaDTO.getRage()>15		&& emotionType.equals("rage"))	{mediaDTO.setEmotion(emotionType); dtoArrayList.add(mediaDTO);}		// 5 �г�
				if(mediaDTO.getSad()>15			&& emotionType.equals("sad"))		{mediaDTO.setEmotion(emotionType); dtoArrayList.add(mediaDTO);}		// 6 ����
			}
			
			logger.debug("����Ÿ�� : " + emotionType);
			logger.debug(String.valueOf(dtoArrayList.size()));
			emotionMusicArrayList.add(dtoArrayList);
		}
		return emotionMusicArrayList ;
	}
	
	

	public static JSONObject getHistoryMusic(List<HistoryDTO> musicList  ){				 
		Iterator<HistoryDTO> iterator = musicList.iterator();
		String emotionType[] ={"happy","disgust","fear","interest","pain","rage","sad"};
		int count[] = new int[7];//7���� ����
		 
		while(iterator.hasNext()){
			HistoryDTO dto = iterator.next();
			if(dto.getEmotion()=="happy") {count[0]++;}
			if(dto.getEmotion()=="disgust") {count[1]++;}
			if(dto.getEmotion()=="fear") {count[2]++;}
			if(dto.getEmotion()=="interest") {count[3]++;}
			if(dto.getEmotion()=="pain") {count[4]++;}
			if(dto.getEmotion()=="rage") {count[5]++;}
			if(dto.getEmotion()=="sad") {count[6]++;}
		}
		
		// ���� �ϼ��� JSONObject ����(��ü)
		JSONObject jsonObject = new JSONObject();
		// person�� JSON������ ���� Array ����
		JSONArray jsonArray = new JSONArray();
		// person�� �Ѹ� ������ �� JSONObject ����
		JSONObject jsonHistory = new JSONObject();
		for(int i=0; i<emotionType.length; i++){
			jsonHistory.put(emotionType[i], count[i]);
			
		}
		//logger.debug(jsonHistory.toString());
		return jsonHistory;
	}
	
	
	/*Bubble Menu ���� �޼ҵ�*/
	// �������� ���� �� Ű���带 ������ ��ȯ �ϴ� �Լ�
	@SuppressWarnings("unchecked")
	public static JSONObject getJsonBubbleMenu(List<DictionaryDTO> dictionaryList  ){				 
		Iterator<DictionaryDTO> iterator = dictionaryList.iterator();
		Random randomEmotion = new Random();
		String emotionType[] ={"���","����","����","���","����","�г�","����"};
		ArrayList<String> listOfEmotionWord[] = new ArrayList[7];			// ������ �ܾ ���� �迭
		for(int index=0; index<listOfEmotionWord.length; index++){
			listOfEmotionWord[index] = new ArrayList<String>();
		}
		//  ������, ��� ���� �ִ�. - ���� ��Ʈ�� �߰� �ؾ��ϴ��� �ǳ�
		while(iterator.hasNext()){								
			DictionaryDTO dto = iterator.next();
			if(dto.getEmotion().equals("���")){
				listOfEmotionWord[0].add(dto.getWord());
			}
			if(dto.getEmotion().equals("����")){
				listOfEmotionWord[1].add(dto.getWord());
			}
			if(dto.getEmotion().equals("����")){
				listOfEmotionWord[2].add(dto.getWord());
			}
			if(dto.getEmotion().equals("���")){
				listOfEmotionWord[3].add(dto.getWord());
			}
			if(dto.getEmotion().equals("����")){
				listOfEmotionWord[4].add(dto.getWord());
			}
			if(dto.getEmotion().equals("�г�")){
				listOfEmotionWord[5].add(dto.getWord());
			}
			if(dto.getEmotion().equals("����")){
				listOfEmotionWord[6].add(dto.getWord());
			}
		}
		
		/*
		 * 		json ���� 
		 *  	#1 #2 #3 jsonObject , 
		 *  	#4 #5 	JsonArray  
		 * 
		 * 	#3{ name:"bubble", children : 
		 * 		#4[
		 *  		 #2{ name:"", description:"", children: 
		 *  		#5[
		 *  				#1{ name:"", address:""}, #1{ name:"", address:""}, #1{ name:"", address:""}
		 *  			]
		 * 		 ]
		 */
																			// �߰��� jsonObject �� �ʿ��� data ����<����Ÿ�� 7��, �����ܾ� 3��> ��ŭ �Ҵ� �Ǿ�� �Ѵ�.
		JSONObject jsonBubbleMenu = new JSONObject();		// #3 ���� ��ȯ��  
		JSONArray jsonMainArray =new JSONArray();			// #4 ���� ����	Array					: add�� jsonMainBubble ��ü �߰� 
		JSONObject jsonMainBubble = null;						// #2 ���� ����	<���� Ÿ��> 		: ����Ÿ�� ��ŭ �ּ� �Ҵ� �ʿ�
		JSONArray jsonSubArray =null;								// #5 ���� ����	Array					: ����Ÿ�� ��ŭ �ּ� �Ҵ� �ʿ�
		JSONObject jsonSubBubble = null;							// #1 ���� ���� 	<���� �ܾ�>���� 	: �����ܾ� ��ŭ �ּ� �Ҵ� �ʿ�
		
	
		for(int emotion=0; emotion<emotionType.length; emotion++){
			  int wordSize 	= listOfEmotionWord[emotion].size();				// ������ ����� �ܾ� ���� 
			  jsonMainBubble = new JSONObject();								// #2, #5 ������ �ּ� �Ҵ� 7��
			  jsonSubArray  = new JSONArray();								
			 
			  for(int i=0; i<3; i++){													// �ܾ� 3�� ���� 
				 jsonSubBubble = new JSONObject();							// #1 �����ܾ �ּ� �Ҵ�
				 int wordIndex	= randomEmotion.nextInt(wordSize);			// �����Լ��� �ܾ� �ε��� ����
				 String word;
				  
				  //logger.debug("����Ÿ��:"+String.valueOf(emotion));
				  if( listOfEmotionWord[emotion].get(wordIndex).length()<4){	// ~�ϴ� ���� ����	��, 3���� ���� ex) ���ϴ�		
					  word = listOfEmotionWord[emotion].get(wordIndex);		
				  }else{
					  word = listOfEmotionWord[emotion].get(wordIndex).replace("�ϴ�","");		
				  }
				  
				 // logger.debug("word:"+word);
				  jsonSubBubble.put("name", word);								// #1 �����ܾ�
				  jsonSubBubble.put("address", "�ּ�");
				  jsonSubArray.add(jsonSubBubble);								// jsonSubBubble �������� �̱� ������ �ߺ� �����. �Ҵ� �ʿ� 
			  }
			  jsonMainBubble.put("name", emotionType[emotion]);			// #2 ����Ÿ�� 
			  jsonMainBubble.put("description", "����");
			  jsonMainBubble.put("children", jsonSubArray);					
			  
			  //logger.debug("#2 jsonMainBubble : "+ jsonMainBubble.toJSONString());			
			  
			  jsonMainArray.add(jsonMainBubble);								// #4 ���� ���� �迭
			 //logger.debug("#4 jsonMainArray :  " + jsonMainArray.toJSONString());
			 
			 jsonBubbleMenu.put("children", jsonMainArray);					// mainBubble childern 										
			 
		}
		jsonBubbleMenu.put("name", "bubble");									// #3 ���� ��ȯ json
		//logger.debug("#3 jsonBubbleMenu : "+jsonBubbleMenu.toJSONString());
	
		
		return jsonBubbleMenu;
	}
	
	
	/*simple dashBoard ���� �޼ҵ�*/
	// ������ ���� json ��ü�� ��ȯ
	@SuppressWarnings("unchecked")
	public static JSONArray getJsonTopMusic(List<MediaDTO> mediaDTOList){
		//���� �ϼ��� JSONObject ����(��ü)
		JSONArray jsonTopMusic = new JSONArray();

		Iterator<MediaDTO> iteratorMedia = mediaDTOList.iterator();
		//String emotionType[] ={"Happy","Disgust","Fear","Interest","Pain","Rage","Sad"};
		ArrayList<Integer> listOfTopTenEmotion[] = new ArrayList[7];			// �Ѱ�� ���� ������ ���� �迭
		
		for(int index=0; index<listOfTopTenEmotion.length; index++){
			listOfTopTenEmotion[index] = new ArrayList<Integer>();
		}
		
		// �Ѱ� �� ������ ������ ArrayList�� ����
		while(iteratorMedia.hasNext()){
			MediaDTO dto = iteratorMedia.next();
			listOfTopTenEmotion[0].add(dto.getHappy());
			listOfTopTenEmotion[1].add(dto.getSad());
			listOfTopTenEmotion[2].add(dto.getRage());
			listOfTopTenEmotion[3].add(dto.getDisgust());
			listOfTopTenEmotion[4].add(dto.getInterest());
			listOfTopTenEmotion[5].add(dto.getPain());
			listOfTopTenEmotion[6].add(dto.getFear());
		}// end while
	
	/*	for(ArrayList<Integer> point : listOfTopTenEmotion){
			logger.debug(String.valueOf(point));
		}*/
	
		// �뷡 �Ѱ��� JSONObject ���·� ����
		for(int i=0; i<10;i++){																// ��ü 10�� 
			JSONObject innerJson = new JSONObject();								// #1 innerJson <���� 7���� ���� >
			JSONObject outterJson = new JSONObject();	
			innerJson.put("happy", listOfTopTenEmotion[0].get(i));
			innerJson.put("sad", listOfTopTenEmotion[1].get(i));
			innerJson.put("rage", listOfTopTenEmotion[2].get(i));
			innerJson.put("disgust", listOfTopTenEmotion[3].get(i));
			innerJson.put("interest", listOfTopTenEmotion[4].get(i));
			innerJson.put("pain", listOfTopTenEmotion[5].get(i));
			innerJson.put("fear", listOfTopTenEmotion[6].get(i));
			outterJson.put("freq", innerJson);											// #2 outterJson index2
			outterJson.put("State", "Top"+(i+1));										// #2 outterJson index1
			jsonTopMusic.add(outterJson);
		}
		
		//logger.debug(jsonTopMusic.get(0).toString());
		return jsonTopMusic;
		
	}
	
	/*  search.do ���� �޼ҵ� : �뷡 ��õ�� ���� ������ ���� ������ ��û*/
	public static ArrayList<MediaDTO>[] categorizeEmotionType(MediaDAO mediaDAO){
		String emotionTypeArray[] ={"happy","sad","disgust","interest","pain","fear","rage"};
		HashMap<String, String> emotionTypeHash = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		ArrayList<MediaDTO> mediaEmotionType[] = new ArrayList[emotionTypeArray.length];		
		// ���� ��� �κ� : String �� ���޽� 'fear' �̷������� ����. 18.. 
		// => log4j-remix �� �̿��ؼ� ������ ���� ĳġ ��������
		// => $ ���ε����� value ���� �ޱ� ���� �ؽ��ʿ� ��Ƽ� ����
		for(int index=0; index<emotionTypeArray.length; index++){		
			emotionTypeHash.put("emotiontype", emotionTypeArray[index]);							// $ ���ε� ������ ���� �ؽ���
			mediaEmotionType[index] =  mediaDAO.listOfEmotionTpye(emotionTypeHash);			// ������ ���� ���� ��û
		}
		return mediaEmotionType;
	}// end
	/*  search.do ���� �޼ҵ� : �ߺ����� ���� �Լ� */
	public static int[] randomNumber(int size, int scope){
		logger.debug("���� ���� ���� : " + size +"/" + "���� : "+ scope);
		
		int randomNumbers[] = new int[size];
		Random random = new Random();
		for(int outter=0; outter<size; outter++){							
			randomNumbers[outter] = random.nextInt(scope);				// ���� ���ڸ� ����
			for(int inner=0; inner<outter; inner++){						// �ߺ� ���ڸ� �����ϱ� ���� �ݺ�
				if(randomNumbers[outter]==randomNumbers[inner]){	// �ߺ��� ��� �ش� ���ڸ� ������ �ڷ� ���ư� ���� ����		
					outter--;
				}
			}
		}
		return randomNumbers;
	}// end
	
	/*  search.do ���� �޼ҵ� :  */
	
	
} // class end
