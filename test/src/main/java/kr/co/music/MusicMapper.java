package kr.co.music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.dto.HistoryDTO;
import kr.co.dto.HistorySearchDTO;

public interface MusicMapper {
		//<select id="list">
		public List<HistoryDTO> list(HashMap hashMap);					// �뷡 ��ü ����Ʈ
		
		public List<HistorySearchDTO> datelist(HashMap hashMap);					// �뷡 ��ü ����Ʈ
}
