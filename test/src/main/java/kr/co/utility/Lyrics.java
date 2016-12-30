package kr.co.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
/*
 * ���� ���� �޼ҵ�
 * 
 * 1. ������ ���� �˻� ���	:	countLyrics(int, int)
 * 2. �˼� ���� ���� ���		:	searchPOP(int, int)
 * 3.  ���� ���� �Լ�			:    lyricsWrite(String, String, int )
 */

public class Lyrics {
	 
	public static void main(String[] args) throws ClientProtocolException, IOException {
		int startNo=100618, finishNo=100700;
		//countLyrics();
		searchPOP(startNo, finishNo);
		
	}
	
	// lyrics ������ ������ ���� ��ȣ ã��
	public static void countLyrics(int startNo, int finishNo) throws IOException{
		// 19��~ 50��  : 31���� ��ĵ
		int count =1;
		String omissionLyricsNo="";
		BufferedReader reader=null; 
		
		// ���� ���� Ȯ�� 
		for(int no = startNo; no<=finishNo; no++ ){
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
		writer.close();		// ��� ��ȣ�� ������ �� �ݱ�
		
		System.out.println("���� �Ѱ� : "+ count);
	}
	
	// 62���� �߿��� �˼� ���� ���� 
	public static void searchPOP(int startNo, int finishNo) throws IOException{
		BufferedReader  lyricsReader=null;	// ���� �б�
		String lyrics ="";							// ��ü ���� 
		String line ="";							// ���ϴ��� �б�
		
		for(int no = startNo; no <= finishNo; no++){										// ���縦 ��ĵ�ϸ鼭 ����/�ѱ�(�Ϻ�,����,Ư������ ����) �뷡 ���� �ϱ�  
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
					lyrics += line+"\n";		// ��ü ���縦 ������ ��, ����� ���� �������� 			 					
				} 
				//System.out.println("Lyrics : "+lyrics);
				
				String replaceLyrics =lyrics.replace(" ","").replace(",", "").replace("'", "").replace("?", "").replace("/",	 "").replace("(","").replace(")","").replace("-","").replace("~","");	// Ư������ ����
				koreaWordCount = replaceLyrics.length()-englishWordCount;		//  ���ĺ� / �ѱ���(�Ϻ�,����) ����
				
				
				System.out.println(" �ѱ� ��(�� �� ��������)  : " + koreaWordCount);
				System.out.println(" ���ĺ� �� : " + englishWordCount);
				
				if( koreaWordCount > englishWordCount || koreaWordCount >=150){			// 1.  ���ĺ� �� ���ڼ��� �� ���ų�, 50�� �̻��� ��� �˼��� Ȯ���� ����
					System.out.println("�� �뷡�� ���� �뷡�� �ƴմϴ�.");
					lyricsWrite("koreaLyrics", lyrics , no);
				
				}else if(replaceLyrics.length()<150){														// 2. ���翡 ���ڼ��� 150 �̸��� ��� ���簡 ���°ɷ� �Ǵ�, ���ܽ�Ŵ
					System.out.println("�� �뷡�� ���簡 �����ϴ�.");
					lyricsWrite("emptyLyrics", lyrics, no);
				}else{
					System.out.println("�� �뷡�� ���� �뷡 �Դϴ�.");									// 3. ���ĺ��� ���� ������ ������ ��� �˼��� Ȯ���� ����.
					lyricsWrite("englishLyrics", lyrics, no);
				}
				
				lyricsReader.close();
				lyrics="";
				
			} catch (FileNotFoundException e) {
				System.out.println("������ ����κ��� pass");
			}
		}
	}
	
	// ���� ���� �Լ� ( �˼�, �ѱ��뷡, ���簡 ���� �뷡 �����ؼ� ������ ����)
	public static void lyricsWrite(String folder, String lyrics,  int no) throws IOException{
		BufferedWriter   writer = null;
		writer = new BufferedWriter(new FileWriter("./"+folder+"/"+no+".txt"));		// �˼� �뷡 ����
		String writeLine[] = lyrics.split("\n");
	 	
		for(String line2 : writeLine){
	 		writer.write(line2);
	 		writer.newLine();
	 	}
	 	writer.close();
	}
	
}
