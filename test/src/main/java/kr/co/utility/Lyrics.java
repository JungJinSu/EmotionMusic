package kr.co.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;


/*
 * ���� ���� �޼ҵ�
 * 
 * 1. ������ ���� ����  
 * 
 */

public class Lyrics {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		countLyrics();
	}
	
	// lyrics ������ ������ ���� ��ȣ ã��
	public static void countLyrics() throws IOException{
		int startNo = 192614;
		int finishNo = 400000;
		int count =1;
		String omissionLyricsNo="";
		BufferedReader reader=null;
		
		// ���� ���� Ȯ�� 
		for(int no = startNo; no<finishNo; no++ ){
			try {
				reader = new BufferedReader(new FileReader("./lyrics/"+no+".txt"));

			} catch (FileNotFoundException e) {								// ������ ���� 
				System.out.println("������ ���� ��ȣ : " + no);
				omissionLyricsNo += no+"/";
				count++;
				reader.close();
			}
		};
		
		// ������ ���� ����
		BufferedWriter writer = new BufferedWriter(new FileWriter("./errorReport/omissionLyrics.txt"));	
		String result[] = omissionLyricsNo.split("/");
		for(String omissionNo : result){
	 		writer.write(omissionNo);
	 		writer.newLine();
	 	}
		writer.write("�� ��� : "+ count);
		writer.close();		// ��� ��ȣ�� ������ �� �ݱ�
		
		System.out.println("���� �Ѱ� : "+ count);
	}
}
