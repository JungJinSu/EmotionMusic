package kr.co.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Crawler {
	public static Document crawl(String url) throws ClientProtocolException, IOException {
   
		HttpPost http = new HttpPost(url); // ������ HTTP �ּ� ����
		HttpClient httpClient = HttpClientBuilder.create().build();		// �������⸦ ������ Ŭ���̾�Ʈ ��ü����
		HttpResponse response = httpClient.execute(http); 				// ���� �� ���� �����͸� Response ��ü�� ����
		HttpEntity entity = response.getEntity(); 						// Response ���� ������ ��, DOM �����͸� ������ Entity�� ����

		ContentType contentType = ContentType.getOrDefault(entity); 	// Charset�� �˾Ƴ��� ���� DOM�� ����Ʈ Ÿ���� ������ ���Charset�� ������
		Charset charset = contentType.getCharset();

		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset)); // DOM�����͸� �� �پ� �б� ���� Reader�� ���� (InputStream Buffered �� ������ ��������)
		StringBuffer sb = new StringBuffer(); 							// ������ DOM �����͸� ������� �׸�

		String line = ""; // DOM ������ ��������
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		Document doc = Jsoup.parse(sb.toString()); // Jsoup���� �Ľ�

		return doc;
	} // crawl() end

	public void getMusicContents() throws ClientProtocolException, IOException {
		System.out.println(" Start Date : " + Utility.getCurrentDate());	// ���������� �ð� ���
		int no = 0;

		for (no = 1; no <= 1; no++) {
			String url = "http://gasazip.com/" + no;
			Document doc = crawl(url);
			try {
				String subject = doc.select("div.col-md-8").get(0).text(); 	// ����
				String lyrics = doc.select("div.col-md-8").get(1).text(); 	// ����
				String singer = doc.select("div.col-md-4").get(0).text();	// ����
				String album ="";
				
				if(singer.contains("��")){									// �ٹ� 
					album = singer.substring(singer.indexOf(":")+2, singer.indexOf("��")+1);	// ��������
				
				}
				
				if (singer.contains("Unknown")) {							// ������ ���� ��� ����ó��
					System.out.println(no + " is pass");
					continue;
				}
				
				BufferedWriter out = new BufferedWriter(new FileWriter("./lyrics/" + no + ".txt")); // ������� ����� : ��ȣ.txt
				out.write("���� : " + subject);
				out.newLine(); 
				out.write("���� : "+ singer);
				out.newLine();
				out.write(lyrics);
				out.newLine();
				out.write(getYoutubeURL(subject, singer, album));
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} // getcontents() end
	
	public String getYoutubeURL(String subject, String singer, String album) throws ClientProtocolException, IOException {
		
		//youtube url �������� (*�Լ�������)
		
		String searchSubject = subject.replace(" ", "+");
		String url = "https://www.youtube.com/results?search_query=" + searchSubject + "+" + singer + "+" + album; // youtube �˻� ��� url
		Document doc = crawl(url);
		
		// ��� �� ��� ���°� �ƴ� �ϳ��� ������ ����� ��ũ �κ� ��������
		String[] varray = doc.select("div.contains-addto a").toString().split(" "); // contains-addto - ���� ��� ������
		String resultValue="";
		for(int idx=0; idx<varray.length; idx++) {
			if(varray[idx].length()>6) {
				if(varray[idx].substring(0,4).equals("href")) {
					resultValue = varray[idx].substring(6, 26); // ��ũ�� �ּҺκ� ����
				}
			}
			if(!resultValue.isEmpty()) // ��ũ�ּҸ� ����� ����������
				break; 
		}
		return "https://www.youtube.com"+ resultValue;	// ���� ù��° ����� url
	}
} // class end
