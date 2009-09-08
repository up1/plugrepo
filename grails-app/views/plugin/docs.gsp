
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="plugin" />
        <title>Plugin Documentation</title>
		<g:javascript library="prototype" />
    </head>
    <body>
        <div class="body">
            <h1>Documentation of Plugin ${fieldValue(bean:pluginRelease, field:'name')}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
			<g:if test="${pluginRelease?.documentationUrl}">
            	<div class="list">
					External Documentation: <a href="${fieldValue(bean:pluginRelease, field:'documentationUrl')}">Doc Link</a>
				</div>
			</g:if>
			<g:if test="${pluginRelease.plugin?.documentation}">
				<div class="list">
					<h2>Documentation</h2>
					${fieldValue(bean:pluginRelease.plugin, field:'documentation')}
				</div>
			</g:if>
			<g:if test="${pluginRelease?.documentationUrl == null && pluginRelease.plugin?.documentation == null}">
				<div class="list">
					No documentation available.
				</div>
			</g:if>
        </div>
    </body>
</html>
