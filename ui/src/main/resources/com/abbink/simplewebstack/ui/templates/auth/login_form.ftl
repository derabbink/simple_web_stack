<html>
<head>
	<title>Login</title>
</head>
<body>
	<h1>Login</h1>
	<#if showFailure>
	<div>
		<strong>Login Failed.</strong>
		Please verify your user name and password, and try again.
	</div>
	</#if>
	<form action="/login" method="post">
		<div>
			<label for="username">User Name:</label>
			<input type="text" name="username" id="username" value="${username}"/>
		</div>
		<div>
			<label for="password">Password:</label>
			<input type="password" name="password" id="password"/>
		</div>
		<div>
			<input type="submit" value="Sign In"/>
		</div>
	</form>
</body>
</html>
