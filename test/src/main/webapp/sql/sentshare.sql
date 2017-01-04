create table sentshare(
bbsno NUMBER(7) not null -- �۹�ȣ
,wname VARCHAR2(30) not null -- �ۼ���
,subject VARCHAR2(1000) not null -- ����
,content VARCHAR2(4000) not null -- �۳���
,readcnt NUMBER(12) DEFAULT 0 not null -- ��ȸ�� 
,regdt date default SYSDATE -- �����
,primary key(bbsno)
);

create sequence bbsno_seq

drop table sentshare purge;

select * from sentshare;

SELECT bbsno, subject, wname, readcnt, regdt FROM sentshare ORDER BY bbsno DESC