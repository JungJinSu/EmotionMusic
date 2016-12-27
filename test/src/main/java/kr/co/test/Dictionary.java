package kr.co.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

/* 
 	�����ܾ� �з� �۾��� ���� Ŭ����. 
*/

public class Dictionary {
	public static void main(String[] args)throws ClientProtocolException, IOException  {
		System.out.println("���� �ܾ� �з� �۾� ���� ");
			
			BufferedReader  emotionWordReader = new BufferedReader(new FileReader("./dictionary/keyword.txt"));
			String keyword ="";				// �������� �ܾ�
			String lyrics ="";
			String line ="";
			int keywordcount=1;
			// �������� �ܾ� �б�
			while((line = emotionWordReader.readLine()) != null) 
			{
				keyword += line+"\n";
				System.out.println(keywordcount+" �����ܾ� : "+line);
				keywordcount++;
			}
			System.out.println("�� �����ܾ� ���� : "+ keywordcount);
			emotionWordReader.close();

			// ��ü ���縦 �� ��Ʈ���� ���� ����, ���� �ܾ �����ϴ� �������� �з�
			// ��ü ���� ���� �б�
			System.out.println("==========================================��ü ���� ��ĵ ����=========================================== ");
			BufferedReader  lyricsReader=null;
			for(int no = 1; no < 1000; no++){
				System.out.println(no+" �� °, ���� ��ĵ");
				try {
					lyricsReader = new BufferedReader(new FileReader("./lyrics/"+no+".txt"));
					while((line = lyricsReader.readLine()) != null) 
					{
						lyrics += line+" ";			 					
					}
				} catch (FileNotFoundException e) {
					System.out.println("������ ����κ��� pass");
				}finally {
					lyricsReader.close();
				}
				
			}
			
			String lyricsArray[] = lyrics.split(" ");						// ��ü ���縦 ������ �������� �ּ� (�ܾ�, ����) ����	
			
			// �����ܾ� ���� ���� �з� 
			String emotionWordArray[] = keyword.split("\n");		
		 	String resultLyrics = "";
		 	int count=0;
		 	System.out.println("==========================================�����ܾ �����ϴ� ����з� ����=========================================== ");
		 	for(String lyricsWord : lyricsArray){
		 		for(String emotionWord : emotionWordArray){
		 			if(lyricsWord.contains(emotionWord)){							// ���翡�� �� �ܾ� �Ǵ� ����ӿ� �����ܾ �����ϴ� ���
		 				System.out.println("���� ����"+count);
				 		resultLyrics +=  lyricsWord+" ";								
				 		count++;
		 			}
		 		}
			}
		 	String scanWord[] = resultLyrics.split(" ");								// �������� �з���. �ߺ��� �ܾ� �� ���� �߻�
		 	resultLyrics="";																// ����� �ʱ�ȭ
		 	lyrics=""; 																		// �ߺ��� �ܾ�� ������ ������ �����ͷ� ���� 
		 	
//		 	for(String conflictWord : scanWord){
//		 		for(int index=0; index<scanWord.length; index++){
//		 			if(scanWord[index].equals(conflictWord)){					// ���� �ܾ�, ������ ��� �ϳ��� ����
//		 				System.out.println(scanWord[index]+"/"+conflictWord+" : ���� �ܾ� �߰��� �ߺ� ó��.");
//		 				resultLyrics += 
//		 			}
//		 		}
//		 	}
		 	
		 	
		 	System.out.println("�����ܾ� ���� ��� ���");
		 	BufferedWriter resultWriter = new BufferedWriter(new FileWriter("./emotion/emotionKeyWordResult.txt"));				// ���� ����Ʈ ����
		 	String resultWord[] = resultLyrics.split("\n");			// �� �پ� ���Ͽ� �Է�
		 	for(String word : resultWord){
		 		resultWriter.write(word);
		 		resultWriter.newLine();
		 	}
		 	resultWriter.close();
	}

}
