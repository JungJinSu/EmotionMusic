package kr.co.music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.dto.HistoryDTO;

public interface MusicMapper {
		//<select id="list">
		public List<HistoryDTO> list(HashMap hashMap);					// �뷡 ��ü ����Ʈ
		
		public List<HistoryDTO> datelist();					// �뷡 ��ü ����Ʈ
}
