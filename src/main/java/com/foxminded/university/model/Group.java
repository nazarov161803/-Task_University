package com.foxminded.university.model;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private int groupId;
    private String groupName;
    private List <Student> students;

    public Group() {
    }

    public Group(String groupName) {
        this.groupName = groupName;
        students = new ArrayList<>();
    }


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName;
    }
}
