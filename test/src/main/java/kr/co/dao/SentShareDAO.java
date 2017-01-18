package kr.co.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.dto.SearchDTO;
import kr.co.dto.SentShareDTO;
import kr.co.main.SentMapper;

@Component
public class SentShareDAO {
	
	@Autowired
	private SqlSession sqlSession;

	public SentShareDAO() {
		
	}
	 
	// ���� ���� �ۼ�
	public int create(SentShareDTO dto){
		SentMapper mapper = sqlSession.getMapper(SentMapper.class);
		int cnt=mapper.create(dto);
		return cnt;
	}
	
	// ���� ���� ���
	public List<SentShareDTO> list(HashMap hashMap){
		SentMapper mapper = sqlSession.getMapper(SentMapper.class);
		List<SentShareDTO> list = mapper.list(hashMap);
		return list;
	}
	
	// ���� ���� ��ȸ
	public SentShareDTO read(int bbsno){
		SentMapper mapper = sqlSession.getMapper(SentMapper.class);
		return mapper.read(bbsno);
	}
	
	// ���� ���� ����
	public int delete(SentShareDTO dto){
		SentMapper mapper = sqlSession.getMapper(SentMapper.class);
		int cnt = mapper.delete(dto);
		return cnt;
	}
	
	// ���� ���� ���� 
	public int update(SentShareDTO dto){
		SentMapper mapper = sqlSession.getMapper(SentMapper.class);
		int cnt = mapper.update(dto);
		return cnt;
	}
	
	// ��ȸ�� ����
	public void readcnt(int bbsno){
		SentMapper mapper = sqlSession.getMapper(SentMapper.class);
		mapper.readcnt(bbsno);
	}
	
	// �� ��ü ����
	public int getArticleCount(SearchDTO searchDTO){
		SentMapper mapper = sqlSession.getMapper(SentMapper.class);
		int cnt = mapper.getArticleCount(searchDTO);
		return cnt;
	}
}
