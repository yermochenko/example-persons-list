<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${empty contact}">
    <jsp:useBean id="contact" class="domain.Contact"/>
</c:if>

<HTML>
    <HEAD>
        <META http-equiv="Content-Type"
              content="text/html; charset=UTF-8">
        <TITLE>
            <c:choose>
                <c:when test="${not empty contact.id}">Редактирование контакта</c:when>
                <c:otherwise>Добавление контакта</c:otherwise>
            </c:choose>
        </TITLE>
    </HEAD>
    <BODY>
        <c:url var="logoutUrl" value="/logout.html"/>
        <P style="text-align: right">Вы вошли, как пользователь ${user.login}<BR><A href="${logoutUrl}">выйти</A></P>
        <c:url var="saveUrl" value="/contact/save.html"/>
        <FORM action="${saveUrl}" method="post">
            <c:choose>
                <c:when test="${not empty contact.id}">
                    <c:set var="p" value="${contact.person}"/>
                    <INPUT type="hidden" name="id" value="${contact.id}">
                </c:when>
                <c:when test="${not empty person}">
                    <c:set var="p" value="${person}"/>
                </c:when>
            </c:choose>
            <c:if test="${not empty p}">
                <INPUT type="hidden" name="person" value="${p.id}">
                <c:set var="fName" value="${fn:substring(p.firstName, 0, 1)}"/>
                <c:set var="mName" value="${fn:substring(p.middleName, 0, 1)}"/>
                ${p.lastName}&nbsp;${fName}.&nbsp;${mName}.<BR>
                Тип контакта:<BR>
                <SELECT name="type">
                    <c:forEach var="type" items="${types}">
                        <c:choose>
                            <c:when test="${not empty contact.type and contact.type.id == type.id}">
                                <c:set var="selected" value="selected"/>
                            </c:when>
                            <c:otherwise>
                                <c:remove var="selected"/>
                            </c:otherwise>
                        </c:choose>
                        <OPTION value="${type.id}" ${selected}>${type.name}</OPTION>
                    </c:forEach>
                </SELECT>
                <c:url var="indexUrl" value="/type/index.html"/>
                <A href="${indexUrl}">редактировать</A><BR>
                Значение контакта:<BR>
                <INPUT type="text" name="value" value="${contact.value}"><BR>
                <BUTTON type="submit">Сохранить</BUTTON><BR>
                <BUTTON type="reset">Сбросить</BUTTON><BR>
            </c:if>
        </FORM>
        <c:if test="${not empty contact.id}">
            <c:url var="deleteUrl" value="/contact/delete.html"/>
            <FORM action="${deleteUrl}" method="post">
                <INPUT type="hidden" name="id" value="${contact.id}">
                <BUTTON type="submit">Удалить</BUTTON>
            </FORM>
        </c:if>
        <c:url var="editUrl" value="/edit.html">
            <c:if test="${not empty p}">
                <c:param name="id" value="${p.id}"/>
            </c:if>
        </c:url>
        <A href="${editUrl}">Вернуться назад</A>
    </BODY>
</HTML>