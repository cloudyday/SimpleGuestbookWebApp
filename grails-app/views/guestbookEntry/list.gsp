<%@ page import="simpleguestbook.EntryToDisplay" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'style.css')}"/>
    <title>Guestbook</title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="create" action="create">Write something</g:link></span>
</div>

<div class="body">
    <h1>Guestbook</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">

        <g:each in="${entries}" status="i" var="entry">
            <div class="gbEntry">
                <div class="entryHead">
                    <span class="userContainer">Name: <span class="user"><g:if test="${entry.email != null}"><a href="mailto:${entry.email}">${entry.name}</a></g:if><g:else>${entry.name}</g:else></span></span>
                    <span class="dateContainer">Date: <span class="date"><g:formatDate date="${entry.date}" type="datetime" style="MEDIUM"/></span></span>
                </div>

                <div class="entryBody">${entry.text}</div>
            </div>
        </g:each>

    </div>
</div>
</body>
</html>
