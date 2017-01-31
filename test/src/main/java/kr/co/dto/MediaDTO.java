package kr.co.dto;

public class MediaDTO {
	
	private int lyricsNo;				// ���� ��ȣ 
	private String title;					// ���� ����
	private String singer;				// ����
	private String url;					// ������ �ּ�
	private String bpm;					// ������
	private int playCnt;					// �÷��� Ƚ��
	private int recCnt;					// ��õ Ƚ��
	private String lyrics;				// ����
	private int happy;					// ���
	private int sad;						// ����
	private int disgust;					// ����
	private int interest;					// ���
	private int pain;						// ����
	private int fear;						// ������
	private int rage; 					// �г� 
	private int RNUM;				// ��ȸ ��� ����
	private String emotion;		// ��ǥ ����
	
	public MediaDTO() {
	}
	public int getLyricsNo() {
		return lyricsNo;
	}
	public void setLyricsNo(int lyricsNo) {
		this.lyricsNo = lyricsNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBpm() {
		return bpm;
	}
	public void setBpm(String bpm) {
		this.bpm = bpm;
	}
	public int getPlayCnt() {
		return playCnt;
	}
	public void setPlayCnt(int playCnt) {
		this.playCnt = playCnt;
	}
	public int getRecCnt() {
		return recCnt;
	}
	public void setRecCnt(int recCnt) {
		this.recCnt = recCnt;
	}
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	public int getHappy() {
		return happy;
	}
	public void setHappy(int happy) {
		this.happy = happy;
	}
	public int getSad() {
		return sad;
	}
	public void setSad(int sad) {
		this.sad = sad;
	}
	public int getDisgust() {
		return disgust;
	}
	public void setDisgust(int disgust) {
		this.disgust = disgust;
	}
	public int getInterest() {
		return interest;
	}
	public void setInterest(int interest) {
		this.interest = interest;
	}
	public int getPain() {
		return pain;
	}
	public void setPain(int pain) {
		this.pain = pain;
	}
	public int getFear() {
		return fear;
	}
	public void setFear(int fear) {
		this.fear = fear;
	}
	public int getRage() {
		return rage;
	}
	public void setRage(int rage) {
		this.rage = rage;
	}
	public int getRNUM() {
		return RNUM;
	}
	public void setRNUM(int RNUM) {
		this.RNUM = RNUM;
	}
	
	public String getEmotion() {
		return emotion;
	}
	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}
	@Override
	public String toString() {
		return "MediaDTO [lyricsNo=" + lyricsNo + ", title=" + title + ", singer=" + singer + ", url=" + url + ", bpm="
				+ bpm + ", playCnt=" + playCnt + ", recCnt=" + recCnt + ", lyrics=" + lyrics + ", happy=" + happy
				+ ", sad=" + sad + ", disgust=" + disgust + ", interest=" + interest + ", pain=" + pain + ", fear="
				+ fear + ", rage=" + rage + ", RNUM=" + RNUM + ", emotion=" + emotion + "]";
	}

	
}
