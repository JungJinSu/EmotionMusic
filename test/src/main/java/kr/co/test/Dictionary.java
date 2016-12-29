package kr.co.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
			String lyrics ="";					// ��ü ���� 
			String line ="";					// ���Ͽ��� ���پ� �б�
			int keywordcount=1;			// �������� �ܾ� ī��Ʈ
		
			while((line = emotionWordReader.readLine()) != null) 		// �������� �ܾ� �б�
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
			BufferedWriter   writer = null;
			for(int no = 5472; no < 5473; no++){										// ���縦 ��ĵ�ϸ鼭 ����/�ѱ�(�Ϻ�,����,Ư������ ����) �뷡 ���� �ϱ�  
				int englishWordCount=0;
				int koreaWordCount =0;
				System.out.println(no+" �� °, ���� ��ĵ");
				try {
					lyricsReader = new BufferedReader(new FileReader("./lyrics/"+no+".txt"));
					
					while((line = lyricsReader.readLine()) != null) 
					{
						for(int index=0; index<line.length(); index++){			
							if( line.charAt(index)>='A' && line.charAt(index)<='Z' || line.charAt(index)>='a' && line.charAt(index)<='z'){  	// ���� ���̿� ���ĺ� �������� ���� �뷡�� �����Ѵ�.
								englishWordCount++;
							}  
						}
						System.out.println("");	// ���ٶ���
						lyrics += line+" ";		// ��ü ���縦 ������ ��, ����� ���� �������� 			 					
					
					} 
				
					koreaWordCount = lyrics.replace(" ","").length()-englishWordCount;		//  ���ĺ� / �ѱ���(�Ϻ�,����,Ư������ ����) ����
					
					System.out.println(" �ѱ� ��(�� �� ��������)  : " + koreaWordCount);
					System.out.println(" ���ĺ� �� : " + englishWordCount);
					
					if( koreaWordCount > englishWordCount || koreaWordCount >=50){		// �ѱ� ���� �� ���ų�, �ѱ��� 50�� �̻��� ��� �ѱ��뷡
						System.out.println("�� �뷡�� ���� �뷡�� �ƴմϴ�.");
					}else{
						System.out.println("�� �뷡�� ���� �뷡 �Դϴ�.");
						File englishLyrics = new File("./lyrics/"+no+".txt");							// ������ �������� �����ϰ� ���ο� ������ �̵���Ű�� 
						englishLyrics.delete();
						
						writer = new BufferedWriter(new FileWriter("./englishLyrics/"+no+".txt"));	// �˼� �뷡 ����
						
						
//						//���� 
//						String result[] = omissionLyricsNo.split("/");
//						for(String omissionNo : result){
//					 		writer.write(omissionNo);
//					 		writer.newLine();
//					 	}
//						writer.close();		// ��� ��ȣ�� ������ �� �ݱ�
						
					}
					
				} catch (FileNotFoundException e) {
					System.out.println("������ ����κ��� pass");
				
				}finally {
					
					lyricsReader.close();
				}
				
			}
			
			String lyricsArray[] = lyrics.split(" ");										// ��ü ���縦 ������ �������� �ּ� (�ܾ�, ����) ����	
			
			// �����ܾ� ���� ���� �з� 
//			String emotionWordArray[] = keyword.split("\n");		
//		 	String resultLyrics = "";
//		 	int count=0;
//		 	System.out.println("==========================================�����ܾ �����ϴ� ����з� ����=========================================== ");
//		 	for(String lyricsWord : lyricsArray){
//		 		for(String emotionWord : emotionWordArray){
//		 			if(lyricsWord.contains(emotionWord)){							// ���翡�� �� �ܾ� �Ǵ� ����ӿ� �����ܾ �����ϴ� ���
//		 				System.out.println("���� ����"+count);
//				 		resultLyrics +=  lyricsWord+" ";								
//				 		count++;
//		 			}
//		 		}
//			}
//		 	String scanWord[] = resultLyrics.split(" ");								// �������� �з���. �ߺ��� �ܾ� �� ���� �߻�
//		 	resultLyrics="";																// ����� �ʱ�ȭ
//		 	lyrics=""; 																		// �ߺ��� �ܾ�� ������ ������ �����ͷ� ���� 
//		 	
//		 	for(String conflictWord : scanWord){
//		 		for(int index=0; index<scanWord.length; index++){
//		 			if(scanWord[index].equals(conflictWord)){					// ���� �ܾ�, ������ ��� �ϳ��� ����
//		 				System.out.println(scanWord[index]+"/"+conflictWord+" : ���� �ܾ� �߰��� �ߺ� ó��.");
//		 				resultLyrics += 
//		 			}
//		 		}
//		 	}
		 	
		 	
//		 	System.out.println("�����ܾ� ���� ��� ���");
//		 	BufferedWriter resultWriter = new BufferedWriter(new FileWriter("./emotion/emotionKeyWordResult.txt"));				// ���� ����Ʈ ����
//		 	String resultWord[] = resultLyrics.split("\n");			// �� �پ� ���Ͽ� �Է�
//		 	for(String word : resultWord){
//		 		resultWriter.write(word);
//		 		resultWriter.newLine();
//		 	}
//		 	resultWriter.close();
	}

}
