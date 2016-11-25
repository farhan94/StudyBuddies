

///////////////////////////////////

//NOTE: Global Variables

///////////////////////////////////

TEST_MODE = false;




///////////////////////////////////

//NOTE: Basic Functions

///////////////////////////////////

$(document).ready(function(){
   // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
   $('.modal').modal();
   $('.datepicker').pickadate({
      selectMonths: true, // Creates a dropdown to control month
      selectYears: 15 // Creates a dropdown of 15 years to control year
    });
    connectSendBird();
 });

var element_list = ["group_info", "messaging","global_notification_list","global_event_list","your_groups","departments","courses","groups","group_notification_list","group_event_list"];

function hideAll(){
  for (var element in element_list){
    hideElement(element_list[element]);
  }
  showElement("background");
}

function findGroups(University){
  hideAll();
  loadDepartments(University);
}

function updateScroll(){
    var element = document.getElementById("messaging");
    element.scrollTop = element.scrollHeight;
}

function yourGroups(){
  if(document.getElementById('your_groups').style.display == 'flex'){
    hideElement('your_groups');
  }else{
    hideAll();
    loadGroups('your_groups', null);
  }
}

function hideElement(element){
  document.getElementById(element).style.display = 'none';
}

function showElement(element){
  document.getElementById(element).style.display = 'flex';
}

function toggleElement(element){
  if(document.getElementById(element).style.display == 'flex'){
    hideElement(element);
  }else{
    showElement(element);
  }
}

///////////////////////////////////

//NOTE: Load Functions

///////////////////////////////////

function loadMessages(group){
  for(var i = 2; i < element_list.length; i++){
    hideElement(element_list[i]);
  }
  hideElement("background");
  showElement("messaging"); //TODO: change this to update from groupID
  updateScroll();
}

function loadDepartments(university){
    //do some logic to get list of departments for given university
    var dept_list;
    if(TEST_MODE){
      dept_list = dummy_dept_list;
    }
    $('#departments #nav-mobile li').not('li:first').empty()
    showElement('departments');
    var getDepartments = {
          "async": true,
          "crossDomain": true,
          "url": "/getdepartments?universityID=" + university,
          "method": "GET",
          "headers": {
            "cache-control": "no-cache",
            "postman-token": "fb38c742-ab74-18f1-d76d-1f3d4560f8ab"
          }
        }
    $.ajax(getDepartments).done(function (response) {
      dept_list = response;
      for(var dept in dept_list){
          var dept_line= "<li class=\'bold\'><a onClick=\"loadCourses(\'" + dept_list[dept].department_uid + "\')\" class=\'waves-effect waves-teal\'>" + dept_list[dept].department_name + "</a></li>"
          $('#departments #nav-mobile').append(dept_line);
      }
    });
}

function loadCourses(department){
  //do some logic to get list of courses for given department
  var course_list;
  if(TEST_MODE){
    course_list = dummy_course_list;
  }
  $('#courses #nav-mobile li').not('li:first').not('li:first').empty()
  showElement('courses');
  var getCourses = {
        "async": true,
        "crossDomain": true,
        "url": "/getcourses?departmentID=" + department,
        "method": "GET",
        "headers": {
          "cache-control": "no-cache",
          "postman-token": "fb38c742-ab74-18f1-d76d-1f3d4560f8ab"
        }
      }
  $.ajax(getCourses).done(function (response) {
    course_list = response;
    for(var course in course_list){
        var course_line= "<li class=\'bold\'><a onClick=\"loadGroups(\'groups\','" + course_list[course].course_uid + "\')\" class=\'waves-effect waves-teal\'>" + course_list[course].course_name + "</a></li>"
        $('#courses #nav-mobile').append(course_line);
    }
  });
}

function loadGroups(groups_type, course){
  var group_list;
  if(TEST_MODE){ group_list = dummy_group_list; }
  $('#' + groups_type + ' #nav-mobile #collection').empty()
  showElement(groups_type);
  if(course != null){
    var getCourseGroups = {
          "async": true,
          "crossDomain": true,
          "url": "/getgroupbycourse?courseID=" + course,
          "method": "GET",
          "headers": {
            "cache-control": "no-cache",
            "postman-token": "fb38c742-ab74-18f1-d76d-1f3d4560f8ab"
          }
        }
    $.ajax(getCourseGroups).done(function (response) {
      group_list = response;
      for(var group in group_list){
          addGroup(groups_type, group_list[group]);
      }
    });
  }else{
    //TODO: get groups for user
  }
}

function loadNotifications(notification_type, uid){
  var notifications_list;
  if(TEST_MODE){
    notifications_list = dummy_notifications_list;
  }
  else if(notification_type == "global_notification_list"){
    //do some logic to get list of all events
  }else{
    //do some logic to get list of events for given group
  }
  showElement(notification_type);
  for(var notification in notifications_list){
      //TODO: eventually
  }
}

