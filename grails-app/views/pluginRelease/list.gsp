
<%@ page import="net.jetztgrad.plugrepo.PluginRelease" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>PluginRelease List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New PluginRelease</g:link></span>
        </div>
        <div class="body">
            <h1>PluginRelease List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="name" title="Name" />
                        
                   	        <th>Repository</th>
                   	    
                   	        <th>Plugin</th>
                   	    
                   	        <g:sortableColumn property="pluginVersion" title="Plugin Version" />
                        
                   	        <g:sortableColumn property="grailsVersion" title="Grails Version" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${pluginReleaseInstanceList}" status="i" var="pluginReleaseInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${pluginReleaseInstance.id}">${fieldValue(bean:pluginReleaseInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:pluginReleaseInstance, field:'name')}</td>
                        
                            <td>${fieldValue(bean:pluginReleaseInstance, field:'repository')}</td>
                        
                            <td>${fieldValue(bean:pluginReleaseInstance, field:'plugin')}</td>
                        
                            <td>${fieldValue(bean:pluginReleaseInstance, field:'pluginVersion')}</td>
                        
                            <td>${fieldValue(bean:pluginReleaseInstance, field:'grailsVersion')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${pluginReleaseInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
