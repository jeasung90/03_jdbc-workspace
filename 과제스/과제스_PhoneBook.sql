DROP TABLE PHONEBOOK;
DROP SEQUENCE SEQ_PBNUM;

CREATE TABLE PHONEBOOK(
    PBNUM NUMBER  CONSTRAINT PBNUMISPRIMARY PRIMARY KEY,
    NAME VARCHAR2(30) CONSTRAINT NAMEISNOTNULL  NOT NULL,
    AGE NUMBER DEFAULT '00',
    ADDRESS VARCHAR2(80)DEFAULT '입력된정보없음',
    PHONE VARCHAR2(11) CONSTRAINT PHONEISNOTNULL NOT NULL
    );
    
    COMMENT ON COLUMN PHONEBOOK.PBNUM IS '순번';
    COMMENT ON COLUMN PHONEBOOK.NAME IS '이름';
    COMMENT ON COLUMN PHONEBOOK.AGE IS '나이';
    COMMENT ON COLUMN PHONEBOOK.ADDRESS IS '주소';
    COMMENT ON COLUMN PHONEBOOK.PHONE IS '전화번호';
    
SELECT * FROM PHONEBOOK;
    
CREATE SEQUENCE SEQ_PBNUM
NOCACHE;

INSERT INTO phonebook VALUES (SEQ_PBNUM.NEXTVAL,'관리자','00','관리자전용','01000000000');
   
SELECT * FROM phonebook; 

----------------------------------------------------------------------------------

INSERT INTO PHONEBOOK VALUES (seq_pbnum.nextval,'박수홍',50,'고양이랑 사는 비싼집','01012345678');

SELECT * FROM PHONEBOOK;

SELECT * FROM PHONEBOOK WHERE NAME = '박수홍';