package kr.co.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
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

public class Test2 {

	public static String getCurrentData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return sdf.format(new Date());
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// 1. ���������� �ð� ���
		System.out.println(" Start Date : " + getCurrentData());
		int no = 1;

		for (no = 1; no <= 1; no++) {
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
				String subject = doc.select("div.col-md-8").get(0).text(); 	// ����
				String lyrics = doc.select("div.col-md-8").get(1).text(); 	// ����
				String metadata = doc.select("div.col-md-4").get(0).text();	// ����
				String singer = metadata.split(" ")[0].toString();
				String album ="";
				
				if(metadata.contains("��")){								// �ٹ� 
					album = metadata.substring(metadata.indexOf(":")+2, metadata.indexOf("��")+1);	// ��������
					album = album.replace(" ", "+");
				}
				
				if (metadata.contains("Unknown")) {							// ������ ���� ��� ����ó��
					System.out.println(no + " is pass");
					continue;
				}
				// ������ ũ�Ѹ� ���� 
				
			
				
				
/*
	��Ȯ�� �����긦 ����ϴµ� �߻� ������
	1. �뷡 �տ� ��ȣ�� �ٴ� ���
	- 1. 	: �տ� 0�� �ٿ��� �˻�
	- 01.	: ���� ��Ȯ�� ��� ����
	
	2. ���� ��ü�� Ʋ�� ��� -> ��Ȯ���� ���� url �ּ� ���� 
		- ����			: �������ŷ� ���� 
		- **�ܾ Ʋ�����	: #1  ���� -> ������
							  #95 jumbo -> jumpo
							  
	
	3. �ٹ����� �뷡 ����� ���� ���(Ÿ��Ʋ�� = �ٹ���)
 		- #81   
 
 
 */

				// youtube url �������� (*�޼ҵ� ���·� ����)
				String searchSubject = subject.replace(" ", "+");				// �˻���� ������ +�� 
				String compare="";
				// �պ���... 
				// ���� ������ Utility class �� ��Ʈ�� �޼ҵ�� �߰� 
				//  1. ����տ� ���ڰ� �ٴ� ���
				int index = subject.indexOf(".");	
				if(index==1) {	
					searchSubject = "0"+ searchSubject.replace(".", "+");
					compare = searchSubject.substring(index+1, searchSubject.length()).replace("+", " ").toLowerCase().trim();
				} else if(index==2) {
					compare = searchSubject.substring(index+1, searchSubject.length()).replace("+", " ").toLowerCase().trim();
				} else {
					compare = subject;
				}
				
				// youtube ��ũ ��������
				System.out.println(no);
				String href = searchYoutube(searchSubject, singer, compare);
				if(href.isEmpty()) {
					href = searchYoutube(searchSubject, singer, album, compare);
				}
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

				BufferedWriter out = new BufferedWriter(new FileWriter("./lyrics/" + no + ".txt")); // ������� ����� : ��ȣ.txt
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
			}
		}
		// 12. �󸶳� �ɷȳ� ����
		System.out.println(" End Date : " + getCurrentData());
	}
	
	// �˻�� �ٹ��� ���� ���
	public static String searchYoutube(String subject, String singer, String compare) throws ClientProtocolException, IOException {
		String href="";
		String url = "https://www.youtube.com/results?search_query=" + subject + "+" + singer; // youtube �˻� ��� url
		HttpPost http = new HttpPost(url);
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = httpClient.execute(http);
		HttpEntity entity = response.getEntity();
		ContentType contentType = ContentType.getOrDefault(entity);
		Charset charset = contentType.getCharset();
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
		StringBuffer sb = new StringBuffer();
		String line = ""; 
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		Document doc = Jsoup.parse(sb.toString());
		
		// comparSource �� ��Ȯ�� ���� ����
		Elements atag = doc.select("h3.yt-lockup-title a");
		// 2. ������ Ʋ�� ��� ��Ȯ�� ����
		String compareSource[] = compare.split(" ");
		
		//�ؽ������� �ٲ���
		/*
		 	�˰��� 
		 	Map1(key, Map2(key2,0�Ǵ�1))
		 	Map1 : resultMap
		 	Map2 : compareMap
		 	key1 : ���� Ÿ��Ʋ
		 	key2 : �˻���� Ÿ��Ʋ
		 	
		 */
		HashMap <String, Integer> compareMap= null;
		HashMap <String, HashMap > resultMap = null;
		
		for(int index=0; index < atag.size(); index++) {			// �±� ���� �񱳴�� ������ ã�� 
			Element element = atag.get(index);
			String compareTitle = element.attr("title");			// �� ����� ����
			
			for(index=0; index<compareSource.length; index++){		
				String resultTitleWord = compareSource[index];		// �� ���� �ܾ�
				if( compareTitle.contains(resultTitleWord)){		
				compareMap.put(compareTitle, 1);					// �� ��� ���񳻿� ���� �ܾ �����ϴ� ���
				}else{
				compareMap.put(compareTitle, 0);					// �������� �ʴ� ���
				}
				resultMap.put(resultTitleWord, compareMap);			// �񱳱��� �ܾ �ʿ� ����
			}
		}
		
		// ����ġ ���, ���� �ܾ�� +0.1
		resultMap.size();	// ��ü ��(�ܾ� ��)
		resultMap.values().size();  // ��ü 

		
		

		
		
		return href;
	}
	
	// �˻�� �ٹ��� �ִ� ���
	public static String searchYoutube(String subject, String singer, String album, String compare) throws ClientProtocolException, IOException {
		String href="";
		String url = "https://www.youtube.com/results?search_query=" + subject + "+" + singer + "+" + album; // youtube �˻� ��� url
		HttpPost http = new HttpPost(url);
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = httpClient.execute(http);
		HttpEntity entity = response.getEntity();
		ContentType contentType = ContentType.getOrDefault(entity);
		Charset charset = contentType.getCharset();
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
		StringBuffer sb = new StringBuffer();
		String line = ""; 
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		Document doc = Jsoup.parse(sb.toString());
		
		Elements atag = doc.select("h3.yt-lockup-title a");
		for(Element element: atag) {
			if(element.attr("title").toLowerCase().contains(compare)) {
				System.out.println("��û Ÿ��Ʋ : " + compare );
				System.out.println("���� Ÿ��Ʋ : " + element.attr("title"));
				href = element.attr("href");
				break;
			}
		}
		
		return href;
	}
}
