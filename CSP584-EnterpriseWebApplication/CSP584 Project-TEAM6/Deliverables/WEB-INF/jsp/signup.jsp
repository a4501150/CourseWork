<%@ page import = "java.util.List" %>
<%@ page import = "team6.entity.User, team6.entity.Role" %>
<%@include file="./partials/header.jsp"%>
		<div class="k2t-body">
		
			<div class="k2t-title-bar">
				<div class="container k2t-wrap">
					<h1 class="main-title k2t-cf7-title" style="font-size:36px;color:#283c5a;">
						Sign up
					</h1>
				</div>
			</div>
			
			
			
			<div class="k2t-content no-sidebar k2t-confirm-content">
				<div class="container k2t-wrap">
					<main class="k2t-main page">
						<div class="page-entry">
								<center>
	                        	<br> <br>
	                        	<form method="post" action="<%= rootPath %>/register">
		                        	<div class="k2t-gr1-field">
		                               	<div class="div-border">
		                               	<input name="email" id='email' type="text" placeholder="Email*" />
		                               	</div>
		                            </div>
		                            <br> 
		                            <div class="k2t-gr1-field">
		                               	<div class="div-border">
		                               	<input name="username" id='username' type="text" placeholder="Username*" />
		                               	</div>
		                            </div>
		                            <br> 
		                            <div class="k2t-gr1-field">
										<div class="div-border">
										<input name="password" id='password' type="password" placeholder="Password*" />
										</div>
									</div>
									<br> 
									<div class="k2t-gr1-field">
										<div class="div-border">
										<input name="cfp" id='cfp' type="password" placeholder="Confirm Password*" />
										</div>
									</div>
									<br>
									<%-- TODO: frontend for input Role (manager function) --%>
									<% if(currentUser != null && currentUser.getRole().equals(Role.MANAGER)) { %>
										<div class="k2t-gr1-field">
											<div class="div-border">
											<label for="role">Role: </label>
											<select name="role">
												<% for(Role role: Role.values()) { %>
													<option value="<%= role.toString().toLowerCase()%>"><%= role.toString() %></option>
												<% } %>
											</select>
											</div>
										</div>
										<br>
									<% } %>
									<% Boolean registerFailed = (Boolean) request.getAttribute("register-failed");
									if(registerFailed != null && registerFailed.equals(Boolean.TRUE)) {
										List<String> listError = (List<String>) request.getAttribute("list-error");
										for(String str: listError) { %>
											<span style="color:red"><%= str %></span>
										<% }
									} %>  
									<button id='signupbutton'> Sign up </button>
								</form>
								</center>
						</div>
					</main>
				</div>
			</div>
			
		</div>




<%@include file="./partials/footer.jsp" %>