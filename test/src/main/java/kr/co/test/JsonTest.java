package kr.co.test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import kr.co.utility.DBOpen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

public class JsonTest {

	public static void main(String[] args) {
		DBOpen dbopen = new DBOpen();
		
		/*
		// ���� �ϼ��� JSONObject ����(��ü)
		JSONObject jsonObject = new JSONObject();
		// person�� JSON������ ���� Array ����
		JSONArray top10Array = new JSONArray();
		// person�� �Ѹ� ������ �� JSONObject ����
		JSONArray state = new JSONArray();
		JSONObject freq = new JSONObject();
		JSONObject emotion = new JSONObject();
		String top = "ž1";
		*/
		
		Connection con=null;
		PreparedStatement pstmt = null;
		StringBuffer sql = null;
		ResultSet rs = null;
		con = dbopen.getConnetion();
		sql=new StringBuffer();
		sql.append(" select * from bardciel.media order by playCnt desc limit 0,10 ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				System.out.println(rs.toString());
			}
			System.out.println("@@@@@@@@@@@@@@@@@@");
				
		} catch (SQLException e) {
		}
		/*
		emotion.put("happy", 7);
		emotion.put("sad", 6);
		emotion.put("rage", 5);
		emotion.put("disgust", 4);
		emotion.put("interest", 3);
		emotion.put("pain", 2);
		emotion.put("fear", 1);
		
		
		freq.put("freq", emotion);
		state.add(top);
		state.add(freq);
		
		
		top10Array.add(state);
		*/
/*	System.out.println(top10Array);*/
		/*System.out.println(	state.get(0) );
		String str	 = state.get(1).toString();
		str=str.replace("\"",	"");
		str=str.substring(1, str.length()-1);
		System.out.println(	str );*/
		
		
		// ���� �Է�
		
		/*freq.put("happy", "10");
		freq.put("sad", "10");
		freq.put("rage", "12");
		freq.put("disgust", "11");
		// Array�� �Է�
		personArray.add(freq);
*/
		
		
		// ��ü�� JSONObject�� ����̶� name���� JSON�� ������ ������ Array�� value�� �Է�
		//jsonObject.put("State", personArray);

		//JSONArray bookArray = new JSONArray();

		/*JSONObject bookInfo = new JSONObject();
		bookInfo.put("name", "����� �������� ��°�?");
		bookInfo.put("writer", "�罺����");
		bookInfo.put("price", "100");
		bookInfo.put("genre", "�Ҽ�");
		bookInfo.put("publisher", "�罺���� ���ǻ�");
		bookArray.add(bookInfo);

		bookInfo = new JSONObject();
		bookInfo.put("name", "ȫ�浿��");
		bookInfo.put("writer", "���");
		bookInfo.put("price", "300");
		bookInfo.put("genre", "�Ҽ�");
		bookInfo.put("publisher", "��� ���ǻ�");
		bookArray.add(bookInfo);

		bookInfo = new JSONObject();
		bookInfo.put("name", "���������");
		bookInfo.put("writer", "���丣 ����");
		bookInfo.put("price", "900");
		bookInfo.put("genre", "�Ҽ�");
		bookInfo.put("publisher", "���丣 ���� ���ǻ�");
		bookArray.add(bookInfo);*/

		//jsonObject.put("books", bookArray);

		// JSONObject�� String ��ü�� �Ҵ�
	/*	String jsonInfo = jsonObject.toJSONString();

		System.out.print(jsonInfo);*/

	}

}
