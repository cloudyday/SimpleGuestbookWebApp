<%--
  Created by IntelliJ IDEA.
  User: Olli
  Date: 22.10.11
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="simpleguestbook.User" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title>Login</title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1>Login</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${userInstance}">
        <div class="errors">
            <g:renderErrors bean="${userInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="login">

        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="user.name.label" default="Name"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'name', 'errors')}">
                        <g:textField name="name" maxlength="15" value="${userInstance?.name}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="password"><g:message code="user.password.label" default="Password"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'password', 'errors')}">
                        <g:passwordField name="password" value=""/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:submitButton name="login" class="save"
                                                 value="${message(code: 'default.button.login.label', default: 'Login')}"/></span>
        </div>
    </g:form>
</div>
</body>
</html>