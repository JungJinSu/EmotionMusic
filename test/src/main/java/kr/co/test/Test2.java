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
		// 1. 가져오기전 시간 찍기
		System.out.println(" Start Date : " + getCurrentData());
		int no = 1;

		for (no = 1; no <= 1; no++) {
			// 2. 가져올 HTTP 주소 세팅
			HttpPost http = new HttpPost("http://gasazip.com/" + no + "");

			// 3. 가져오기를 실행할 클라이언트 객체 생성
			HttpClient httpClient = HttpClientBuilder.create().build();

			// 4. 실행 및 실행 데이터를 Response 객체에 담음
			HttpResponse response = httpClient.execute(http);

			// 5. Response 받은 데이터 중, DOM 데이터를 가져와 Entity에 담음
			HttpEntity entity = response.getEntity();

			// 6. Charset을 알아내기 위해 DOM의 컨텐트 타입을 가져와 담고 Charset을 가져옴
			ContentType contentType = ContentType.getOrDefault(entity);
			Charset charset = contentType.getCharset();

			// 7. DOM 데이터를 한 줄씩 읽기 위해 Reader에 담음 (InputStream / Buffered 중 선택은 개인취향)
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));

			// 8. 가져온 DOM 데이터를 담기위한 그릇
			StringBuffer sb = new StringBuffer();

			// 9. DOM 데이터 가져오기
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

			// 11. Jsoup으로 파싱해보자.
			Document doc = Jsoup.parse(sb.toString());
			
			try {
				String subject = doc.select("div.col-md-8").get(0).text(); 	// 제목
				String lyrics = doc.select("div.col-md-8").get(1).text(); 	// 가사
				String metadata = doc.select("div.col-md-4").get(0).text();	// 가수
				String singer = metadata.split(" ")[0].toString();
				String album ="";
				
				if(metadata.contains("집")){								// 앨범 
					album = metadata.substring(metadata.indexOf(":")+2, metadata.indexOf("집")+1);	// 공백제외
					album = album.replace(" ", "+");
				}
				
				if (metadata.contains("Unknown")) {							// 가수가 없는 경우 예외처리
					System.out.println(no + " is pass");
					continue;
				}
				// 가사집 크롤링 종료 
				
			
				
				
/*
	정확한 유투브를 재생하는데 발생 문제점
	1. 노래 앞에 번호가 붙는 경우
	- 1. 	: 앞에 0을 붙여서 검색
	- 01.	: 보다 정확한 결과 나옴
	
	2. 제목 자체가 틀린 경우 -> 정확도에 따라 url 주소 선정 
		- 띄어쓰기			: 공백제거로 가능 
		- **단어가 틀린경우	: #1  오른 -> 오르는
							  #95 jumbo -> jumpo
							  
	
	3. 앨범명이 노래 제목과 같은 경우(타이틀곡 = 앨범명)
 		- #81   
 
 
 */

				// youtube url 가져오기 (*메소드 형태로 구현)
				String searchSubject = subject.replace(" ", "+");				// 검색어에서 공백을 +로 
				String compare="";
				// 손보자... 
				// 비교할 내용을 Utility class 에 스트링 메소드로 추가 
				//  1. 제목앞에 숫자가 붙는 경우
				int index = subject.indexOf(".");	
				if(index==1) {	
					searchSubject = "0"+ searchSubject.replace(".", "+");
					compare = searchSubject.substring(index+1, searchSubject.length()).replace("+", " ").toLowerCase().trim();
				} else if(index==2) {
					compare = searchSubject.substring(index+1, searchSubject.length()).replace("+", " ").toLowerCase().trim();
				} else {
					compare = subject;
				}
				
				// youtube 링크 가져오기
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
							resultValue = varray[idx].substring(6, 26); // 링크의
																		// 주소부분
																		// 추출
						}
					}
					if (!resultValue.isEmpty()) // 링크주소를 얻고나면 빠져나오기
						break;
				}*/
				String sourceUrl = "https://www.youtube.com" + href; 

				BufferedWriter out = new BufferedWriter(new FileWriter("./lyrics/" + no + ".txt")); // 출력파일 만들기 : 번호.txt
				out.write("제목 : " + subject);
				out.newLine();
				out.write("가수 : " + singer);
				out.newLine();
				out.write(lyrics);
				out.newLine();
				out.write(sourceUrl);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 12. 얼마나 걸렸나 찍어보자
		System.out.println(" End Date : " + getCurrentData());
	}
	
	// 검색어에 앨범이 없는 경우
	public static String searchYoutube(String subject, String singer, String compare) throws ClientProtocolException, IOException {
		String href="";
		String url = "https://www.youtube.com/results?search_query=" + subject + "+" + singer; // youtube 검색 결과 url
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
		
		// comparSource 와 정확도 측정 시작
		Elements atag = doc.select("h3.yt-lockup-title a");
		// 2. 제목이 틀린 경우 정확도 측정
		String compareSource[] = compare.split(" ");
		
		//해쉬맵으로 바꾸자
		/*
		 	알고리즘 
		 	Map1(key, Map2(key2,0또는1))
		 	Map1 : resultMap
		 	Map2 : compareMap
		 	key1 : 비교할 타이틀
		 	key2 : 검색결과 타이틀
		 	
		 */
		HashMap <String, Integer> compareMap= null;
		HashMap <String, HashMap > resultMap = null;
		
		for(int index=0; index < atag.size(); index++) {			// 태그 내에 비교대상 제목을 찾음 
			Element element = atag.get(index);
			String compareTitle = element.attr("title");			// 비교 대상의 제목
			
			for(index=0; index<compareSource.length; index++){		
				String resultTitleWord = compareSource[index];		// 비교 기준 단어
				if( compareTitle.contains(resultTitleWord)){		
				compareMap.put(compareTitle, 1);					// 비교 대상 제목내에 같은 단어를 포함하는 경우
				}else{
				compareMap.put(compareTitle, 0);					// 포함하지 않는 경우
				}
				resultMap.put(resultTitleWord, compareMap);			// 비교기준 단어를 맵에 저장
			}
		}
		

		
		

		
		
		return href;
	}
	
	// 검색어에 앨범이 있는 경우
	public static String searchYoutube(String subject, String singer, String album, String compare) throws ClientProtocolException, IOException {
		String href="";
		String url = "https://www.youtube.com/results?search_query=" + subject + "+" + singer + "+" + album; // youtube 검색 결과 url
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
				System.out.println("요청 타이틀 : " + compare );
				System.out.println("응답 타이틀 : " + element.attr("title"));
				href = element.attr("href");
				break;
			}
		}
		
		return href;
	}
}
