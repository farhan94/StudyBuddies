package com.recursivebogosort.studybuddies.entities;

import com.googlecode.objectify.annotation.Subclass;

/**
 * Created by ryan on 11/7/16.
 */

@Subclass
public class GroupOwner extends GroupMember {
    //Any owner specific properties and functions

    protected GroupOwner() { super(); }

    public GroupOwner(StudyBuddiesUser user, Group group) {
        super(user,group);
    }



}
