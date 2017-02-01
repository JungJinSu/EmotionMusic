package kr.co.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.dto.DictionaryDTO;
import kr.co.dto.HistoryDTO;
import kr.co.dto.MediaDTO;
import kr.co.media.MediaMapper;

@Component
public class MediaDAO extends AbstractDAO{

	@Autowired
	private SqlSession sqlSession;		// sql �� ���� ��ü
	
		// �뷡 ���� ���
		public int create(MediaDTO dto){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			int cnt=mapper.create(dto);
			return cnt;
		}
		
		
		// �뷡 ��ȸ ����Ʈ
		public List<MediaDTO> list(){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			List<MediaDTO> list = mapper.list();
			return list;
		}
		// ������ �뷡 ��ȸ ����Ʈ
		public ArrayList<MediaDTO> listOfEmotionTpye(HashMap<String, String> emotionType){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			ArrayList<MediaDTO> list = mapper.listOfEmotionTpye(emotionType);
			return list;
		}
		
		// �뷡 top10 ��ȸ ����Ʈ
		public List<MediaDTO> toplist(){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			List<MediaDTO> toplist = mapper.toplist();
			return toplist;
		}
		// �����ܾ� ��ȸ ��� ����Ʈ
		public List<MediaDTO> searchEmotionList(ArrayList<DictionaryDTO> paramDICList){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			List<MediaDTO> searchEmotionList = mapper.searchEmotionList(paramDICList);
			return searchEmotionList;
		}
		
		// RandomPlay ����Ʈ
		public List<MediaDTO> randomList(){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			List<MediaDTO> randomList = mapper.randomList();
			return randomList;
		}
	/*	// emotionMusicList ����Ʈ
		public List<MediaDTO> emotionmusiclist(){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			List<MediaDTO> randomList = mapper.();
			return randomList;
		}*/

		
		
		// �뷡 ��ȸ
		public MediaDTO read(int lyricNo){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			return mapper.read(lyricNo);
		}
		
		// �뷡 ����
		public int delete(MediaDTO dto){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			int cnt = mapper.delete(dto);
			return cnt;
		}
		
		// �뷡 ����
		public int update(MediaDTO dto){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			int cnt = mapper.update(dto);
			return cnt;
		}
		
		// ��ȸ�� ����
		public void readcnt(int lyricNo){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			mapper.readcnt(lyricNo);
		}
		 
		//���Ƚ�� ����
		public void playcnt(int lyricNo){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			mapper.playcnt(lyricNo);
		}

		public void dateinsert(HashMap hashMap){
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			mapper.dateinsert(hashMap);
		}
}
