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

 ---------------------------------------------------
create table purpose
( p_index int primary key auto_increment,
  p_subject varchar(50) not null,
  p_content varchar(50) not null,
  p_unit varchar(20) not null,
  p_goal int not null,
  p_date datetime,
  m_index int references member(m_index)
  );

insert into purpose(p_subject,p_content,p_unit,p_goal,p_date,m_index)
     values('독서하기','일주일1권읽기','페이지',1000,now(),12);

select p.*,( select ifnull(sum(pp_value),0) from purpose_process pp where pp.p_index=p.p_index  ) p_now from purpose p where m_index=12;

create table purpose_process
(pp_index int primary key auto_increment,
 pp_value int not null,
 pp_date datetime,
 p_index int references purpose(p_index)
 ); 


*/