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
				<g:each in="${pluginInstanceList}" status="i" var="pluginInstance">
					<g:render template="plugin" model="['pluginInstance':pluginInstance]"/>
				</g:each>
			</div>
			<div class="paginateButtons">
				<g:paginate total="${pluginInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
