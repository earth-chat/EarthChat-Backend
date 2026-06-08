-- ==========================================
-- 0. 시퀀스 정의
-- ==========================================
CREATE SEQUENCE seq_role_info START WITH 1;
CREATE SEQUENCE seq_user_info START WITH 1;
CREATE SEQUENCE seq_email_auth_info START WITH 1;
CREATE SEQUENCE seq_chatroom_info START WITH 1;
CREATE SEQUENCE seq_guest_info START WITH 1;
CREATE SEQUENCE seq_refresh_token_info START WITH 1;
CREATE SEQUENCE seq_chat_history START WITH 1;
CREATE SEQUENCE seq_translate_history START WITH 1;


-- ==========================================
-- 1. 역할 정보
-- ==========================================
CREATE TABLE role_info (
    role_seq INT DEFAULT nextval('seq_role_info'),
    role_name VARCHAR(20) NOT NULL,
    admin_yn CHAR(1) DEFAULT 'N',
    reg_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    mod_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT pk_role_info PRIMARY KEY (role_seq),
    CONSTRAINT uk_role_name UNIQUE (role_name)
);

COMMENT ON TABLE role_info IS '역할정보';
COMMENT ON COLUMN role_info.role_seq IS '역할SEQ';
COMMENT ON COLUMN role_info.role_name IS '역할명';
COMMENT ON COLUMN role_info.admin_yn IS '관리자여부';
COMMENT ON COLUMN role_info.reg_dt IS '등록일시';
COMMENT ON COLUMN role_info.mod_dt IS '수정일시';


-- ==========================================
-- 2. 사용자 정보
-- ==========================================
CREATE TABLE user_info (
    user_seq BIGINT DEFAULT nextval('seq_user_info'),
    nickname VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    pwd VARCHAR(255) NOT NULL,
    use_yn CHAR(1) DEFAULT 'Y',
    translate_code VARCHAR(20) DEFAULT 'EN_US',
    user_status VARCHAR(20) DEFAULT 'COMPLETED',
    reg_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    mod_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT pk_user_info PRIMARY KEY (user_seq),
    CONSTRAINT uk_user_email UNIQUE (email)
);

COMMENT ON TABLE user_info IS '사용자정보';
COMMENT ON COLUMN user_info.user_seq IS '사용자SEQ';
COMMENT ON COLUMN user_info.nickname IS '닉네임';
COMMENT ON COLUMN user_info.email IS '이메일';
COMMENT ON COLUMN user_info.pwd IS '비밀번호';
COMMENT ON COLUMN user_info.use_yn IS '사용여부';
COMMENT ON COLUMN user_info.translate_code IS '기본번역언어';
COMMENT ON COLUMN user_info.user_status IS '계정상태';
COMMENT ON COLUMN user_info.reg_dt IS '등록일시';
COMMENT ON COLUMN user_info.mod_dt IS '수정일시';


-- ==========================================
-- 3. 이메일 인증 정보
-- ==========================================
CREATE TABLE email_auth_info (
    mail_auth_seq BIGINT DEFAULT nextval('seq_email_auth_info'),
    email VARCHAR(150) NOT NULL,
    auth_num CHAR(6) NOT NULL,
    expired_dt TIMESTAMP NOT NULL,
    reg_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT pk_email_auth_info PRIMARY KEY (mail_auth_seq)
);

COMMENT ON TABLE email_auth_info IS '이메일인증정보';
COMMENT ON COLUMN email_auth_info.mail_auth_seq IS '이메일인증SEQ';
COMMENT ON COLUMN email_auth_info.email IS '이메일';
COMMENT ON COLUMN email_auth_info.auth_num IS '인증번호';
COMMENT ON COLUMN email_auth_info.expired_dt IS '만료일시';
COMMENT ON COLUMN email_auth_info.reg_dt IS '등록일시';


-- ==========================================
-- 4. 채팅방 정보
-- ==========================================
CREATE TABLE chatroom_info (
    chatroom_seq BIGINT DEFAULT nextval('seq_chatroom_info'),
    chatroom_name VARCHAR(100) NOT NULL,
    chatroom_description VARCHAR(300),
    owner_seq BIGINT NOT NULL,
    max_participant_num INT DEFAULT 5,
    reg_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    mod_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT pk_chatroom_info PRIMARY KEY (chatroom_seq),
    CONSTRAINT fk_chatroom_owner FOREIGN KEY (owner_seq) REFERENCES user_info(user_seq)
);

