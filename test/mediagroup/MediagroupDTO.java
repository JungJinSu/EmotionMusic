package kr.co.mymelon.mediagroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class MediagroupDTO {

	@Autowired
	private DBOpen dbopen;
	@Autowired
	private DBClose dbclose;
	
	  //@Autowired  : @Component�� ������ ��ü�� �����Ҷ�
	
	public MediagroupDTO() {
		System.out.println("---MediagroupDTO��ü ������");
	}

}
