package kr.co.test;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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


public class Ktest {
	
	public static String getCurrentData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return sdf.format(new Date());
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		// 1. ���������� �ð� ���
		System.out.println(" Start Date : " + getCurrentData());
		
		
		//
		
		for(int no=1;no<=2;no++){
		// 2. ������ HTTP �ּ� ����
	    HttpPost http = new HttpPost("http://gasazip.com/"+no);

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
	    while((line=br.readLine()) != null){
	    	sb.append(line+"\n");
	    }
	    
	    // 10. ������ �Ƹ��ٿ� DOM�� ����
	    //System.out.println(sb.toString());
	    
	    // 11. Jsoup���� �Ľ��غ���.
	    Document doc = Jsoup.parse(sb.toString());
	    
	    
	    System.out.println(doc.select("div.col-md-8"));
	    String gasa = doc.select("div.col-md-8").toString();
	    
	    
	    /*
	 // ���� ����ϱ�
	 		try {
	 			String fileName="D:/java0802/song.txt";
	 			// ��� ������ ������ ����
	 			// append: true �߰�, false ���ľ���(overwrite)
	 			FileWriter fw=new FileWriter(fileName,false);
	 			PrintWriter out=new PrintWriter(fw, true);  // true ���� Ŭ����
	 			out.println(doc.select("div.col-md-8"));

	 			out.close();
	 			fw.close();
	 			System.out.println("������ ���� ���� �Ϸ�!!");
	 			
	 		}catch(Exception e){
	 			System.out.println("���� �б� ����!!  "+e);
	 		}
	    */
		}
	    
	    // 12. �󸶳� �ɷȳ� ����
	    System.out.println(" End Date : " + getCurrentData());
	}
}
