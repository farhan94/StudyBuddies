package com.recursivebogosort.studybuddies.servlets;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.recursivebogosort.studybuddies.entities.Course;
import com.recursivebogosort.studybuddies.entities.Group;
import com.recursivebogosort.studybuddies.entities.StudyBuddiesUser;
import com.recursivebogosort.studybuddies.entities.University;
import com.recursivebogosort.studybuddies.entities.Department;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by ryan on 11/17/16.
 */
public class GetCoursesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cIDString = req.getParameter("departmentID");
        long cID = Long.parseLong(cIDString);
        Department dept = ofy().load().type(Department.class).id(cID).getValue();
        Collection<Course> courses = dept.getCourses();
        Object[] c = courses.toArray();
        JSONArray ar = new JSONArray();
        for(int i = 0; i < c.length; i++){
            Course crs = (Course) c[i];
            JSONObject jo = new JSONObject();
            try {
                jo.put("uid", crs.getId().toString());
                jo.put("course_name", crs.getCourseName());
                jo.put("prof_name", crs.getProfessor());
                jo.put("uni_name", crs.getUniversity().getName());
                jo.put("course_id", crs.getCourseId());
                //jo.put("size", grp.getCurrentSize());
                //jo.put("purpose", grp.getGroupDescription());
                //jo.put("isMember", true);
                //jo.put("icon_url","google.com");

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ar.put(jo);
        }
//		Ref<StudyBuddiesUser> sbuRef = ofy().load().type(StudyBuddiesUser.class).id(id);
        //JSONObject jo = new JSONObject(groups.toArray());

//		try {
//			jo.put("Name", sbu.getName());
//			jo.put("Email", sbu.getEmail());
//			jo.put("PhoneNumber", sbu.getPhoneNumber());
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


        resp.setContentType("application/json");

        resp.getWriter().print(ar);
        //}
        //super.doGet(req, resp);


    }


}
