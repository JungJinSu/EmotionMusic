package kr.co.media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.dto.DictionaryDTO;
import kr.co.dto.MediaDTO;

public interface MediaMapper {
		//<select id="list">
		public List<MediaDTO> list();							// �뷡 ��ü ����Ʈ
		public List<MediaDTO> toplist();						// top10 ����Ʈ
		public List<MediaDTO> searchEmotionList(List<Object> paramDICList);			// �����˻� ��� ����Ʈ
		
		//<insert id="create">
		public int create(MediaDTO dto);
		
		//<select id="read" parameterType="int" resultType="SentShareDTO">
		public MediaDTO read(int bbsno);
			
		//<delete id="delete" parameterType="int" resultType="SentShareDTO">
		public int delete(MediaDTO dto);
			
		public int update(MediaDTO dto);
		
		public void readcnt(int bbsno);
}
