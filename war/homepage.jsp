<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>StudyBuddies</title>
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

<div class="jumbotron jumbotron2 text-center">
<h1>StudyBuddies</h1>
<p>Studying made simple</p>
      <a href="/studybuddies"  class="btn btn-default btn-md">Sign In</a>
</div>

<div class="container-fluid bg-2 text-center">
  <h2>What is StudyBuddies?</h2>
  <p>StudyBuddies is a web application that makes finding, creating, and managing study groups easy.</p>
  <p>Just find your class to begin searching for existing study groups or create a new study group for your class!</p>
</div>

<div class="container-fluid text-center">
  <h2>Features:</h2>
  <br>
  <div class="row">
    <div class="col-sm-4">
      <h4>Dashboard</h4>
      <p>The most important information, right where you need it.</p>
      <img src="" class="img-responsive" style="width:100%" alt="Image">
    </div>
    <div class="col-sm-4">
      <h4>Notifications</h4>
      <p>Stay updated on study group events with text message notifications, sent using Twilio.</p>
      <img src="images/homepage/sms.jpg" class="img-responsive" style="width:100%" alt="Image">
    </div>
    <div class="col-sm-4">
      <h4>In Group Messaging</h4>
      <p>All your group messages, easily accessible.</p>
      <img src="" class="img-responsive" style="width:100%" alt="Image">
    </div>
  </div>
</div>

</body>
</html>
