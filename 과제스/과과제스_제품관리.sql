DROP TABLE PRODUCT;

CREATE TABLE PRODUCT(
    PRODUCT_ID VARCHAR2(50) PRIMARY KEY,
    P_NAME VARCHAR2(20) NOT NULL,
    PRICE NUMBER NOT NULL,
    DESCRIPTION VARCHAR2(50),
    STOCK NUMBER NOT NULL
);

SELECT * FROM product;

INSERT 
  INTO product(
       PRODUCT_ID
     , p_name
     , price
     , description
     , stock
     )
VALUES
     (
       'nb_ss7'
     , '»ï¼º³ëÆ®ºÏ'
     , 1570000
     , '½Ã¸®Áî7'
     , 10
     );
INSERT 
  INTO product(
       PRODUCT_ID
     , p_name
     , price
     , description
     , stock
     )
VALUES
     (
       'nb_ama4'
     , '¸ÆºÏ¿¡¾î'
     , 1200000
     , 'xcode4'
     , 20
     );
INSERT 
  INTO product(
       PRODUCT_ID
     , p_name
     , price
     , description
     , stock
     )
VALUES
     (
       'pc_ibm'
     , 'ibmPC'
     , 750000
     , 'window8'
     , 5
     );

INSERT INTO PRODUCT
     (
        PRODUCT_ID
      , P_NAME
      , PRICE
      , DESCRIPTION
      , STOCK
      )
 VALUES
      (
         ?
       , ?
       , ?
       , ?
       , ?
       );

UPDATE PRODUCT
   SET PRODUCT_ID = 's24'
     , P_NAME = '°µ³á½Ã 24'
     , PRICE =1500000
     , DESCRIPTION ='½ÅÇü ³ª¿Ã°ÅÀÓ'
     , STOCK =1
 WHERE PRODUCT_ID = 's23';

DELETE product
where PRODUCT_ID=?

SELECT *
	   	  FROM PRODUCT
	   	 WHERE PRODUCT_ID = 