COMMENT ON TABLE chatroom_info IS '채팅방정보';
COMMENT ON COLUMN chatroom_info.chatroom_seq IS '채팅방SEQ';
COMMENT ON COLUMN chatroom_info.chatroom_name IS '채팅방명';
COMMENT ON COLUMN chatroom_info.chatroom_description IS '채팅방설명';
COMMENT ON COLUMN chatroom_info.owner_seq IS '방장사용자SEQ';
COMMENT ON COLUMN chatroom_info.max_participant_num IS '최대참가가능인원';
COMMENT ON COLUMN chatroom_info.reg_dt IS '등록일시';
COMMENT ON COLUMN chatroom_info.mod_dt IS '수정일시';


-- ==========================================
-- 5. 채팅방 초대코드 정보
-- ==========================================
CREATE TABLE chatroom_code (
    room_code CHAR(10) NOT NULL,
    chatroom_seq BIGINT NOT NULL,
    use_yn CHAR(1) DEFAULT 'N',
    expired_dt TIMESTAMP NOT NULL,
    reg_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT pk_chatroom_code PRIMARY KEY (room_code),
    CONSTRAINT fk_chatroom_code_chatroom FOREIGN KEY (chatroom_seq) REFERENCES chatroom_info(chatroom_seq)
);

COMMENT ON TABLE chatroom_code IS '채팅방초대코드정보';
COMMENT ON COLUMN chatroom_code.room_code IS '채팅방초대코드';
COMMENT ON COLUMN chatroom_code.chatroom_seq IS '채팅방SEQ';
COMMENT ON COLUMN chatroom_code.use_yn IS '사용여부';
COMMENT ON COLUMN chatroom_code.expired_dt IS '만료일시';
COMMENT ON COLUMN chatroom_code.reg_dt IS '등록일시';


-- ==========================================
-- 6. 게스트 정보
-- ==========================================
CREATE TABLE guest_info (
    guest_seq BIGINT DEFAULT nextval('seq_guest_info'),
    room_code CHAR(10) NOT NULL,
    chatroom_seq BIGINT NOT NULL,
    nickname VARCHAR(100) DEFAULT 'GUEST',
    use_yn CHAR(1) DEFAULT 'Y',
    translate_code VARCHAR(20) DEFAULT 'EN_US',
    reg_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT pk_guest_info PRIMARY KEY (guest_seq),
    CONSTRAINT fk_guest_room_code FOREIGN KEY (room_code) REFERENCES chatroom_code(room_code),
    CONSTRAINT fk_guest_chatroom FOREIGN KEY (chatroom_seq) REFERENCES chatroom_info(chatroom_seq)
);

COMMENT ON TABLE guest_info IS '게스트정보';
COMMENT ON COLUMN guest_info.guest_seq IS '게스트SEQ';
COMMENT ON COLUMN guest_info.room_code IS '입장코드';
COMMENT ON COLUMN guest_info.chatroom_seq IS '채팅방SEQ';
COMMENT ON COLUMN guest_info.nickname IS '닉네임';
COMMENT ON COLUMN guest_info.use_yn IS '사용여부';
COMMENT ON COLUMN guest_info.translate_code IS '기본번역언어';
COMMENT ON COLUMN guest_info.reg_dt IS '등록일시';


-- ==========================================
-- 7. 사용자 역할 매핑
-- ==========================================
CREATE TABLE user_role (
    user_seq BIGINT NOT NULL,
    role_id INT NOT NULL,

    CONSTRAINT pk_user_role PRIMARY KEY (user_seq, role_id),
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_seq) REFERENCES user_info(user_seq),
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES role_info(role_seq)
);

COMMENT ON TABLE user_role IS '사용자역할매핑';
COMMENT ON COLUMN user_role.user_seq IS '사용자SEQ';
COMMENT ON COLUMN user_role.role_id IS '역할SEQ';


-- ==========================================
-- 8. 게스트 역할 매핑
-- ==========================================
CREATE TABLE guest_role (
    guest_seq BIGINT NOT NULL,
    role_id INT NOT NULL,

    CONSTRAINT pk_guest_role PRIMARY KEY (guest_seq, role_id),
    CONSTRAINT fk_guest_role_guest FOREIGN KEY (guest_seq) REFERENCES guest_info(guest_seq),
    CONSTRAINT fk_guest_role_role FOREIGN KEY (role_id) REFERENCES role_info(role_seq)
);

COMMENT ON TABLE guest_role IS '게스트역할매핑';
COMMENT ON COLUMN guest_role.guest_seq IS '게스트SEQ';
COMMENT ON COLUMN guest_role.role_id IS '역할SEQ';


