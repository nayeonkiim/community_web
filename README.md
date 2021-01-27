# community_web

 블로그 형식의 커뮤니티 웹으로 회원가입, 로그인, 게시판을 포함하고 있다.
 
 DB - MySql <br/>
 Spring Framework <br/>
 Tomcat Server <br/>
 Bootstrap

# 기능들
 1. User (회원가입, 로그인)
  - 로그인 화면 요청 : /loginView 
  - 로그인 인증 : /loginAskOk 
   => Spring Security
  - 회원 가입 화면 요청 : /registerUser
  - 아이디 중복 체크 : /IdDuplicate
  - 회원 가입 요청(DB에 저장) : /registerAsk
 
 2. Board (게시판) 
  - 게시판 목록(첫 화면) : /board/{boardName}/{page}
  - 글(댓글) 쓰기 화면 요청 : /board/{boardName}/{bNum}/0
  - 글(댓글) 쓴 후 DB반영 : /board/{boardName}/{bNum}/askWriteArticle
  - 글읽기 : /board/{boardName}/readArticle/{bNum}/{page}
  - 글 수정 화면 요청: /board/{boardName}/modifyArticle/{bNum}
  - 글 수정 DB 반영 : /{boardName}/askModify/{bNum}
  - 답변 등록(ajax) : /board/reply
  - 답변 조회(ajax) : /board/commentList
  - 글 삭제 : /board/{boardName}/deleteArticle/{bNum}


## Database

```sql
create user 'springprj'@'localhost' identified by 'springprj';
create database springprjs;

grant all privileges on springprjs.* to 'springprj'@'localhost';

create table user_info(
userId varchar(100) not null primary key,
userName varchar(50) not null,
userPw char(60) not null,
phoneNum char(11) not null,
email varchar(255) not null
);

create table user_auth(
userId varchar(100) not null primary key,
authority varchar(50) not null,
foreign key (userId) references user_info(userId)
);

create table board_main(
  bNum int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  bParent int NOT NULL,
  bOrder int NOT NULL default 0,
  bIndent int NOT NULL default 0,
  bTitle varchar(50) NOT NULL,
  bContent varchar(20000) NOT NULL,
  bId varchar(20) NOT NULL,
  bRegdate datetime default current_timestamp,
  bFrequency int default 0
);

select * from user_auth;
update user_auth set authority='ROLE_ADMIN' where userId='admin'; 
```
