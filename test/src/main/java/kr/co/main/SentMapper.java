package kr.co.main;

import java.util.ArrayList;

import kr.co.main.SentShareDTO;

public interface SentMapper {
	// �������̽�, �߻�޼ҵ�θ� �����ϸ� MyBatis3�� ȣ���ϱ� ���ϵ��� ����
	// ���ۿ� ���� �޼ҵ���� MyBatis�� XML ���Ͼ��� id�� �����ؾ� �Ѵ� 
	// sent.xml
		
	//<select id="list">
	public ArrayList<SentShareDTO> list();
		
	//<insert id="create">
	public int create(SentShareDTO dto);
		
	//<select id="read" parameterType="int" resultType="SentShareDTO">
	public SentShareDTO read(int bbsno);
		
	//<delete id="delete" parameterType="int" resultType="SentShareDTO">
	public int delete(SentShareDTO dto);
		
	public int update(SentShareDTO dto);
	
	public void readcnt(int bbsno);
}
