package com.foxminded.university.model;

import java.util.List;

public class Lesson {
    private int lessonId;
    private Teacher teacherId;
    private List<Group> groups;
    private ClassRoom classRoom;
    private TimeLesson time;

    public Lesson() {
    }

    public Lesson(Teacher teacherId, ClassRoom classRoom, TimeLesson time) {
        this.teacherId = teacherId;
        this.classRoom = classRoom;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", teacherId=" + teacherId +
                ", groups=" + groups +
                ", classRoom=" + classRoom +
                ", time=" + time +
                '}';
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public Teacher getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Teacher teacherId) {
        this.teacherId = teacherId;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public TimeLesson getTime() {
        return time;
    }

    public void setTime(TimeLesson time) {
        this.time = time;
    }
}
