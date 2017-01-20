package kr.co.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.dao.MediaDAO;
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
} // class end
