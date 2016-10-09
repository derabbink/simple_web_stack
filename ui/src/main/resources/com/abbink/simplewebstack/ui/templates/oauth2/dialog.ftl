<html>
<head>
	<title>Login to {}</title>
</head>
<body>
	<h1>Login to </h1>
	<#if hasSubject>
	<div>
		<div><lable>Principal:</lable> ${principal!"null"} (${principalType!"no type"})</div>
		<div><lable>Is Authenticated:</lable> ${isAuthenticated?string}</div>
		<div><lable>Is Remembered:</lable> ${isRemembered?string}</div>
		<div><lable>Has Session:</lable> ${hasSession?string}</div>
	</div>
	</#if>
	<#if hasSession>
	<div>
		<div><lable>Session ID:</label> ${sessionId}</div>
		<div><lable>Session Start Time:</label> ${sessionStartTime?datetime}</div>
		<div><lable>Session Last Access Time:</label> ${sessionLastAccessTime?datetime}</div>
		<div><lable>Session Timeout:</label> ${sessionTimeout}ms</div>
	</div>
	</#if>
	<#if hasSubject && (isAuthenticated || isRemembered)>
	<div>
		<a href="${logoutUri}">logout</a>
	</div>
	<#else>
	<div>Not logged in. (<a href="${loginUri}">login</a>)</div>
	</#if>
</body>
</html>
