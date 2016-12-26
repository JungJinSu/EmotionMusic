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
		try {
			BufferedReader  dicWordReader = new BufferedReader(new FileReader("./dictionary/dictionaryword.txt"));
			BufferedReader  lyricsReader = new BufferedReader(new FileReader("./lyrics.txt"));
			BufferedWriter dicWordWriter = new BufferedWriter(new FileWriter("./dictionary/Exception word_range.txt"));				// ���� ����Ʈ ���� 
			String dicWord ="";				// �����ܾ������ ���Ե� �ܾ� 
			String lyrics ="";
			String line ="";
			// ���ܽ�ų �ܾ �б�
			while((line = dicWordReader.readLine()) != null) 
			{
				dicWord += line+" ";
				//System.out.println(line);
			}
			int count =0;
			// ��ü ���� ���� �б�
			while((line = lyricsReader.readLine()) != null) 
			{
				lyrics += line+"\n";
				System.out.println(count);
				count++;
			}
			
			dicWordReader.close();
			lyricsReader.close();
			
			// �����ܾ� ���� ��Ű�� 
		 	String removeWord[] = dicWord.split(" ");
		 	String resultLyrics = "";
		 	count=0;
		 	System.out.println("�����ܾ� ���� ����");
		 	for(String remove : removeWord){
		 		lyrics =  lyrics.replace(remove, "");
		 		System.out.println(count);
		 		count++;
		 		
			}
		 	
		 	resultLyrics = lyrics;
		 	
		 	System.out.println("�����ܾ� ���� ���");
		 	BufferedWriter resultWriter = new BufferedWriter(new FileWriter("./emotion/exceptionWord.txt"));				// ���� ����Ʈ ����
		 	String resultWord[] = resultLyrics.split("\n");			// �� �پ� ���Ͽ� �Է�
		 	for(String word : resultWord){
		 		resultWriter.write(word);
		 		resultWriter.newLine();
		 	}
		 	resultWriter.close();
		 	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
