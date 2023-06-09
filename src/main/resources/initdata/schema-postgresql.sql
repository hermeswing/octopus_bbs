DROP TABLE IF EXISTS T_USER_M;

CREATE TABLE IF NOT EXISTS T_USER_M (
    ID SERIAL NOT NULL,
    USER_ID VARCHAR(20) NOT NULL,
    USER_NM VARCHAR(20) NOT NULL,
    CRT_ID VARCHAR(20),
    CRT_DT TIMESTAMP,
    MDF_ID VARCHAR(20),
    MDF_DT TIMESTAMP,
    CONSTRAINT PK_T_USER_M PRIMARY KEY (USER_ID)
);

DROP TABLE IF EXISTS T_BOARD_M;

CREATE TABLE IF NOT EXISTS T_BOARD_M (
    ID SERIAL NOT NULL,
    TITLE VARCHAR(200) NOT NULL,
    CONTENTS TEXT NOT NULL,
    READ_CNT INTEGER NOT NULL DEFAULT 0 ,
    NOTICE_YN VARCHAR(1) DEFAULT 'N',
    CRT_ID VARCHAR(20),
    CRT_DT TIMESTAMP,
    MDF_ID VARCHAR(20),
    MDF_DT TIMESTAMP,
    CONSTRAINT PK_T_BOARD_M PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS T_COMMENT_M;

CREATE TABLE IF NOT EXISTS T_COMMENT_M (
    ID SERIAL NOT NULL,
    POST_ID INTEGER NOT NULL,
    CONTENTS TEXT NOT NULL,
    CRT_ID VARCHAR(20),
    CRT_DT TIMESTAMP,
    MDF_ID VARCHAR(20),
    MDF_DT TIMESTAMP,
    CONSTRAINT PK_T_COMMENT_M PRIMARY KEY (ID, POST_ID)
);