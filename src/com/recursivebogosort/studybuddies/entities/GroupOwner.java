package com.recursivebogosort.studybuddies.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.EntitySubclass;


/**
 * Created by ryan on 11/7/16.
 */

@EntitySubclass
public class GroupOwner extends GroupMember {
    //Any owner specific properties and functions

    protected GroupOwner() { super(); }

    public GroupOwner(Ref<StudyBuddiesUser> user, Ref<Group> group) {
        super(user,group);
    }



}