-- ==========================================
-- 9. 리프레시 토큰 정보
-- ==========================================
CREATE TABLE refresh_token_info (
    token_seq BIGINT DEFAULT nextval('seq_refresh_token_info'),
    user_seq BIGINT NOT NULL,
    token_value VARCHAR(500) NOT NULL,
    expired_dt TIMESTAMP NOT NULL,
    use_yn CHAR(1) DEFAULT 'Y',
    reg_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT pk_refresh_token_info PRIMARY KEY (token_seq),
    CONSTRAINT fk_refresh_token_user FOREIGN KEY (user_seq) REFERENCES user_info(user_seq)
);

COMMENT ON TABLE refresh_token_info IS '리프레시토큰정보';
COMMENT ON COLUMN refresh_token_info.token_seq IS '토큰SEQ';
COMMENT ON COLUMN refresh_token_info.user_seq IS '사용자SEQ';
COMMENT ON COLUMN refresh_token_info.token_value IS '리프레시토큰값';
COMMENT ON COLUMN refresh_token_info.expired_dt IS '만료일시';
COMMENT ON COLUMN refresh_token_info.use_yn IS '사용여부';
COMMENT ON COLUMN refresh_token_info.reg_dt IS '등록일시';


-- ==========================================
-- 10. 채팅방 참여자 정보
-- ==========================================
CREATE TABLE chatroom_participant (
    participant_seq BIGINT NOT NULL,
    chatroom_seq BIGINT NOT NULL,
    participant_type VARCHAR(20) DEFAULT 'USER',

    CONSTRAINT pk_chatroom_participant PRIMARY KEY (participant_seq, chatroom_seq),
    CONSTRAINT fk_participant_chatroom FOREIGN KEY (chatroom_seq) REFERENCES chatroom_info(chatroom_seq)
);

COMMENT ON TABLE chatroom_participant IS '채팅방참여자정보';
COMMENT ON COLUMN chatroom_participant.participant_seq IS '참여자SEQ';
COMMENT ON COLUMN chatroom_participant.chatroom_seq IS '채팅방SEQ';
COMMENT ON COLUMN chatroom_participant.participant_type IS '참여자유형(USER/GUEST)';


-- ==========================================
-- 11. 채팅 이력
-- ==========================================
CREATE TABLE chat_history (
    chat_history_seq BIGINT DEFAULT nextval('seq_chat_history'),
    chatroom_seq BIGINT NOT NULL,
    sender_seq BIGINT NOT NULL,
    sender_type VARCHAR(20) DEFAULT 'USER',
    original_message TEXT,
    process_message TEXT,
    reg_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    mod_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT pk_chat_history PRIMARY KEY (chat_history_seq),
    CONSTRAINT fk_chat_history_chatroom FOREIGN KEY (chatroom_seq) REFERENCES chatroom_info(chatroom_seq)
);

COMMENT ON TABLE chat_history IS '채팅이력';
COMMENT ON COLUMN chat_history.chat_history_seq IS '채팅이력SEQ';
COMMENT ON COLUMN chat_history.chatroom_seq IS '채팅방SEQ';
COMMENT ON COLUMN chat_history.sender_seq IS '송신자SEQ';
COMMENT ON COLUMN chat_history.sender_type IS '송신자유형(USER/GUEST)';
COMMENT ON COLUMN chat_history.original_message IS '원본메시지';
COMMENT ON COLUMN chat_history.process_message IS '정제메시지';
COMMENT ON COLUMN chat_history.reg_dt IS '등록일시';
COMMENT ON COLUMN chat_history.mod_dt IS '수정일시';


-- ==========================================
-- 12. 번역 이력
-- ==========================================
CREATE TABLE translate_history (
    translate_history_seq BIGINT DEFAULT nextval('seq_translate_history'),
    chat_history_seq BIGINT NOT NULL,
    translate_code VARCHAR(20) NOT NULL,
    message TEXT,
    reg_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT pk_translate_history PRIMARY KEY (translate_history_seq),
    CONSTRAINT fk_translate_history_chat FOREIGN KEY (chat_history_seq) REFERENCES chat_history(chat_history_seq)
);

COMMENT ON TABLE translate_history IS '번역 이력';
COMMENT ON COLUMN translate_history.translate_history_seq IS '번역이력SEQ';
COMMENT ON COLUMN translate_history.chat_history_seq IS '채팅이력SEQ';
COMMENT ON COLUMN translate_history.translate_code IS '번역코드';
COMMENT ON COLUMN translate_history.message IS '번역메시지';
COMMENT ON COLUMN translate_history.reg_dt IS '등록일시';