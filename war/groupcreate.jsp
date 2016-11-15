<%@ page import="com.googlecode.objectify.ObjectifyService"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page
	import="com.recursivebogosort.studybuddies.entities.StudyBuddiesUser"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.googlecode.objectify.Ref"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Create a StudyBuddies Group</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"
	type="text/javascript"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="stylesheets/homestyle.css" />
</head>
<nav class="navbar navbar-default">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
				<%
					ObjectifyService.register(StudyBuddiesUser.class);
					UserService userService = UserServiceFactory.getUserService();
					User user = userService.getCurrentUser();
					if (user != null) {
						String id = user.getUserId();
						Ref<StudyBuddiesUser> sbuRef = ObjectifyService.ofy().load().type(StudyBuddiesUser.class).id(id);
						StudyBuddiesUser sbu = sbuRef.get();
						if (sbu != null) {
				%>

				<%
					}
					}

					//UserService userService = UserServiceFactory.getUserService();
					//User user = userService.getCurrentUser();
					if (user != null) {
						pageContext.setAttribute("user", user);
				%>
				<a class="navbar-brand" href="/dashboard.jsp">StudyBuddies</a>
			</ul>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav navbar-right">
				<li><a
					href='<%=userService.createLogoutURL("/homepage.jsp")%>'><span
						class="glyphicon glyphicon-log-out"></span> LOG OUT</a></li>
				<%
					} else {
				%>
				<a class="navbar-brand" href="/homepage.jsp">StudyBuddies</a>
			</ul>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav navbar-right">
				<%
					}
				%>
			</ul>
		</div>
	</div>
</nav>

<body>
	<%
		if (user != null) {
	%>
	<div class="jumbotron groupReg text-center">
		<h2>Create a Study Group Here!</h2>
		<p>Fill out this form to make a new group!</p>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<form class="form-horizontal" action="/groupcreate" method="POST">
					<fieldset>
						<!-- Group Form -->
						<div id="legend">
							<br>
							<legend class="">Register</legend>
						</div>

						<div class="control-group">
							<label class="control-label" for="group_name">Group Name</label>
							<div class="controls">
								<input type="text" id="group_name" name="group_name"
									required="true" placeholder="" class="form-control input-lg">
								<p class="help-block">You group name can be whatever you'd
									like it to be</p>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="max_size">Max Size</label>
							<div class="controls">
								<input type="number" min="0" max="50" id="max_size"
									name="max_size" required="" placeholder=""
									class="form-control input-lg">
								<p class="help-block">Choose the max size of your group.
									Zero will not put a limit on the group size. If you need to you
									can change this later.</p>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="join_by_request">Make
								Group Public</label>
							<div class="controls">
								<input type="checkbox" id="join_by_request"
									name="join_by_request" required="true"
									class="form-control input-lg">
								<p class="help-block">Making your group public will allow
									anyone to join as long as the max size is not met. A private
									group will require you to approve user request</p>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="group_description">Group
								Description</label>
							<div class="controls">
								<input type="text" id="group_description"
									name="group_description" required="false"
									placeholder="Provide a description about your groups goals and purpose."
									class="form-control input-lg">
							</div>
						</div>


						<!-- Course Form -->
						<!-- University Stuff Not Needed
                    <div class="control-group">
                        <label class="control-label" for="university">University</label>
                        <div class="controls">
                            <input type="text" id="university" name="university" required="true" placeholder="" class="form-control input-lg">
                            <p class="help-block"></p>
                        </div>
                    </div> -->

						<div class="control-group">
							<label class="control-label" for="course_id">Course Id</label>
							<div class="controls">
								<input type="text" id="course_id" name="course_id"
									required="true" placeholder="" class="form-control input-lg">
								<p class="help-block">The course number. Example EE461S or
									M360L</p>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="course_name">Course
								Name</label>
							<div class="controls">
								<input type="text" id="course_name" name="course_name"
									required="true" placeholder="" class="form-control input-lg">
								<p class="help-block">The name of the course that you are
									creating this group for. Example Vector Calculus</p>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="professor">Course
								Professor</label>
							<div class="controls">
								<input type="text" id="professor" name="professor"
									required="false" placeholder="" class="form-control input-lg">
								<p class="help-block"></p>
							</div>
						</div>

						<br>
						<div class="control-group">
							<!-- Button -->
							<div class="controls">
								<button type="submit" class="btn btn-success">Create
									Group</button>
								<a href="/dashboard.jsp" class="btn btn-danger"> Cancel</a>
							</div>
						</div>
					</fieldset>
				</form>

			</div>
		</div>
	</div>
	<%
		} else {
	%>
	<div class="jumbotron jumbotron3 text-center">
		<h2>You need to be a registered member to view this page!</h2>
		<p>Sorry!</p>
	</div>
	<%
		}
	%>
</body>

</html>
