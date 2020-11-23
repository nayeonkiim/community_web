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