function loadEvents(event_type, uid){
  var event_list;
  if(TEST_MODE){
    event_list = dummy_event_list;
  }
  else if(event_type == "global_event_list"){
    //do some logic to get list of all events
  }else{
    //do some logic to get list of events for given group
  }
  showElement(event_type);
  for(var event_item in event_list){
      addEvent(event_type, event_item);
  }
}

///////////////////////////////////

//NOTE: Add Single Item Functions

///////////////////////////////////

function submitMessage(){
  var message = {};
  message.text = $('#message_input #icon_prefix')[0].value;
  if(message.text == "") {return;}
  if(addMessage(message)){
    $('#message_input #icon_prefix')[0].value = "";
  }else{
    //TODO: Message did not send
  }
}

function addMessage(message){
  var color = isAuthor(message) ? "teal" : "blue-grey"
  var message_line = "<li class=\"message-right\"><div class=\"card " + color + " lighten-3 message text-right\">";
  message_line += "<div class=\"card-content white-text\"><p>" + message.text;
  message_line += "</p></div></div></li>";
  $('#messaging ul').append(message_line);
  updateScroll();
  return true;
}

function addGroup(groups_type, group){
    var group_uid = group.uid;
    var group_icon_url = group.icon_url;
    var group_name = group.name;
    var group_size = group.size;
    var group_purpose = group.purpose;
    var group_join_leave = group.is_member ? 'Leave' : 'Join';
    var group_line = "<li onClick=\"loadGroupInfo(\'" + group_uid + "\')\" alt class=\'collection-item avatar waves-effect waves-teal z-depth-2\'>";
    group_line += "<img src=\"" + group_icon_url + "\" class=\"circle group_icon\">";
    group_line +=  "<div class=\"study_budy_info\"><span class=\"title\">" + group_name + "</span>";
    group_line +=  "<p>" + group_size + " members <br>" + group_purpose + "</p></div>";
    group_line +=  "<a href=\"#!\" class=\"group_joinORleave\"><p>" + group_join_leave + "<br> Group </p></a></li>";
    $('#' + groups_type + ' #nav-mobile #collection').append(group_line);
}

function addEvent(event_type, event_item){
  var event_id = event_item.uid;
  var event_name = event_item.name;
  var event_description = event_item.description;
  //var group_icon_url = event_list[group].creator;
  var event_date = event_item.date;
  var event_time= event_item.time;
  var event_location = event_item.location;
  //var group_purpose = event_list[group].duration;

  var event_line = "<li><div class=\"card teal darken-2\" style=\"margin: 5px; line-height:initial;\">";
  event_line += "<div class=\"card-content white-text\"><span class=\"card-subtitle\">";
  event_line += event_name + "</p><p><b>Time: </b>";
  event_line += event_date + "at" + event_time + "</br><b>Duration: </b>";
  event_line += "3 hours" + "</br><b>Location: </b>";
  event_line += event_location + "</p></div><div class=\"card-action\"><a onClick=\"\" >";
  event_line += "Leave" + "</a></div></div></li>"
  //$('#group_event_list #nav-mobile').append(event_line);
  $('#' + event_type + ' #nav-mobile').append(event_line);
}



function loadGroupInfo(group){
  //do some logic to get group information for given uid

  var group
  if(TEST_MODE){
    group = dummy_group;
  }
  var getGroupInfo = {
          "async": true,
          "crossDomain": true,
          "url": "/getgroupinfo?groupID=" + group,
          "method": "GET",
          "headers": {
            "cache-control": "no-cache",
            "postman-token": "fb38c742-ab74-18f1-d76d-1f3d4560f8ab"
          }
        }
    $.ajax(getGroupInfo).done(function (response) {
      group = response;
    	  var group_icon_url = group.icon_url;
    	  var group_name = group.name;
    	  var isMember = group.is_member;
    	  var group_purpose = group.purpose;
    	  var group_new_notifications = 4;
    	  var group_new_messages = 3;
    	  var group_events = 5;
    	  $('#group_info #nav-mobile img')[0].src = group_icon_url;
    	  $('#group_info #nav-mobile #name')[0].textContent = group_name;
    	  $('#group_info #nav-mobile #purpose')[0].textContent = group_purpose;
    	  $('#group_info #nav-mobile #joinleavebtn')[0].remove();
    	  if(!isMember){
    	    $('#group_info #nav-mobile #notifications')[0].style.display = "none";
    	    $('#group_info #nav-mobile #messages')[0].style.display = "none";
    	    $('#group_info #nav-mobile #events')[0].style.display = "none";
    	    $('#group_info #nav-mobile #settings')[0].style.display = "none";
    	  }else{
    	    $('#group_info #nav-mobile')[0].style.display = "flex";
    	    $('#group_info #nav-mobile #notifications #badge')[0].textContent = group_new_notifications;
    	    $('#group_info #nav-mobile #notifications a')[0].setAttribute("onclick", "loadNotifications('group_notification_list', " + group.uid + ")");
    	    $('#group_info #nav-mobile #messages #badge')[0].textContent = group_new_messages;
    	    $('#group_info #nav-mobile #events #badge')[0].textContent = group_events;
    	    $('#group_info #nav-mobile #events a')[0].setAttribute("onclick", "loadEvents('group_event_list', " + group.uid + ")");
    	  }
    	  var color = isMember ? "red" : "";
    	  var join_or_leave = isMember ? "Leave" : "Join";

    	  var btn = "<li id=\"joinleavebtn\"><a class=\"waves-effect " + color + " waves-light btn\">" + join_or_leave + " Group</a></li>";
    	  $('#group_info #nav-mobile').append(btn);
    });
  showElement("group_info");
}

