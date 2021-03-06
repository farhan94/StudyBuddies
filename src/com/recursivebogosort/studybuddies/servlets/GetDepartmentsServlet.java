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
import com.recursivebogosort.studybuddies.entities.Department;
import com.recursivebogosort.studybuddies.entities.Group;
import com.recursivebogosort.studybuddies.entities.StudyBuddiesUser;
import com.recursivebogosort.studybuddies.entities.University;

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
public class GetDepartmentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cID = req.getParameter("universityID");
        
        University university = ofy().load().type(University.class).id(cID).getValue();
        Collection<Department> depts = university.get_departments();
        Object[] c = depts.toArray();
        JSONArray ar = new JSONArray();
        for(int i = 0; i < c.length; i++){
            Department crs = (Department) c[i];
            JSONObject jo = new JSONObject();
            try {
                jo.put("department_uid", crs.getId());
                jo.put("department_name", crs.getDepartmentName());
//                jo.put("prof_name", crs.getProfessor());
//                jo.put("uni_name", crs.getUniversity().getName());
//                jo.put("course_id", crs.getCourseId());
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
