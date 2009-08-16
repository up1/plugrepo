
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="plugin" />
        <title>Plugin List</title>
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
				<div class="list">
					<table>
					<g:uploadForm action="store">
						<tr class="prop">
							<td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'repository','errors')}">
								<label for="repository">Upload to repository</label>
								<g:select optionKey="id" from="${repositories}" name="repository.id" value="${preselectedRepository.id}"></g:select>
							</td>
						</tr>
						<tr class="prop">
							<td valign="top">
								<input type="file" name="pluginFile"/><br/>
							</td>
						</tr>
						<tr class="prop">
							<td valign="top">
								<input type="checkbox" name="updateIfExists"/> Update if exists<br/>
							</td>
						</tr>
						<tr class="prop">
							<td valign="top">
								<input type="submit" value="Upload"/><br/>
							</td>
						</tr>
					</g:uploadForm>
					</table>
				</div>
		</div>
	</body>
</html>
