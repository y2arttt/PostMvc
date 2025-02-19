<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<!-- 페이지 내용 -->
<div class="row">
    <div class="col-12">
        <!-- 게시글 목록 -->
        <div class="mb-3">
            <a href="/posts/create/" class="btn btn-primary">게시글 등록</a>
        </div>
        <table class="table table-striped table-hover table-bordered"></table>
        <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>생성일시</th>
            <th>수정일시</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${postsVoList}" var="postsVo">
            <tr>
                <td>${postsVo.seq}</td>
                <td><a href="/posts/${postsVo.seq}/">${postsVo.title}</a></td>
                <td>${postsVo.username}</td>
                <td>${postsVo.createdAt.substring(0, 16)}</td>
                <td>${postsVo.updatedAt.substring(0, 16)}</td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
        <!--// 게시글 목록 -->
    </div>
</div>
<!--// 페이지 내용 -->

<%@ include file="../base/script.jsp" %>

<!-- script -->
<script>
    /* 자바스크립트 */
</script>
<!--// script -->

<%@ include file="../base/bottom.jsp" %>
