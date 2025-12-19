create table board
(
    bno        int auto_increment comment '게시글 번호(PK)'
        primary key,
    title      varchar(200)                         not null comment '제목',
    content    text                                 null comment '내용',
    writer     varchar(50)                          not null comment '작성자',
    read_count int        default 0                 null comment '조회수',
    is_del     char       default 'N'               null comment '삭제여부(Y/N)',
    reg_date   datetime   default CURRENT_TIMESTAMP null comment '작성일',
    like_count int        default 0                 null,
    is_secret  tinyint(1) default 0                 null,
    password   varchar(100)                         null
);

create table board_read_log
(
    log_no    int auto_increment
        primary key,
    bno       int                                not null,
    reader_id varchar(50)                        not null,
    read_date datetime default CURRENT_TIMESTAMP null,
    constraint unique_read
        unique (bno, reader_id),
    constraint board_read_log_ibfk_1
        foreign key (bno) references board (bno)
            on delete cascade
);

create table member
(
    email      varchar(100)                       not null
        primary key,
    pwd        varchar(100)                       not null,
    nick_name  varchar(50)                        not null,
    reg_date   datetime default CURRENT_TIMESTAMP null,
    last_login datetime                           null
);

create table auth
(
    id    int auto_increment
        primary key,
    email varchar(100) not null,
    auth  varchar(50)  not null,
    constraint fk_auth_member
        foreign key (email) references member (email)
            on delete cascade
);

create table reply
(
    rno        int auto_increment
        primary key,
    bno        int                                not null,
    reply      varchar(1000)                      not null,
    replyer    varchar(50)                        not null,
    replyDate  datetime default CURRENT_TIMESTAMP null,
    updateDate datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint reply_ibfk_1
        foreign key (bno) references board (bno)
            on delete cascade
);

create index bno
    on reply (bno);

create table tbl_file
(
    uuid        varchar(100)                         not null
        primary key,
    upload_path varchar(200)                         not null,
    file_name   varchar(100)                         not null,
    file_type   tinyint(1) default 0                 null,
    bno         int                                  null,
    file_size   bigint                               null,
    reg_date    datetime   default CURRENT_TIMESTAMP null,
    constraint fk_file_board
        foreign key (bno) references board (bno)
            on delete cascade
);

create table tbl_like
(
    bno   int         not null,
    liker varchar(50) not null,
    primary key (bno, liker),
    constraint tbl_like_ibfk_1
        foreign key (bno) references board (bno)
            on delete cascade
);
