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
		logger.debug(jsonEmotion.toString());
		return jsonEmotion;
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
		logger.debug(jsonHistory.toString());
		return jsonHistory;
	}
	
	
	/*Bubble Menu ���� �޼ҵ�*/
	// �������� ���� �� Ű���带 ������ ��ȯ �ϴ� �Լ�
	@SuppressWarnings("unchecked")
	public static JSONObject getJsonBubbleMenu(List<DictionaryDTO> dictionaryList  ){				 
		Iterator<DictionaryDTO> iterator = dictionaryList.iterator();
		Random randomEmotion = new Random();
		String emotionType[] ={"happy","disgust","fear","interest","pain","rage","sad"};
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
		
		
		// bubble circle �Ѱ� ������ �� JSONObject ����
		JSONObject jsonEmotion = new JSONObject();
		for(int i=0; i<emotionType.length; i++){
			jsonEmotion.put("test", "test");
			
		}
		//logger.debug(jsonEmotion.toString());
		return jsonEmotion;
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
	
		for(ArrayList<Integer> point : listOfTopTenEmotion){
			logger.debug(String.valueOf(point));
		}
	
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
		
		logger.debug(jsonTopMusic.toJSONString());
		return jsonTopMusic;
		
	}
} // class end
