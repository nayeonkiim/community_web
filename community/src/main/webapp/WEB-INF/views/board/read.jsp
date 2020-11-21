<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ include file="/WEB-INF/views/layout/top.jsp" %>

<body>
	<c:set var="b" value="${board}"/>
	<div class="container">
		<div class="row">
			<div class="col-lg">
				<div class="jumbotron jumbotron-fluid">
				  <div class="container">
				    <h1 class="display-4">readArticle</h1>
				    <p><c:out value="${boardName}" /></p>
				  </div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<form class="form-group" action="/board/${boardName}/modifyArticle/${bNum}">
				 	글쓴이 <c:out value="${b.bId}"/>
				  	<h2 class="mt-3">${b.bTitle}</h2><hr>  
				 	<div class="mb-3">
				 		${b.bContent}<hr>
				 	</div>
				 	<div class="btn-grp">
					 	<a class="btn btn-primary" href="/board/${boardName}/${page}" role="button">목록으로</a>
						<c:if test="${user.username == b.bId}">
							<input type="submit" class="btn btn-primary" value="수정하기">
						</c:if>
						<s:authorize access="hasRole('ROLE_USER')">
							<c:if test="${user.username == b.bId}">
								<input type="button" class="btn btn-primary" onclick="del(${bNum})" value="삭제하기">
							</c:if>
						</s:authorize>
				 	</div>
				 </form>
			</div>
		</div>
		
		<div class="row mt-5">
			<div class="col">
				<form id="commentForm" name="commentForm" method="post">
					
					<s:csrfInput />
					
					<div>
						<span><strong>Comments</strong></span><span id="cCnt"> </span>
					</div>
					<div class="form-group">
						<input type="hidden" name="boardName" value="${boardName}" >
						<input type="hidden" name="bNum" value="${bNum}" >
						<input type="hidden" name="bIndent" value="0" >
						<input type="hidden" name="bOrder" value="0" >
					 	<label for="bId">아이디</label>
						<input type="text" name="bId" class="form-control mb-3" value="${user.username}" readOnly>
						
						<textarea class="form-control mb-3" id="exampleFormControlTextarea1" name="bContent" rows="3"></textarea>
						<a href="#" onClick="fn_comment()" class="btn btn-primary">등록하기</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<div class="container">
		<form id="commentListForm" name="commentListForm" method="post">
			<div id="commentList">
			</div>
		</form>
	</div> 
	
	<script>
		function fn_comment(){
			
			$.ajax({
				type: 'POST',
				url : "/board/reply",
				data : $("#commentForm").serialize(),
				success : function(data){
					if(data == "success"){
						getCommentList();
						$("comment").val("");
					}
				},
				error : function(request,status,error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			       }
			    });
			}
			 
			/**
			 * 초기 페이지 로딩시 댓글 불러오기
			 */
			$(function(){
			    
			    getCommentList();
			    
			});
			 
			/**
			 * 댓글 불러오기(Ajax)
			 */
			function getCommentList(){
			    
			    $.ajax({
			        type:'GET',
			        url : "/board/commentList",
			        dataType : "json",
			        data:$("#commentForm").serialize(),
			        contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
			        success : function(data){
			            
			            var html = "";
			            var cCnt = data.length;
			            
			            if(data.length > 0){
			                
			                for(i=0; i<data.length; i++){
			                    html += "<div>";
			                    html += "<div><table class='table'><h6><strong>"+data[i].bId+"</strong></h6>";
			                    html += data[i].bContent + "<tr><td></td></tr>";
			                    html += "</table></div>";
			                    html += "</div>";
			                }
			                
			            } else {
			                
			                html += "<div>";
			                html += "<div><table class='table'><h6><strong>등록된 댓글이 없습니다.</strong></h6>";
			                html += "</table></div>";
			                html += "</div>";
			                
			            }
			            
			            $("#cCnt").html(" "+cCnt);
			            $("#commentList").html(html);
			            
			        },
			        error:function(request,status,error){
			            
			       }
			        
			    });
			}

			function del(bNum){
				var chk = confirm("정말 삭제하시겠습니까?");
				if(chk) {
					location.href='/board/${boardName}/deleteArticle/'+bNum;
				}
			}
			</script>
	</html>
</body>


