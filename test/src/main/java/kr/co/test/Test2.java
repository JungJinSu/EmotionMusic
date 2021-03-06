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
		// 1. 가져오기전 시간 찍기
		System.out.println(" Start Date : " + getCurrentData());
		//int noStart = 560001;					// 가사 크롤링 시작 번호
		//int noFinish = 580000;					// 가사 크롤링 끝 번호
		// 디버깅을 위한 Error Report 작성 
		//System.out.println("Error Report Open");
		//int errorNo=1, passedNo=1;																																	// 에러 개수, 패스된 가사 카운트
		//BufferedWriter errorOut = new BufferedWriter(new FileWriter("./errorReport/Exception Lyrics_"+ noStart+"~"+noFinish+"_range.txt"));				// 에러 리포트 문서 
		//BufferedWriter passedOut = new BufferedWriter(new FileWriter("./errorReport/Passed Lyrics_"+ noStart+"~"+noFinish+"_range.txt"));		// 패스된 가사 리포트 문서 
		
		// 누락된 가사를 배열에 저장 
		BufferedReader  reader = new BufferedReader(new FileReader("./errorReport/omissionLyrics2.txt"));
		int count=1;
		String omissionLyrics="";
		String omissionLine="";
		while((omissionLine = reader.readLine()) != null) 
		{
			omissionLyrics += omissionLine+"/";
			System.out.println("누락된 가사 번호 : "+ omissionLine);
			count++;
		}
		String omissionNo[] = omissionLyrics.split("/");
		
		System.out.println("누락된 가사 총 개수 : "+ omissionNo.length);
		System.out.println("===================================크롤링 시작===========================================");
		
		count=1;
		// 누락된 가사만 크롤링 테스트하기
		for (String no : omissionNo) {
			System.out.print("No."+count+" ");
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
				String subject = doc.select("div.col-md-8").get(0).text().replace("'","").replace("<","").replace(">","").replace("`","").replace("*",""); 	// 제목
				String lyrics = doc.select("div.col-md-8").get(1).text(); 	// 가사
				String metadata = doc.select("div.col-md-4").get(0).text();	// 가수
				String singer = metadata.split(" ")[0].toString();
				String album ="";
				 
				if(metadata.contains("집")){								// 앨범 
					album = metadata.substring(metadata.indexOf(":")+2, metadata.indexOf("집")+1);	// 공백제외
					album = album.replace(" ", "+");
				}
			
				if (metadata.contains("Unknown")) {							// 가수가 없는 경우 예외처리
					System.out.println(no + " is pass..");
//					passedOut.write("패스No."+passedNo+" / ");
//					passedOut.write("가사No." + no +" is passed");
//					passedOut.newLine();
//					passedNo++;
					continue;
				}
				// 가사집 크롤링 종료 
	
				// youtube url 가져오기 (*메소드 형태로 구현)
				String searchSubject = subject.replace(" ", "+");				// 검색어에서 공백을 +로 
				String compare=singer+" ";										// 비교기준에 가수 추가
				// 손보자... 
				// 비교할 내용을 Utility class 에 스트링 메소드로 추가 
				
				//  1. 제목앞에 숫자가 붙는 경우
				int index = subject.indexOf(".");	
				if(index==1) {	
					searchSubject = "0"+ searchSubject.replace(".", "+");
					compare += searchSubject.substring(index+1, searchSubject.length()).replace("+", " ").toLowerCase().trim();
				} else if(index==2) {
					compare += searchSubject.substring(index+1, searchSubject.length()).replace("+", " ").toLowerCase().trim();
				} else {
					compare += subject.toLowerCase();
				}
				
				// youtube 링크 가져오기
				System.out.println(no);
				String href = searchYoutube(searchSubject, singer,album, compare);
				
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

				BufferedWriter out = new BufferedWriter(new FileWriter("./omissionLyrics/" + no + ".txt")); // 출력파일 만들기 : 번호.txt
				
				
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
				//System.out.println("예외 발생! 문서에 저장합니다.");			// 디버깅을 위해 예외가일어나는 경우까지 모두 텍스트 파일로 저장
//				errorOut.write("에러No."+errorNo+" / ");
//				errorOut.write("가사No." + no);
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
		// 12. 얼마나 걸렸나 찍어보자
		System.out.println(" End Date : " + getCurrentData());
	}
	
	// 검색어에 앨범이 없는 경우
	public static String searchYoutube(String subject, String singer, String album, String compare) throws ClientProtocolException, IOException {
		String url="";
		if(album.equals("")){
			url = "https://www.youtube.com/results?search_query=" + subject + "+" + singer; 	// youtube 검색 결과 url
		}else{
			url = "https://www.youtube.com/results?search_query=" + subject + "+" + singer + "+" + album;
		}
		Document doc= Crawler.crawl(url);
		Elements atag = doc.select("h3.yt-lockup-title a");
		String playHrefs[] = new String[atag.size()];				// 재생목록 링크
		String playTitle[] = new String[atag.size()];
		
		
		// 2. 제목의 정확도 측정, 가수 까지 비교해야한다.
		// #73 신의 계시(19금 등장) : https://www.youtube.com/watch?v=xJNKkhaaRGM&list=PLte5SNR6rfS3vXAtzBo8yMnaS2FokhNSN
		String compareSource[] = compare.split(" "); 
		int weights[] = new int[atag.size()];								// 각 타이틀의 가중치 결과
		for(int index=0; index < atag.size(); index++) {				// 태그 내에 비교대상 제목을 찾음 
			Element element = atag.get(index);
			String compareTitle = element.attr("title").toLowerCase();	// 비교 대상의 제목, 영어의 경우 소문자로
			playTitle[index] = element.attr("title");							// 재생링크 제목 저장
			playHrefs[index] = element.attr("href");						// 재생링크 목록 저장
			
			weights[index] = 0; 											// 가중치 초기값
			for(String resultTitleWord : compareSource){				// 비교 기준 단어
				if( compareTitle.contains(resultTitleWord)){				// 가중치 합산
					weights[index] += 1;
				}
			}
		}
	
		int maxWeight = weights[0];				// 초기값
		int correct=0;					
		int index=-1;							// 배열 0번째 부터 넣기위해 초기값 -1
		for(int weight :weights){				// 최대값 구하기
			index++;
//			System.out.println(" 가중치 결과 : " + weight);
			if(maxWeight < weight){				// 가중치가 같은 경우 가장 위의 제목을 가리킴	
				maxWeight = weight;
				correct = index;				// 가장 정확한 제목의 주소 인덱스
			}
		}
		//System.out.println(playHrefs[correct]);
		
		return playHrefs[correct];					// 가중치 최대값의 제목 주소를 리턴
	}
	
	public static String getCurrentData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return sdf.format(new Date());
	}
}
