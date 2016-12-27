package kr.co.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import kr.co.utility.Crawler;

public class Test2 {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		// 1. ���������� �ð� ���
		System.out.println(" Start Date : " + getCurrentData());
		//int noStart = 36741;					// ���� ũ�Ѹ� ���� ��ȣ
		//int noFinish = 161000;					// ���� ũ�Ѹ� �� ��ȣ
		// ������� ���� Error Report �ۼ� 
		//System.out.println("Error Report Open");
		//int errorNo=1, passedNo=1;																																	// ���� ����, �н��� ���� ī��Ʈ
		//BufferedWriter errorOut = new BufferedWriter(new FileWriter("./errorReport/Exception Lyrics_"+ noStart+"~"+noFinish+"_range.txt"));				// ���� ����Ʈ ���� 
		//BufferedWriter passedOut = new BufferedWriter(new FileWriter("./errorReport/Passed Lyrics_"+ noStart+"~"+noFinish+"_range.txt"));		// �н��� ���� ����Ʈ ���� 
		
		// ������ ���縦 �迭�� ���� 
		BufferedReader  reader = new BufferedReader(new FileReader("./errorReport/omissionLyrics2.txt"));
		int count=1;
		String omissionLyrics="";
		String omissionLine="";
		while((omissionLine = reader.readLine()) != null) 
		{
			omissionLyrics += omissionLine+"/";
			System.out.println("������ ���� ��ȣ : "+ omissionLine);
			count++;
		}
		String omissionNo[] = omissionLyrics.split("/");
		
		System.out.println("������ ���� �� ���� : "+ omissionNo.length);
		System.out.println("===================================ũ�Ѹ� ����===========================================");
		
