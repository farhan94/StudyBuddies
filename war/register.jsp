<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Register for StudyBuddies</title>
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
	    	UserService userService = UserServiceFactory.getUserService();
	    	User user = userService.getCurrentUser();
	    	if (user != null) {
	      		pageContext.setAttribute("user", user);
		%>
      <li><a href="<%= userService.createLogoutURL("/homepage.jsp")%>"><span class="glyphicon glyphicon-log-out"></span> LOG OUT</a></li>
      <%} %>
      </ul>
    </div>
  </div>
</nav>

<body>
   <%
	    	if (user != null) {
	      		pageContext.setAttribute("user", user);
		%>
<div class="jumbotron jumbotron3 text-center">
<h2>So you want to join?</h2>
<p>Fill out this form and we will sign you up!</p>
</div>
<div class="container">
  <div class="row">
  	<div class="col-md-6">
          <form class="form-horizontal" action="/studybuddies" method="POST">
          <fieldset>
            <div id="legend">
              <br>
              <legend class="">Register</legend>
            </div>
            <div class="control-group">
              <label class="control-label" for="name">Name</label>
              <div class="controls">
                <input type="text" id="name" name="name" required="" placeholder="" class="form-control input-lg">
                <p class="help-block">Your name can be whatever you'd like it to be, this is how others will know who you are</p>
              </div>
            </div>

            <div class="control-group">
              <label class="control-label" for="email">Phone Number</label>
              <div class="controls">
                <input type="tel" id="phoneNumber" name="phoneNumber" required="" placeholder="" class="form-control input-lg">
                <p class="help-block">Please provide your Phone Number</p>
              </div>
            </div>

            <div class="control-group">
              <label class="control-label" for="university">University</label>
              <div class="controls">
                  <select id = "university" name="university" required="" class="form-control input-lg">
                    <option value="" selected>Select your University</option>
                    <option value="Something">Something</option>
                    <option value="Something Else">Something Else</option>
                </select>
              </div>
            </div>

            <div class="control-group">
              <label class="control-label" for="">Subscriptions</label>
              <div class="controls">
                <input type="checkbox" name="subscription" value="emailNotification"> I want Email Notifications<br>
                <input type="checkbox" name="subscription" value="textNotification"> I want SMS Notifications
              </div>
            </div>
            <br>
            <div class="control-group">
              <!-- Button -->
              <div class="controls">
                <button class="btn btn-success">Register</button>
				<a href="<%= userService.createLogoutURL("/homepage.jsp") %>" class="btn btn-danger"> Cancel and Log Out</a>
              </div>
            </div>
          </fieldset>
        </form>

    </div>
  </div>
</div>
<% } else { %>
<div class="jumbotron jumbotron3 text-center">
<h2>You need to sign in with your Google Account to see this page!</h2>
<p>Sorry!</p>
</div>
<%} %>
</body>
</html>
