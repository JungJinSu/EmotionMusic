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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
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

		for (no = 30; no <= 30; no++) {
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

			// 7. DOM �����͸� �� �پ� �б� ���� Reader�� ���� (InputStream / Buffered ��
			// ������ ��������)
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));

			// 8. ������ DOM �����͸� ������� �׸�
			StringBuffer sb = new StringBuffer();

			// 9. DOM ������ ��������
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

			// 10. ������ DOM�� ����
			// System.out.println(sb.toString());

			// 11. Jsoup���� �Ľ��غ���.
			Document doc = Jsoup.parse(sb.toString());
			// StringBuffer str = new StringBuffer();

			// System.out.println(doc.select("div.col-md-8"));
			try {
				//String content = doc.select("div.col-md-8").toString(); // ����, ���� �κ�
				//Elements contents = doc.select("div.col-md-8"); // ����, ���� �κ�

				String subject = doc.select("div.col-md-8").get(0).text(); 	// ����
				String lyrics = doc.select("div.col-md-8").get(1).text(); 	// ����
				String metadata = doc.select("div.col-md-4").get(0).text();	// ����
				String singer = metadata.split(" ")[0].toString();
				String album ="";
				
				if(metadata.contains("��")){									// �ٹ� 
					album = metadata.substring(metadata.indexOf(":")+2, metadata.indexOf("��")+1);	// ��������
				}
				
				if (metadata.contains("Unknown")) {							// ������ ���� ��� ����ó��
					System.out.println(no + " is pass");
					continue;
				}
				// ������ ũ�Ѹ� ���� 
				
				
				
				
				// youtube url �������� (*�Լ�������)
				String searchSubject = subject.replace(" ", "+");
				String url = "https://www.youtube.com/results?search_query=" + searchSubject + "+" + singer + "+" + album; // youtube �˻� ��� url
				http = new HttpPost(url);
				httpClient = HttpClientBuilder.create().build();
				response = httpClient.execute(http);
				entity = response.getEntity();
				contentType = ContentType.getOrDefault(entity);
				charset = contentType.getCharset();
				br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
				sb = new StringBuffer();
				line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				doc = Jsoup.parse(sb.toString());
				// ��� �� ��� ���°� �ƴ� �ϳ��� ������ ����� ��ũ �κ� ��������
				
				// ����������� title�� ���ϱ� ���� ������ ���Ե� ��ȣ���� 
				int index = subject.indexOf(".");
				
				
				System.out.println(url);
				Elements atag = doc.select("h3.yt-lockup-title a");
				System.out.println(atag);
				for(Element element: atag) {
					if(element.attr("title").contains(subject)){
						System.out.println("��û Ÿ��Ʋ : " + subject );
						System.out.println("���� Ÿ��Ʋ : " +  element.attr("title"));
						break;
					}
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
				String sourceUrl = "https://www.youtube.com"; // ����
																			// ù��°
																			// �����
																			// url

				BufferedWriter out = new BufferedWriter(new FileWriter("./lyrics/" + no + ".txt")); // �������
																									// �����
																									// :
																									// ��ȣ.txt
				// ������� ����
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
}
