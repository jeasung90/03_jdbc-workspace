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











