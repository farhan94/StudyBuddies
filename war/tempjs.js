
TEST_MODE = false;

document.r

var element_list = ["group_info", "messaging","all_your_groups","departments","courses","groups"];

function hideAll(){
  for (var element in element_list){
    hideElement(element_list[element]);
  }
}

function findGroups(){
  hideAll();
  showElement('departments');
}

function updateScroll(){
    var element = document.getElementById("messaging");
    element.scrollTop = element.scrollHeight;
}

function allGroups(){
  if(document.getElementById('all_your_groups').style.display == 'flex'){
    hideElement('all_your_groups');
  }else{
    hideAll();
    showElement('all_your_groups');
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

function submitMessage(){
  var Message = $('#message_input #icon_prefix')[0].value;
  addMessage(Message);
  $('#message_input #icon_prefix')[0].value = "";
}

function addMessage(message_text){

  var message_line = "<li class=\"message-right\"><div class=\"card blue-grey lighten-2 message text-right\">";
  message_line += "<div class=\"card-content white-text\"><p>" + message_text;
  message_line += "</p></div></div></li>";
  $('#messaging ul').append(message_line);
  updateScroll();
}

function loadMessages(group){
  for(var i = 2; i < element_list.length; i++){
    hideElement(element_list[i]);
  }
  showElement("messaging");
  updateScroll();
}

function loadDepartments(university){
    //do some logic to get list of departments for given university
    var dept_list;
    if(TEST_MODE){
      dept_list = dummy_dept_list;
    }
    showElement('departments');
    for(var dept in dept_list){
        var dept_line= "<li class=\'bold\'><a onClick=\"loadCourses(\'" + dept_list[dept] + "\')\" class=\'waves-effect waves-teal\'>" + dept_list[dept] + "</a></li>"
        $('#departments #nav-mobile').append(dept_line);
    }
}

function loadCourses(department){
  //do some logic to get list of courses for given department
  var course_list;
  if(TEST_MODE){
    course_list = dummy_course_list;
  }
  showElement('courses');
  for(var course in course_list){
      var course_line= "<li class=\'bold\'><a onClick=\"loadGroups(\'" + course_list[course] + "\')\" class=\'waves-effect waves-teal\'>" + course_list[course] + "</a></li>"
      $('#courses #nav-mobile').append(course_line);
  }
}

function loadGroups(course){
	  //do some logic to get list of groups for given course
		
		var settings = {
				  "async": true,
				  "crossDomain": true,
				  "url": "/getgroupbycourse?courseID=" + course,
				  "method": "GET",
				  "headers": {
				    "cache-control": "no-cache",
				    "postman-token": "fb38c742-ab74-18f1-d76d-1f3d4560f8ab"
				  }
				}

		$.ajax(settings).done(function (response) {
			var group_list = response;
			  showElement('groups');
			  for(var group in group_list){
			      var group_uid = group_list[group].uid;
			      var group_icon_url = group_list[group].icon_url;
			      var group_name = group_list[group].name;
			      var group_size = group_list[group].size;
			      var group_purpose = group_list[group].purpose;
			      var group_join_leave = group_list[group].is_member ? 'Leave' : 'Join';
			      var group_line = "<li onClick=\"showGroupInfo(\'" + group_uid + "\')\" alt class=\'collection-item avatar waves-effect waves-teal z-depth-2\'>";
			      group_line += "<img src=\"" + group_icon_url + "\" class=\"circle group_icon\">";
			      group_line +=  "<div class=\"study_budy_info\"><span class=\"title\">" + group_name + "</span>";
			      group_line +=  "<p>" + group_size + " members <br>" + group_purpose + "</p></div>";
			      group_line +=  "<a href=\"#!\" class=\"group_joinORleave\"><p>" + group_join_leave + "<br> Group </p></a></li>";
			      $('#groups #nav-mobile #collection').append(group_line);
			  }
		});
		  
	}


function loadGroupInfo(uid){
  //do some logic to get group information for given uid
  var group
  if(TEST_MODE){
    group = dummy_group;
  }
  var group_icon_url = group.icon_url;
  var group_name = group.name;
  var group_join_leave = group.is_member;
  var group_purpose = group.purpose;
  var group_new_notifications = 4;
  var group_new_messages = 3;
  var group_events = 5;
  showElement("group_info");
  $('#group_info #nav-mobile img')[0].src = group_icon_url;
  $('#group_info #nav-mobile #name')[0].textContent = group_name;
  $('#group_info #nav-mobile #purpose')[0].textContent = group_purpose;
  $('#group_info #nav-mobile #joinleavebtn')[0].remove();
  if(!group_join_leave){
    $('#group_info #nav-mobile #notifications')[0].style.display = "none";
    $('#group_info #nav-mobile #messages')[0].style.display = "none";
    $('#group_info #nav-mobile #events')[0].style.display = "none";
    $('#group_info #nav-mobile #settings')[0].style.display = "none";
    var btn = "<li id=\"joinleavebtn\"><a class=\"waves-effect waves-light btn\">Join Group</a></li>";
    $('#group_info #nav-mobile').append(btn);
  }else{
    $('#group_info #nav-mobile #notifications')[0].style.display = "flex";
    $('#group_info #nav-mobile #notifications #badge')[0].textContent = group_new_notifications;
    $('#group_info #nav-mobile #messages')[0].style.display = "flex";
    $('#group_info #nav-mobile #messages #badge')[0].textContent = group_new_messages;
    $('#group_info #nav-mobile #events')[0].style.display = "flex";
    $('#group_info #nav-mobile #events #badge')[0].textContent = group_events;
    $('#group_info #nav-mobile #settings')[0].style.display = "flex";
    var btn = "<li id=\"joinleavebtn\"><a class=\"waves-effect waves-light btn\">Leave Group</a></li>";
    $('#group_info #nav-mobile').append(btn);
  }

}

function loadNotifications(uid){
  //do some logic to get list of notifications for given group
  var notifications_list;
  if(TEST_MODE){
    //TODO: notifications_list = dummy_notifications_list;
  }
  showElement('group_notification_list');
  for(var notification in notifications_list){
      //TODO: eventually
  }
}

function loadEvents(uid){
  //do some logic to get list of events for given group
  var event_list;
  if(TEST_MODE){
    event_list = dummy_event_list;
  }
  showElement('group_event_list');
  for(var event_item in event_list){
      var event_id = event_list[event_item].id;
      var event_name = event_list[event_item].name;
      var event_description = event_list[event_item].description;
      //var group_icon_url = event_list[group].creator;
      var event_date = event_list[event_item].date;
      var event_time= event_list[event_item].time;
      var event_location = event_list[event_item].location;
      //var group_purpose = event_list[group].duration;

      var event_line = "<li><div class=\"card teal darken-2\" style=\"margin: 5px; line-height:initial;\">";
      event_line += "<div class=\"card-content white-text\"><span class=\"card-subtitle\">";
      event_line += event_name + "</p><p><b>Time: </b>";
      event_line += event_date + "at" + event_time + "</br><b>Duration: </b>";
      event_line += "3 hours" + "</br><b>Location: </b>";
      event_line += event_location + "</p></div><div class=\"card-action\"><a onClick=\"\" >";
      event_line += "Leave" + "</a></div></div></li>"
      $('#group_event_list #nav-mobile').append(event_line);
  }
}


////////////Test Functions//////////////////////

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
  loadGroups(null);
}

function testLoadGroupInfo(){
  TEST_MODE = true;
  loadGroupInfo(null);
}

function testLoadEvents(){
  TEST_MODE = true;
  loadEvents(null);
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
