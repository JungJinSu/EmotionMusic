package kr.co.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.dto.mediaDTO;
import kr.co.media.MediaMapper;

@Component
public class MediaDAO extends AbstractDAO{

	@Autowired
	private SqlSession sqlSession;		// sql �� ���� ��ü
	
		// �뷡 ���� ���
		public int create(mediaDTO dto){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			int cnt=mapper.create(dto);
			return cnt;
		}
		
		// �뷡 ��ȸ ����Ʈ
		public List<mediaDTO> list(HashMap hashMap){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			List<mediaDTO> list = mapper.list(hashMap);
			return list;
		}
		
		// �뷡 ��ȸ
		public mediaDTO read(int lyricNo){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			return mapper.read(lyricNo);
		}
		
		// �뷡 ����
		public int delete(mediaDTO dto){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			int cnt = mapper.delete(dto);
			return cnt;
		}
		
		// �뷡 ����
		public int update(mediaDTO dto){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			int cnt = mapper.update(dto);
			return cnt;
		}
		
		// ��ȸ�� ����
		public void readcnt(int lyricNo){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			mapper.readcnt(lyricNo);
		}
		
		
	
}
