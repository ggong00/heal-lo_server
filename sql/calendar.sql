CREATE TABLE Calendar (
  CDNO NUMBER(8) PRIMARY KEY,
  MEMNO NUMBER(8)  REFERENCES Member(memno),
  CDCONTENT CLOB,
  CDCDATE DATE,
  CDUDATE DATE
);
