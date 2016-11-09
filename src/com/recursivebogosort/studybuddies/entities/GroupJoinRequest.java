package com.recursivebogosort.studybuddies.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.EntitySubclass;
import com.recursivebogosort.studybuddies.entities.Group;
import com.recursivebogosort.studybuddies.entities.GroupMember;
import com.recursivebogosort.studybuddies.entities.StudyBuddiesUser;

/**
 * Created by ryan on 11/7/16.
 */


@EntitySubclass
public class GroupJoinRequest extends GroupMember {

    protected GroupJoinRequest(){ super(); }

    public GroupJoinRequest(Ref<StudyBuddiesUser> user, Ref<Group> group) {
        super(user, group);

    }


}
