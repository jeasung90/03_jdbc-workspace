DROP TABLE MEMBER;
DROP SEQUENCE SEQ_USERNO;

CREATE TABLE MEMBER(
    USERNO NUMBER PRIMARY KEY,
    USERID VARCHAR2(15) NOT NULL UNIQUE,
    USERPWD VARCHAR2(15) NOT NULL,
    USERNAEM VARCHAR2(30) NOT NULL,
    GENDER CHAR(1) CHECK(GENDER IN ('M','F')),
    AGE NUMBER,
    EMAIL VARCHAR2(40),
    PHONE CHAR(11),
    ADDRESS VARCHAR2(100),
    HOBBY VARCHAR2(50),
    ENROLLDATE DATE DEFAULT SYSDATE NOT NULL
);

SELECT * FROM MEMBER;

CREATE SEQUENCE SEQ_USERNO
NOCACHE;

INSERT INTO MEMBER
VALUES (SEQ_USERNO.NEXTVAL, 'admin','1234','관리자','M',45,'admin@iei.or.kr','01012345555','서울',null,'2021-07-27');
INSERT INTO MEMBER
VALUES (SEQ_USERNO.NEXTVAL, 'user01','pass01','홍길순',null,45,'user01@iei.or.kr','01022225555','동해번쩍 서해번쩍','등산, 의적질','2007-07-27');


CREATE TABLE TEST(
    TNO NUMBER,
    TNAME VARCHAR2(20),
    TDATE DATE
);

SELECT * FROM TEST;
























































