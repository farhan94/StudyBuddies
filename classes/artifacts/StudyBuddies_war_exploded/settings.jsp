<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="com.googlecode.objectify.Ref" %>
<%@ page import="com.recursivebogosort.studybuddies.entities.StudyBuddiesUser" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Settings</title>
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
      <a class="navbar-brand" href="/dashboard.jsp">StudyBuddies</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
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
      
      </ul>
    </div>
  </div>
</nav>

<body>

<div class="jumbotron settingPg text-center">
<h2>Change Your Settings</h2>
<p>Change whatever you want</p>
</div>
<div class="container">
  <div class="row">
  	<div class="col-md-6">
          <form class="form-horizontal" action="/studybuddies" method="POST">
          <fieldset>
            <div id="legend">
              <br>
              <legend class="">Settings</legend>
            </div>
            <div class="control-group">
              <label class="control-label" for="name">Name</label>
              <div class="controls">
            <%   if (user != null) {
         	String id = user.getUserId();
         	Ref<StudyBuddiesUser> sbuRef = ObjectifyService.ofy().load().type(StudyBuddiesUser.class).id(id);
         	StudyBuddiesUser sbu = sbuRef.get(); %>
                <input type="text" id="name" name="name" placeholder="<%out.println(sbu.getName()); %>" class="form-control input-lg">
                <p class="help-block">Your name can be whatever you'd like it to be, this is how others will know who you are</p>
              </div>
            </div>

            <div class="control-group">
              <label class="control-label" for="email">Phone Number</label>
              <div class="controls">
                <input type="tel" id="phoneNumber" name="phoneNumber" placeholder="<% out.println(sbu.getPhoneNumber()); %>" class="form-control input-lg">
                <p class="help-block">Change your Phone Number</p>
              </div>
            </div>

<!--             <div class="control-group">
              <label class="control-label" for="university">University</label>
              
              <div class="controls">
                  <select id = "university" name="university" required="" class="form-control input-lg">
                    <option value="" selected>Select your University</option>
                    <option value="Something">Something</option>
                    <option value="Something Else">Something Else</option>
                </select>
              </div>
            </div> !-->

            <div class="control-group">
              <label class="control-label" for="">Subscriptions</label>
              <div class="controls">
              	<% if(sbu.isSubscribedToEmails()){ %>
                <input type="checkbox" name="subscription" value="emailNotification" checked> I want Email Notifications<br>
                <% } else{ %>
                <input type="checkbox" name="subscription" value="emailNotification"> I want Email Notifications<br>
                <%} if(sbu.isSubscribedToSMS()){ %>
                <input type="checkbox" name="subscription" value="textNotification" checked> I want SMS Notifications
                <% }else { %>
                 <input type="checkbox" name="subscription" value="textNotification"> I want SMS Notifications
                 <%}} %>
              </div>
            </div>
            <br>
            <div class="control-group">
              <!-- Button -->
              <div class="controls">
                <button class="btn btn-success">Change Settings</button>
				<a href="/dashboard.jsp" class="btn btn-danger"> Cancel</a>
              </div>
            </div>
          </fieldset>
        </form>

    </div>
  </div>
</div>
<% } else { %>
      </ul>
    </div>
  </div>
</nav>
<body>
<div class="jumbotron jumbotron3 text-center">
<h2>You need to sign in with your Google Account to see this page!</h2>
<p>Sorry!</p>
</div>
<%} %>
</body>
</html>