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
		logger.debug(String.valueOf(listOfEmotionWord[0].size()));
		logger.debug(String.valueOf(listOfEmotionWord[1].size()));
		logger.debug(String.valueOf(listOfEmotionWord[2].size()));
		logger.debug(String.valueOf(listOfEmotionWord[3].size()));
		logger.debug(String.valueOf(listOfEmotionWord[4].size()));
		logger.debug(String.valueOf(listOfEmotionWord[5].size()));
		logger.debug(String.valueOf(listOfEmotionWord[6].size()));
		
		/*for(String emotion:emotionType){
			logger.debug(emotion + " : ");
			for(int index=0; index<3; index++){
				logger.debug(String.valueOf( randomEmotion.nextInt(547)));
			}
		}*/
		
		
		// person�� �Ѹ� ������ �� JSONObject ����
		JSONObject jsonEmotion = new JSONObject();
		for(int i=0; i<emotionType.length; i++){
			jsonEmotion.put("test", "test");
			
		}
		logger.debug(jsonEmotion.toString());
		return jsonEmotion;
	}
} // class end
