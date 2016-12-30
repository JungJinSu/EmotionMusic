package kr.co.test;

import static org.hamcrest.CoreMatchers.equalTo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

/* 
* 	�������� ���� �޼ҵ�
* 
*  1. �����ܾ� ���� ���� �з� ���	: searchEmotionLyrics() 
*  2.  
*  
* 	
*/

public class Dictionary {
	public static void main(String[] args)throws ClientProtocolException, IOException  {
		
		searchEmotionLyrics();
	}
	
	public static void searchEmotionLyrics() throws IOException{
		System.out.println("�������� �ܾ� �б� ");
		BufferedReader  emotionWordReader=null; 	// �������� �ܾ�
		BufferedReader  lyricsReader=null;				// ���� �б�
		BufferedWriter   writer = null;					// ����
		String keyword ="";									// �������� �ܾ�
		String lyrics ="";										// ��ü ���� 
		String line ="";										// ���ϴ��� �б�
		int keywordcount=1;								// �������� �ܾ� ī��Ʈ
		
		emotionWordReader = new BufferedReader(new FileReader("./dictionary/keyword.txt"));
		while((line = emotionWordReader.readLine()) != null) 		// �������� �ܾ� �б�
		{
			keyword += line+"\n";
			System.out.println(keywordcount+" �����ܾ� : "+line);
			keywordcount++;
		}
		System.out.println("�� �����ܾ� ���� : "+ keywordcount);
	
		// ��ü ���縦 �� ��Ʈ���� ���� ����, ���� �ܾ �����ϴ� �������� �з�
		System.out.println("==========================================��ü ���� ��ĵ ����=========================================== ");
		
		
		String totalLyrics="";							// �����ܾ�� ���ϱ� ���� ��� ���縦 ������ 
		
		for(int no = 5000; no < 6001; no++){										
			System.out.println(no+" �� °, ���� ��ĵ");
			try {
				lyricsReader = new BufferedReader(new FileReader("./lyrics/"+no+".txt"));
				while((line = lyricsReader.readLine()) != null) { lyrics += line+"\n"; } // ����, ����, url �� �о� ����
				String lyricsArray[] = lyrics.split("\n");										// [2] ��°�� ���� �����	
				totalLyrics += lyricsArray[2]+" ";											// ���� ��� ���縦 ���ڿ��� ���� 
				lyrics="";																			// ���� ���� �ʱ�ȭ 
				
			} catch (FileNotFoundException e) {
				System.out.println("������ ����κ��� pass");
			}finally {
				emotionWordReader.close();
				lyricsReader.close();
			}
		}
		
		// �����ܾ� ���� ���� �з� 
		String emotionLyrics = "";														// �����ܾ �����ϴ� ���� ����, �ܾ�
		String emotionWordArray[] = keyword.split("\n");							// �����ܾ� �迭
		String totalLyricsArray[] = totalLyrics.split(" ");								// ��� ���縦 �������� ����
		int count=0;
	 	
		System.out.println("==========================================�����ܾ �����ϴ� ����з� ����=========================================== ");
	 	for(String lyricsWord : totalLyricsArray){
	 		for(String emotionWord : emotionWordArray){
	 			if(lyricsWord.contains(emotionWord)){								// ���翡 �����ܾ ���ԵǴ� ���
	 				//System.out.println("���� ����"+count);
	 				emotionLyrics +=  lyricsWord+" ";								
			 		count++;
	 			}
	 		}
		}
	 	
	 	String emotionLyricsWord[] = emotionLyrics.split(" ");								// �������� �з���. �ߺ��� �ܾ� �� ���� �߻��ϰ� �ȴ�.
	 	String conflictEmotion="";
	 	String conflictEmotions="";
	 	for(String temp : emotionLyricsWord){
	 		System.out.print(temp +"/");
	 	}
	 	System.out.println("");
	 	
	 	for(String word : emotionLyricsWord){									// �ߺ��� ������ �������� �ݺ���
	 		for(int index=0; index< emotionLyricsWord.length; index++){	// �ڽ��� �񱳴������ �ߺ� �������� ã��
	 			if(emotionLyricsWord[index].equals(word)){					
	 				//System.out.println("�ߺ� ��ġ �߻� : "+index + word);
	 				//System.out.println(index); 									// @@@@@�ε����� �帧�� ��������. => �� ���ڿ��� ���� �ϸ鼭 �ߺ� ���ڿ� ���Ű���
	 				emotionLyricsWord[index]="";									// �ߺ��� ��������� �迭���� ���� 
	 				conflictEmotion = word;										// �������縦 �� ���ڿ��� ����
	 			}
	 		}
	 		
	 		conflictEmotions += conflictEmotion+" ";							// ����� ��������, �ٽ� �ѹ� ����. �帧����
	 		
	 	}
	 	System.out.println(conflictEmotions);
	 	
	 	System.out.println("�����ܾ� ���� ��� ���");
	 	BufferedWriter resultWriter = new BufferedWriter(new FileWriter("./emotion/emotionKeyWordResult.txt"));				// ���� ����Ʈ ����
	 	String resultWord[] = conflictEmotions.split(" ");			// �� �پ� ���Ͽ� �Է�
	 	for(String word : resultWord){
	 		if(!word.equals("")){							// �ߺ����� ���ܵ� �κ��� ������� �ʴ´�.
	 			resultWriter.write(word);
	 			resultWriter.newLine();
	 		}
	 	}
	 	resultWriter.close();
	}

}
