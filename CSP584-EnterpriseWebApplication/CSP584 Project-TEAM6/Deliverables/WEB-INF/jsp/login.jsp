<%@include file="./partials/header.jsp"%>

<div class="k2t-body">
	<div class="k2t-title-bar">
		<div class="container k2t-wrap">
			<h1 class="main-title k2t-cf7-title" style="font-size:36px;color:#283c5a;">
				login
			</h1>
		</div>
	</div>
	<div class="k2t-content no-sidebar k2t-confirm-content">
		<div class="container k2t-wrap">
			<main class="k2t-main page">
				<div class="page-entry">
					<center>
                       	<br> <br>
                       	<form method="post">
	                       	<div class="k2t-gr1-field">
		                       	<div class="div-border">
		                       		<input required id='username' name="username" type="text" placeholder="username" />
		                       	</div>
	                   		</div>
	                        <br> 
	                        <div class="k2t-gr1-field">
								<div class="div-border">
									<input required id='password' name="password" type="password" placeholder="Password" />
								</div>
							</div>
							<br>
							<% Boolean loginFailed = (Boolean) request.getAttribute("login-failed");
							if(loginFailed != null && loginFailed.equals(Boolean.TRUE)) { %>
								<span style="color:red">Invalid username or password.</span>
							<% } %> 
							<button id="loginbutton" type="submit"> Login </button>
						</form>
					</center>
				</div>
			</main>
		</div>
	</div>
	
</div>

<%@include file="./partials/footer.jsp" %>