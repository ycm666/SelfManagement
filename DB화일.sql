/*
CREATE table member 
(  m_index int primary key auto_increment,
  m_id varchar(30) unique not null,
  m_pwd varchar(20) not null,
  m_name varchar(30) not null,
  m_phone_num varchar(100) not null,
  m_gender varchar(10) not null,
  m_age varchar(10) not null
);

--Sample
INSERT INTO member(m_id,m_pwd,m_name,m_phone_num,m_gender,m_age) 
       VALUES('hong','1111','홍길동','010-111-2222','남자',20);
INSERT INTO member(m_id,m_pwd,m_name,m_phone_num,m_gender,m_age) 
       VALUES('test','1111','테스트','010-222-2222','남자',30);


create table category
(  c_index int primary key auto_increment,
  c_name varchar(30) not null
);

--Sample
insert into category(c_name) values('운동');
insert into category(c_name) values('다이어트');
insert into category(c_name) values('프로젝트');
insert into category(c_name) values('금연');
insert into category(c_name) values('자격증');



create table group_list
(  g_index int primary key auto_increment,
  g_subject varchar(50) not null,
  m_index int references member(m_index),
  c_index int references category(c_index)
);

create table daily
( d_index int primary key auto_increment,
  d_subject varchar(50) not null,
  d_content varchar(1000) not null,
  d_image varchar(100),
  d_date datetime,
  g_index int references group_list(g_index)
);



*/