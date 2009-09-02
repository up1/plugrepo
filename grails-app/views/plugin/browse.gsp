
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="plugin" />
        <title>Plugin List</title>
    </head>
    <body>
        <div class="body">
            <h1>Plugin List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
							<g:sortableColumn property="name" title="Name" />
							<g:sortableColumn property="author" title="Author" />
							<g:sortableColumn property="description" title="Description" />
							<%--
							<g:sortableColumn property="defaultVersion" title="Default" />
							<g:sortableColumn property="pluginVersion" title="Version" />
							<g:sortableColumn property="grailsVersion" title="Grails Version" />
							--%>
							<th class="sortable">Docs</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${pluginInstanceList}" status="i" var="pluginInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <%--<td><g:link action="download" params="[plugin:fieldValue(bean:pluginInstance, field:'name'), version:fieldValue(bean:pluginInstance, field:'pluginVersion')]">${fieldValue(bean:pluginInstance, field:'name')}</g:link></td>--%>
							<td><g:link action="download" params="[plugin:fieldValue(bean:pluginInstance, field:'name')]">${fieldValue(bean:pluginInstance, field:'name')}</g:link></td>
							<td>${fieldValue(bean:pluginInstance, field:'author')}</td>
							<td>${fieldValue(bean:pluginInstance, field:'description')}</td>
							<%--
							<td>${fieldValue(bean:pluginInstance, field:'defaultVersion')}</td>
							<td>${fieldValue(bean:pluginInstance, field:'pluginVersion')}</td>
							<td>${fieldValue(bean:pluginInstance, field:'grailsVersion')}</td>
							--%>
							<%--
                            <td><g:link action="docs" params="[plugin:fieldValue(bean:pluginInstance, field:'name'), version:fieldValue(bean:pluginInstance, field:'pluginVersion')]">Docs</g:link></td>--%>
                            <td><g:link action="docs" params="[plugin:fieldValue(bean:pluginInstance, field:'name')]">Docs</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${pluginInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
