<div class="list">
	<table>
		<thead>
			<tr>
				<g:sortableColumn property="name" title="Name" />
<%--
				<g:sortableColumn property="author" title="Author" />

				<g:sortableColumn property="defaultVersion" title="Default" />
--%>
				<g:sortableColumn property="pluginVersion" title="Version" />
<%--
				<g:sortableColumn property="grailsVersion" title="Grails Version" />

				<g:sortableColumn property="description" title="Description" />
--%>
				<th>Docs</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${pluginInstanceList}" status="i" var="pluginInstance">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

					<td><g:link action="download" params="[plugin:fieldValue(bean:pluginInstance, field:'name'), version:fieldValue(bean:pluginInstance, field:'pluginVersion')]">${fieldValue(bean:pluginInstance, field:'name')}</g:link></td>

<%--
					<td>${fieldValue(bean:pluginInstance, field:'author')}</td>

					<td>${fieldValue(bean:pluginInstance, field:'defaultVersion')}</td>
--%>
					<td>${fieldValue(bean:pluginInstance, field:'pluginVersion')}</td>
<%--
					<td>${fieldValue(bean:pluginInstance, field:'grailsVersion')}</td>

					<td>${fieldValue(bean:pluginInstance, field:'description')}</td>
--%>
					<td><g:if test="${pluginInstance != null}"><g:link action="docs" params="[plugin:fieldValue(bean:pluginInstance, field:'name'), version:fieldValue(bean:pluginInstance, field:'pluginVersion')]">Docs</g:link></g:if></td>
				</tr>
			</g:each>
		</tbody>
	</table>
</div>
<!--
<div class="paginateButtons">
	<g:paginate total="${pluginInstanceTotal}" />
</div>
-->
