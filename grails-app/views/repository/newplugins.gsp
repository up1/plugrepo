
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="plugin" />
        <title>Plugin List</title>
    </head>
    <body>
		<g:each in="${updatedPluginsMap.keySet()}" status="i" var="repositoryName">
				<h3>${repositoryName}</h3>
				<g:render template="/plugin/list" model="['pluginInstanceList': updatedPluginsMap[repositoryName], 'pluginInstanceTotal': updatedPluginsMap[repositoryName].size()]"/>
		</g:each>
    </body>
</html>
