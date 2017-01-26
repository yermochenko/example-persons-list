<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<HTML>
    <HEAD>
        <META http-equiv="Content-Type"
              content="text/html; charset=UTF-8">
        <TITLE>Список людей</TITLE>
        <STYLE>
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
        <TABLE>
            <TR>
                <TH>ID</TH>
                <TH>Фамилия и инициалы</TH>
                <TH>Рост</TH>
                <TH>Вес</TH>
                <TH>Гражданство</TH>
            </TR>
            <c:forEach var="person" items="${persons}">
                <TR>
                    <TD>
                        <fmt:formatNumber value="${person.id}" pattern="00"/>
                    </TD>
                    <c:url var="editUrl" value="/edit.html">
                        <c:param name="id" value="${person.id}"/>
                    </c:url>
                    <c:set var="fName"
                           value="${fn:substring(person.firstName, 0, 1)}"/>
                    <c:set var="mName"
                           value="${fn:substring(person.middleName, 0, 1)}"/>
                    <TD>
                        <A href="${editUrl}">${person.lastName}&nbsp;${fName}.&nbsp;${mName}.</A>
                    </TD>
                    <TD>
                        <fmt:formatNumber value="${person.height}"
                                          pattern="#.##"/>
                    </TD>
                    <TD>
                        <fmt:formatNumber value="${person.weight}"
                                          pattern="#.##"/>
                    </TD>
                    <TD>
                        <c:choose>
                            <c:when test="${person.citizen}">Да</c:when>
                            <c:otherwise>Нет</c:otherwise>
                        </c:choose>
                    </TD>
                </TR>
            </c:forEach>
        </TABLE>
        <c:url var="editUrl" value="/edit.html"/>
        <A href="${editUrl}">Добавить человека</A>
    </BODY>
</HTML>