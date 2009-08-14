
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="plugin" />
        <title>Plugin List</title>
    </head>
    <body>
		<div class="body">
			<h1>Plugin Repository</h1>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${plugin}">
				<g:renderErrors bean="${plugin}" as="list" />
			</g:hasErrors>
			<p/>

			<h2>Upload Plugin</h2>
			<div class="list">
				<g:uploadForm action="store">
					<tr class="prop">
                        <td valign="top" class="name">
                            <label for="repository">Repository:</label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'repository','errors')}">
                            <g:select optionKey="id" from="${net.jetztgrad.plugrepo.Repository.list()}" name="repository.id" value="${pluginInstance?.repository?.id}" noSelection="['null':'']"></g:select>
                        </td>
                    </tr>
					<input type="file" name="pluginFile"/><br/>
					<input type="checkbox" name="updateIfExists"/> Update if exists<br/>
					<input type="submit" value="Upload"/><br/>
				</g:uploadForm>
			</div>
		</div>
	</body>
</html>
