<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<HTML>
    <HEAD>
        <META http-equiv="Content-Type"
              content="text/html; charset=UTF-8">
        <TITLE>Список типов контактов</TITLE>
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
                <TH>Имя типа</TH>
            </TR>
            <c:forEach var="type" items="${types}">
                <TR>
                    <TD>
                        <fmt:formatNumber value="${type.id}" pattern="00"/>
                    </TD>
                    <c:url var="editUrl" value="/type/edit.html">
                        <c:param name="id" value="${type.id}"/>
                    </c:url>
                    <TD>
                        <A href="${editUrl}">${type.name}</A>
                    </TD>
                </TR>
            </c:forEach>
        </TABLE>
        <c:url var="editUrl" value="/type/edit.html"/>
        <A href="${editUrl}">Добавить тип</A><BR>
        <c:url var="indexUrl" value="/index.html"/>
        <A href="${indexUrl}">На главную</A>
    </BODY>
</HTML>