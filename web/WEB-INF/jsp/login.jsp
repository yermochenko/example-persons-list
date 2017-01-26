<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML>
    <HEAD>
        <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <TITLE>Вход в систему</TITLE>
    </HEAD>
    <BODY>
        <c:if test="${not empty error}">
            <P style="color: red;">Имя пользовтеля или пароль не опознаны</P>
        </c:if>
        <c:url var="loginUrl" value="/login.html"/>
        <FORM action="${loginUrl}" method="post">
            Имя пользователя:<BR>
            <INPUT type="text" name="login"><BR>
            Пароль:<BR>
            <INPUT type="password" name="password"><BR>
            <BUTTON type="submit">Войти</BUTTON><BR>
            <BUTTON type="reset">Сбросить</BUTTON><BR>
        </FORM>
    </BODY>
</HTML>