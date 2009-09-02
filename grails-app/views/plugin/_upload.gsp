<div class="list">
	<table>
	<g:uploadForm controller="plugin" action="store">
		<tr class="prop">
			<td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'repository','errors')}">
				<label for="repository">Upload to repository</label>
				<g:select optionKey="id" from="${repositories}" name="repository.id" value="${preselectedRepository.id}"></g:select>
			</td>
		</tr>
		<tr class="prop">
			<td valign="top">
				<input type="file" name="pluginFile" style="width: 400;"/><br/>
			</td>
		</tr>
		<tr class="prop">
			<td valign="top">
				<g:checkBox name="useAsDefaultVersion" value="${true}" /> Use as default version<br/>
			</td>
		</tr>
		<tr class="prop">
			<td valign="top">
				<g:checkBox name="updateIfExists" value="${false}" /> Update if exists<br/>
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
