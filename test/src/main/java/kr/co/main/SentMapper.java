package kr.co.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.main.SentShareDTO;

// ���� ���� ����(Mapper).
public interface SentMapper {
	// �������̽�, �߻�޼ҵ�θ� �����ϸ� MyBatis3�� ȣ���ϱ� ���ϵ��� ����
	// ���ۿ� ���� �޼ҵ���� MyBatis�� XML ���Ͼ��� id�� �����ؾ� �Ѵ� 
	// sent.xml
	
	//<select id="list">
	public List<SentShareDTO> list(HashMap hashMap);  // ���� ���� ��� 
		
	//<insert id="create">
	public int create(SentShareDTO dto);
		
	//<select id="read" parameterType="int" resultType="SentShareDTO">
	public SentShareDTO read(int bbsno);
		
	//<delete id="delete" parameterType="int" resultType="SentShareDTO">
	public int delete(SentShareDTO dto);
		
	public int update(SentShareDTO dto);
	
	public void readcnt(int bbsno);
	
	public int getArticleCount(SearchDTO searchDTO); // �� ��ü ����
}
