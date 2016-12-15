package kr.co.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
			// System.out.println(doc.select("div.col-md-8"));
			try {
				String content = doc.select("div.col-md-8").toString();
				String[] carray = content.split(">");
				String subject = carray[2].substring(0, carray[2].length() - 4);
				String[] sarray = doc.select("div.col-md-4").toString().split(">");
				BufferedWriter out = new BufferedWriter(new FileWriter(no+ " - " + subject + ".txt"));
				out.write("���� : " + subject);
				out.newLine();
				out.write("���� : " + sarray[1].substring(3, sarray[1].length() - 7));
				out.newLine();
				for (int i = 6; i < carray.length; i++) {
					out.write(carray[i].substring(0, carray[i].length() - 7));
					out.newLine();
				}
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ���� - Jsoup���� �����ϴ� Connect ó��
			Document doc2 = Jsoup.connect("http://finance.naver.com/item/coinfo.nhn?code=045510&target=finsum_more").get();
			//System.out.println(doc2.data());
		}
		// 12. �󸶳� �ɷȳ� ����
		System.out.println(" End Date : " + getCurrentData());
	}
}
