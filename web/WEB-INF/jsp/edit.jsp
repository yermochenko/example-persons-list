<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${empty person}">
    <jsp:useBean id="person" class="domain.Person"/>
</c:if>

<HTML>
    <HEAD>
        <META http-equiv="Content-Type"
              content="text/html; charset=UTF-8">
        <TITLE>
            <c:choose>
                <c:when test="${not empty person.id}">Редактирование данных о человеке</c:when>
                <c:otherwise>Добавление данных о человеке</c:otherwise>
            </c:choose>
        </TITLE>
        <STYLE type="text/css">
            BODY {
                padding: 0;
            }
            .person, .contacts {
                float: left;
                margin: 0;
                padding: 20px;
            }
            TABLE {
                border-collapse: collapse;
            }
            TH, TD {
                border: 1px solid black;
                padding: 5px 30px 5px 10px;
            }
        </STYLE>
    </HEAD>
    <BODY>
        <c:url var="logoutUrl" value="/logout.html"/>
        <P style="text-align: right">Вы вошли, как пользователь ${user.login}<BR><A href="${logoutUrl}">выйти</A></P>
        <DIV class="person">
            <c:url var="saveUrl" value="/save.html"/>
            <FORM action="${saveUrl}" method="post">
                <c:if test="${not empty person.id}">
                    <INPUT type="hidden" name="id" value="${person.id}">
                </c:if>
                Фамилия:<BR>
                <INPUT type="text" name="last-name" value="${person.lastName}"><BR>
                Имя:<BR>
                <INPUT type="text" name="first-name" value="${person.firstName}"><BR>
                Отчество:<BR>
                <INPUT type="text" name="middle-name" value="${person.middleName}"><BR>
                Рост:<BR>
                <fmt:formatNumber var="height" value="${person.height}" pattern="#.##"/>
                <INPUT type="text" name="height" value="${height}"><BR>
                Вес:<BR>
                <fmt:formatNumber var="weight" value="${person.weight}" pattern="#.##"/>
                <INPUT type="text" name="weight" value="${weight}"><BR>
                <c:if test="${person.citizen}">
                    <c:set var="checked" value="checked"/>
                </c:if>
                <INPUT type="checkbox" name="citizen" ${checked}> Гражданство<BR>
                <BUTTON type="submit">Сохранить</BUTTON><BR>
                <BUTTON type="reset">Сбросить</BUTTON><BR>
            </FORM>
            <c:if test="${not empty person.id}">
                <c:url var="deleteUrl" value="/delete.html"/>
                <FORM action="${deleteUrl}" method="post">
                    <INPUT type="hidden" name="id" value="${person.id}">
                    <BUTTON type="submit">Удалить</BUTTON>
                </FORM>
            </c:if>
            <c:url var="indexUrl" value="/index.html"/>
            <A href="${indexUrl}">Вернуться назад</A>
        </DIV>
        <c:if test="${not empty person.id}">
            <DIV class="contacts">
                <TABLE>
                    <TR>
                        <TH>Тип контакта</TH>
                        <TH>Значение</TH>
                    </TR>
                    <c:forEach var="contact" items="${person.contacts}">
                        <TR>
                            <TD>${contact.type.name}</TD>
                            <c:url var="editUrl" value="/contact/edit.html">
                                <c:param name="id" value="${contact.id}"/>
                            </c:url>
                            <TD><A href="${editUrl}">${contact.value}</A></TD>
                        </TR>
                    </c:forEach>
                </TABLE>
                <c:url var="editUrl" value="/contact/edit.html">
                    <c:param name="person" value="${person.id}"/>
                </c:url>
                <A href="${editUrl}">Добавить контакт</A>
            </DIV>
        </c:if>
    </BODY>
</HTML>