		count=1;
		// ������ ���縸 ũ�Ѹ� �׽�Ʈ�ϱ�
		for (String no : omissionNo) {
			System.out.print("No."+count+" ");
			// 2. ������ HTTP �ּ� ����
			HttpPost http = new HttpPost("http://gasazip.com/" + no + "");

			// 3. �������⸦ ������ Ŭ���̾�Ʈ ��ü ����
			HttpClient httpClient = HttpClientBuilder.create().build();

			// 4. ���� �� ���� �����͸� Response ��ü�� ����
			HttpResponse response = httpClient.execute(http);

			// 5. Response ���� ������ ��, DOM �����͸� ������ Entity�� ����
			HttpEntity entity = response.getEntity();

			// 6. Charset�� �˾Ƴ��� ���� DOM�� ����Ʈ Ÿ���� ������ ��� Charset�� ������
			ContentType contentType = ContentType.getOrDefault(entity);
			Charset charset = contentType.getCharset();

			// 7. DOM �����͸� �� �پ� �б� ���� Reader�� ���� (InputStream / Buffered �� ������ ��������)
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
 
			// 8. ������ DOM �����͸� ������� �׸�
			StringBuffer sb = new StringBuffer();
 
			// 9. DOM ������ ��������
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

			// 11. Jsoup���� �Ľ��غ���.
			Document doc = Jsoup.parse(sb.toString());
			try {
				String subject = doc.select("div.col-md-8").get(0).text().replace("'","").replace("<","").replace(">","").replace("`","").replace("*",""); 	// ����
				String lyrics = doc.select("div.col-md-8").get(1).text(); 	// ����
				String metadata = doc.select("div.col-md-4").get(0).text();	// ����
				String singer = metadata.split(" ")[0].toString();
				String album ="";
				 
				if(metadata.contains("��")){								// �ٹ� 
					album = metadata.substring(metadata.indexOf(":")+2, metadata.indexOf("��")+1);	// ��������
					album = album.replace(" ", "+");
				}
			
				if (metadata.contains("Unknown")) {							// ������ ���� ��� ����ó��
					System.out.println(no + " is pass..");
//					passedOut.write("�н�No."+passedNo+" / ");
//					passedOut.write("����No." + no +" is passed");
//					passedOut.newLine();
//					passedNo++;
					continue;
				}
				// ������ ũ�Ѹ� ���� 
	
				// youtube url �������� (*�޼ҵ� ���·� ����)
				String searchSubject = subject.replace(" ", "+");				// �˻���� ������ +�� 
				String compare=singer+" ";										// �񱳱��ؿ� ���� �߰�
				// �պ���... 
				// ���� ������ Utility class �� ��Ʈ�� �޼ҵ�� �߰� 
				
				//  1. ����տ� ���ڰ� �ٴ� ���
				int index = subject.indexOf(".");	
				if(index==1) {	
					searchSubject = "0"+ searchSubject.replace(".", "+");
					compare += searchSubject.substring(index+1, searchSubject.length()).replace("+", " ").toLowerCase().trim();
				} else if(index==2) {
					compare += searchSubject.substring(index+1, searchSubject.length()).replace("+", " ").toLowerCase().trim();
				} else {
					compare += subject.toLowerCase();
				}
				
				// youtube ��ũ ��������
				System.out.println(no);
				String href = searchYoutube(searchSubject, singer,album, compare);
				
				if(href.isEmpty()) {
					System.out.println("https://www.youtube.com/results?search_query=" + searchSubject + "+" + singer + "+" + album);
				}

				/*String resultValue = "";
				for (int idx = 0; idx < varray.length; idx++) {
					if (varray[idx].length() > 6) {
						if (varray[idx].substring(0, 4).equals("href")) {
							resultValue = varray[idx].substring(6, 26); // ��ũ��
																		// �ּҺκ�
																		// ����
						}
					}
					if (!resultValue.isEmpty()) // ��ũ�ּҸ� ����� ����������
						break;
				}*/
				String sourceUrl = "https://www.youtube.com" + href; 

				BufferedWriter out = new BufferedWriter(new FileWriter("./omissionLyrics/" + no + ".txt")); // ������� ����� : ��ȣ.txt
				
				
				out.write("���� : " + subject);
				out.newLine();
				out.write("���� : " + singer);
				out.newLine();
				out.write(lyrics);
				out.newLine();
				out.write(sourceUrl);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				//System.out.println("���� �߻�! ������ �����մϴ�.");			// ������� ���� ���ܰ��Ͼ�� ������ ��� �ؽ�Ʈ ���Ϸ� ����
//				errorOut.write("����No."+errorNo+" / ");
//				errorOut.write("����No." + no);
//				errorOut.newLine();
//				errorOut.write(e.toString());
//				errorOut.newLine();
//				errorNo++;
			}
			count++;
		}
		//System.out.println("Error Report Close");
//		errorOut.close();
//		passedOut.close();
		// 12. �󸶳� �ɷȳ� ����
		System.out.println(" End Date : " + getCurrentData());
	}
	
	// �˻�� �ٹ��� ���� ���
	public static String searchYoutube(String subject, String singer, String album, String compare) throws ClientProtocolException, IOException {
		String url="";
		if(album.equals("")){
			url = "https://www.youtube.com/results?search_query=" + subject + "+" + singer; 	// youtube �˻� ��� url
		}else{
			url = "https://www.youtube.com/results?search_query=" + subject + "+" + singer + "+" + album;
		}
		Document doc= Crawler.crawl(url);
		Elements atag = doc.select("h3.yt-lockup-title a");
		String playHrefs[] = new String[atag.size()];				// ������ ��ũ
		String playTitle[] = new String[atag.size()];
		
		
		// 2. ������ ��Ȯ�� ����, ���� ���� ���ؾ��Ѵ�.
		// #73 ���� ���(19�� ����) : https://www.youtube.com/watch?v=xJNKkhaaRGM&list=PLte5SNR6rfS3vXAtzBo8yMnaS2FokhNSN
		String compareSource[] = compare.split(" "); 
		int weights[] = new int[atag.size()];								// �� Ÿ��Ʋ�� ����ġ ���
		for(int index=0; index < atag.size(); index++) {				// �±� ���� �񱳴�� ������ ã�� 
			Element element = atag.get(index);
			String compareTitle = element.attr("title").toLowerCase();	// �� ����� ����, ������ ��� �ҹ��ڷ�
			playTitle[index] = element.attr("title");							// �����ũ ���� ����
			playHrefs[index] = element.attr("href");						// �����ũ ��� ����
			
			weights[index] = 0; 											// ����ġ �ʱⰪ
			for(String resultTitleWord : compareSource){				// �� ���� �ܾ�
				if( compareTitle.contains(resultTitleWord)){				// ����ġ �ջ�
					weights[index] += 1;
				}
			}
		}
	
		int maxWeight = weights[0];				// �ʱⰪ
		int correct=0;					
		int index=-1;							// �迭 0��° ���� �ֱ����� �ʱⰪ -1
		for(int weight :weights){				// �ִ밪 ���ϱ�
			index++;
//			System.out.println(" ����ġ ��� : " + weight);
			if(maxWeight < weight){				// ����ġ�� ���� ��� ���� ���� ������ ����Ŵ	
				maxWeight = weight;
				correct = index;				// ���� ��Ȯ�� ������ �ּ� �ε���
			}
		}
		//System.out.println(playHrefs[correct]);
		
		return playHrefs[correct];					// ����ġ �ִ밪�� ���� �ּҸ� ����
	}
	
	public static String getCurrentData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return sdf.format(new Date());
	}
}
