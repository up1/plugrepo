
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="plugin" />
        <title>Plugin Upload</title>
		<g:javascript library="prototype" />
    </head>
    <body>
		<div class="body">
				<g:if test="${flash.message}">
					<div class="message">${flash.message}</div>
				</g:if>
				<g:hasErrors bean="${plugin}">
					<g:renderErrors bean="${plugin}" as="list" />
				</g:hasErrors>
				<p/>
				<h2>Upload Plugin</h2>
				<g:render template="upload"/>
		</div>
	</body>
</html>
