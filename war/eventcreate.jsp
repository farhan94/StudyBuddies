<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.recursivebogosort.studybuddies.entities.StudyBuddiesUser" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.Ref" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.recursivebogosort.studybuddies.entities.*"%>

<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%><%--
  Created by Farhan
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create a StudyBuddies Event</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="stylesheets/homestyle.css" />
</head>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">StudyBuddies</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">ABOUT</a></li>
                <li><a href="#">FEATURES</a></li>
                <%
                    ObjectifyService.register(StudyBuddiesUser.class);
                    UserService userService = UserServiceFactory.getUserService();
                    User user = userService.getCurrentUser();
                   

                    //UserService userService = UserServiceFactory.getUserService();
                    //User user = userService.getCurrentUser();
                    if (user != null) {
                        pageContext.setAttribute("user", user);
                %>
                <li><a href="<%= userService.createLogoutURL("/homepage.jsp")%>"><span class="glyphicon glyphicon-log-out"></span> LOG OUT</a></li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>

<body>
<% if(user != null ){ 
         String id = user.getUserId();
         Ref<StudyBuddiesUser> sbuRef = ObjectifyService.ofy().load().type(StudyBuddiesUser.class).id(id);
         StudyBuddiesUser sbu = sbuRef.get();
         if(sbu != null){ 



%>
<div class="jumbotron groupReg text-center">
    <h2>Create an Event For a Study Group!</h2>
    <p>Fill out this form to create an event!</p>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <form class="form-horizontal" action="/eventcreate" method="POST">
                <fieldset>
                    <!-- Group Form -->
                    <div id="legend">
                        <br>
                        <legend class="">CreateEvent</legend>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="event_name">Event Name</label>
                        <div class="controls">
                            <input type="text" id="event_name" name="event_name" required="true" placeholder="" class="form-control input-lg">
                            <p class="help-block">You event name can be whatever you'd like it to be</p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="event_description">Event Description</label>
                        <div class="controls">
                            <input type="text" id="event_description" name="event_description" required="true" placeholder="Provide a description about your event's goals and purpose." class="form-control input-lg">
                        </div>
                    </div>
                    <div class="control-group">
		            	<label class="control-label" for="group">Group</label>
			            <div class="controls">
			              <select id = "group" name="group" required="" class="form-control input-lg">
			                <option value="" selected>Select the Group for the Event</option>
			                <% Iterator i = sbu.getMyGroups().iterator();
			                		while(i.hasNext()){
			                			Ref<GroupOwner> one = (Ref<GroupOwner>)i.next();
			                			one = ObjectifyService.ofy().load().ref(one);
			                			GroupOwner go = one.get();
			                			%>
			                			<option value="<% out.print(go.getGroup().getId()); %>"><% out.print(go.getGroup().getGroupName());%></option>
			                	<% 	}
			            	%>

			              </select>
			            </div>
		          </div>
                    <div class="control-group"> <!--  MIN DATE IS NOT WORKING -->
                    	<label class="control-label" for="date">Choose a Date for Your Event</label>
                    	<div class="controls">
                    	<input type="datetime-local" id = "date" name="date" required="true" min="<% DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
						Date date = new Date();
						out.print(dateFormat.format(date));%>">
                    	</div>
                    </div>
                    <br>
                    <div class="control-group">
                        <!-- Button -->
                        <div class="controls">
                            <button type="submit" class="btn btn-success">Create Event</button>
                            <a href="/dashboard.jsp" class="btn btn-danger"> Cancel</a>
                        </div>
                    </div>
                </fieldset>
            </form>

        </div>
    </div>
</div>

    <% } } else { %>
<div class="jumbotron jumbotron3 text-center">
    <h2>You need to be a registered member to view this page!</h2>
    <p>Sorry!</p>
</div>
<%} %>
</body>

</html>
