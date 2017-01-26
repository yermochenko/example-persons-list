<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty type}">
    <jsp:useBean id="type" class="domain.Type"/>
</c:if>

<HTML>
    <HEAD>
        <META http-equiv="Content-Type"
              content="text/html; charset=UTF-8">
        <TITLE>
            <c:choose>
                <c:when test="${not empty type.id}">Редактирование типа контактов</c:when>
                <c:otherwise>Добавление типа контактов</c:otherwise>
            </c:choose>
        </TITLE>
    </HEAD>
    <BODY>
        <c:url var="logoutUrl" value="/logout.html"/>
        <P style="text-align: right">Вы вошли, как пользователь ${user.login}<BR><A href="${logoutUrl}">выйти</A></P>
        <c:url var="saveUrl" value="/type/save.html"/>
        <FORM action="${saveUrl}" method="post">
            <c:if test="${not empty type.id}">
                <INPUT type="hidden" name="id" value="${type.id}">
            </c:if>
            Имя типа контактов:<BR>
            <INPUT type="text" name="name" value="${type.name}"><BR>
            <BUTTON type="submit">Сохранить</BUTTON><BR>
            <BUTTON type="reset">Сбросить</BUTTON><BR>
        </FORM>
        <c:if test="${not empty type.id}">
            <c:url var="deleteUrl" value="/type/delete.html"/>
            <FORM action="${deleteUrl}" method="post">
                <INPUT type="hidden" name="id" value="${type.id}">
                <BUTTON type="submit">Удалить</BUTTON>
            </FORM>
        </c:if>
        <c:url var="indexUrl" value="/type/index.html"/>
        <A href="${indexUrl}">Вернуться назад</A>
    </BODY>
</HTML>