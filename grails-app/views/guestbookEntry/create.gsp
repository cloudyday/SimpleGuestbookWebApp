<%@ page import="simpleguestbook.GuestbookEntry" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'guestbookEntry.label', default: 'GuestbookEntry')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${session.user == null}">
        <div class="message">You are not logged in. Log in <g:link controller="user"
                                                                   action="login">here</g:link>, or post as unregistered user below.</div>
    </g:if>
    <g:else>
        <div class="message">You are posting as ${session.user}!</div>
    </g:else>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${guestbookEntryInstance}">
        <div class="errors">
            <g:renderErrors bean="${guestbookEntryInstance}" as="list"/>
        </div>
    </g:hasErrors>

    <g:form action="create">
        <div class="dialog">
            <table>
                <tbody>

                <g:if test="${session.user == null}">
                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="text">Your Name:</label>
                        </td>
                        <td valign="top">
                            <g:textField name="anonName" value="${guestbookEntryInstance?.anonName}"/>
                        </td>
                    </tr>
                </g:if>


                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="text"><g:message code="guestbookEntry.text.label" default="Text"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: guestbookEntryInstance, field: 'text', 'errors')}">
                        <g:textArea name="text" rows="5" cols="40" value="${guestbookEntryInstance?.text}"/>
                    </td>
                </tr>

                <g:if test="${session.user == null}">
                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="anonEmail"><g:message code="guestbookEntry.anonEmail.label"
                                                              default="User Email"/></label>
                        </td>
                        <td valign="top"
                            class="value ${hasErrors(bean: guestbookEntryInstance, field: 'anonEmail', 'errors')}">
                            <g:textField name="anonEmail" value="${guestbookEntryInstance?.anonEmail}"/>
                        </td>
                    </tr>
                </g:if>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:submitButton name="create" class="save"
                                                 value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
