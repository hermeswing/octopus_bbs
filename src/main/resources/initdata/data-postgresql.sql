COMMENT on column T_BOARD_M.ID is 'ID';
COMMENT on column T_BOARD_M.TITLE is ' 제목 ';
COMMENT on column T_BOARD_M.CONTENTS is ' 내용 ';
COMMENT on column T_BOARD_M.READ_CNT is ' 조회수 ';
COMMENT on column T_BOARD_M.CRT_ID is '생성자ID';
COMMENT on column T_BOARD_M.CRT_DT is '생성일시';
COMMENT on column T_BOARD_M.MDF_ID is '수정자ID';
COMMENT on column T_BOARD_M.MDF_DT is '수정일시';

INSERT INTO T_BOARD_M(TITLE, CONTENTS, READ_CNT, CRT_ID, CRT_DT, MDF_ID, MDF_DT) VALUES ('테스트', '한글 테스트', 0, 'admin', NOW(), 'admin', NOW());