///////////////////////////////////

//NOTE: Helper Functions

///////////////////////////////////



function isAuthor(message){
  return true;
}

///////////////////////////////////

//NOTE: SendBird Functions

///////////////////////////////////

var sb;
var currentChannel = null;

function connectSendBird(){
  sb = new SendBird({appId: "B3E1CBF0-0C08-42FB-A967-4817AE8FE95A"});
}

function connectUser(UserID, NickName){
  sb.connect(UserID, function(user, error) {});
}

function updateUser(NickName, Profile_url){
  sb.updateCurrentUserInfo(NickName, Profile_url, function(response, error) {
    console.log(response, error);
  });
}

function createOpenChannel(Channel_name, Cover_url, Data){
  sb.OpenChannel.createChannel(Channel_name, Cover_url, Data, function (channel, error) {
    if (error) {
        console.error(error);
        return;
    }
    console.log(channel);
    var currentChannel = channel;
    return channel
  });
}

function getOpenChannels(){
  var openChannelListQuery = sb.OpenChannel.createOpenChannelListQuery();
  openChannelListQuery.next(function (response, error) {
    if (error) {
        console.log(error);
        return;
    }
    console.log(response);
  });
}

function enterChannel(Channel_url){
  sb.OpenChannel.getChannel(Channel_url, function (channel, error) {
    if (error) {
        console.error(error);
        return;
    }

    channel.enter(function(response, error){
        if (error) {
            console.error(error);
            return;
        }
        currentChannel = channel;
    });
  });
}

function exitChannel(Channel_url){
  sb.OpenChannel.getChannel(Channel_url, function (channel, error) {
    if (error) {
        console.error(error);
        return;
    }

    channel.exit(function(response, error){
        if (error) {
            console.error(error);
            return;
        }
        currentChannel = null;
    });
  });
}

function loadPreviousMessages(){
  var messageListQuery = currentChannel.createPreviousMessageListQuery();
  messageListQuery.load(20, true, function(messageList, error){
    if (error) {
        console.error(error);
        return;
    }
    console.log(messageList);
  });
}

function loadMoreMessages(message_timestamp){
  var messageListQuery = channel.createMessageListQuery();

  messageListQuery.prev(message_timestamp, 20, true, function(messageList, error){
      if (error) {
          console.error(error);
          return;
      }
      console.log(messageList);
  });
}

function sendMessage(Message){
  currentChannel.sendUserMessage(Message, null, function(message, error){
    if (error) {
        console.error(error);
        return;
    }
    console.log(message);
  });
}

function connectChannelHandler(){
  var ChannelHandler = new sb.ChannelHandler();

  ChannelHandler.onMessageReceived = messageReceived;

  sb.addChannelHandler("Study_buddies_handler", ChannelHandler);
}

function messageReceived(channel, message){
  console.log(channel, message);
}

///////////////////////////////////

//NOTE: Test Functions

///////////////////////////////////


function testLoadDepartments(){
  TEST_MODE = true;
  loadDepartments();
}

function testLoadCourses(){
  TEST_MODE = true;
  loadCourses(null);
}

function testLoadGroups(){
  TEST_MODE = true;
  loadGroups("groups",null);
}

function testLoadGroupInfo(){
  TEST_MODE = true;
  loadGroupInfo(null);
}

function testLoadEvents_Group(){
  TEST_MODE = true;
  loadEvents("group_event_list", null);
}

function testLoadEvents_Global(){
  TEST_MODE = true;
  loadEvents("global_event_list", null);
}
var dummy_dept_list = ['Dance', 'Biology', 'Physical Education', 'Geology','Civil Engineering'];
var dummy_course_list = ['Dance 101 ', 'Biology 304', 'Physical Education 202', 'Geology 331','Civil Engineering 432'];
var dummy_group = {uid: 4713, icon_url: "http://simpleicon.com/wp-content/uploads/wifi_11.png", name: "ArmStrongs", size: 9, purpose: "To go where no man has gone before", is_member: true};
var dummy_group2 = {uid: 2917, icon_url: "http://simpleicon.com/wp-content/uploads/wifi_11.png", name: "Genius", size: 3, purpose: "T100 on every test", is_member: false};
var dummy_group_list = [dummy_group, dummy_group2];
var dummy_event = {id: 4713, name: "Best event ever", description: "yes, it's true", date: "Dec 9", time: "10:00 PM",  location: "Starbucks"};
var dummy_event2 = {id: 2917, name: "Another event", description: "another one", date: "Nov 3", time: "9:00 PM", location: "Somewhere over the rainbow"};
var dummy_event_list = [dummy_event, dummy_event2];
//
