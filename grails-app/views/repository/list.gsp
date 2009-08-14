
<%@ page import="net.jetztgrad.plugrepo.Repository" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Repository List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Repository</g:link></span>
        </div>
        <div class="body">
            <h1>Repository List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="name" title="Name" />
                        
                   	        <g:sortableColumn property="repositoryURL" title="Repository URL" />
                        
                   	        <g:sortableColumn property="description" title="Description" />
                        
                   	        <g:sortableColumn property="userName" title="User Name" />
                        
                   	        <g:sortableColumn property="password" title="Password" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${repositoryInstanceList}" status="i" var="repositoryInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${repositoryInstance.id}">${fieldValue(bean:repositoryInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:repositoryInstance, field:'name')}</td>
                        
                            <td>${fieldValue(bean:repositoryInstance, field:'repositoryURL')}</td>
                        
                            <td>${fieldValue(bean:repositoryInstance, field:'description')}</td>
                        
                            <td>${fieldValue(bean:repositoryInstance, field:'userName')}</td>
                        
                            <td>${fieldValue(bean:repositoryInstance, field:'password')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${repositoryInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
