package kr.co.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
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
	
} // class end
