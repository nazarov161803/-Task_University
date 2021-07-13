package com.foxminded.university.dao;

import com.foxminded.university.config.Config;
import com.foxminded.university.model.ClassRoom;
import com.foxminded.university.model.Lesson;
import com.foxminded.university.model.Teacher;
import com.foxminded.university.model.TimeLesson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class LessonTest {

    @Qualifier("lessonDaoImpl")
    @Autowired
    private Dao lessonDao;

    @Qualifier("teacherDaoImpl")
    @Autowired
    private Dao teacherDao;


    LocalDateTime dateTimeStartFirstLesson = LocalDateTime.of(2021, Month.JULY, 1, 8, 15);
    LocalDateTime dateTimeEndFirstLesson = LocalDateTime.of(2021, Month.JULY, 1, 10, 15);

    LocalDateTime dateTimeStartSecondLesson = LocalDateTime.of(2021, Month.JULY, 1, 10, 30);
    LocalDateTime dateTimeEndSecondLesson = LocalDateTime.of(2021, Month.JULY, 1, 12, 30);

    LocalDateTime dateTimeStartThirdLesson = LocalDateTime.of(2021, Month.JULY, 1, 13, 0);
    LocalDateTime dateTimeEndThirdLesson = LocalDateTime.of(2021, Month.JULY, 1, 15, 0);

    private final Teacher teacherOne = new Teacher("Gastón", " Enya");
    private final ClassRoom classRoom = new ClassRoom(113);


    private final Lesson lessonOne = new Lesson(teacherOne, classRoom, new TimeLesson(dateTimeStartFirstLesson, dateTimeEndFirstLesson));
    private final Lesson lessonTwo = new Lesson(teacherOne, classRoom, new TimeLesson(dateTimeStartSecondLesson, dateTimeEndSecondLesson));
    private final Lesson lessonThree = new Lesson(teacherOne, classRoom, new TimeLesson(dateTimeStartThirdLesson, dateTimeEndThirdLesson));


    @BeforeEach
    void setUp() {
        teacherDao.removeAll();
        teacherDao.save(teacherOne);

        lessonDao.removeAll();
        lessonDao.save(lessonOne);
        lessonDao.save(lessonTwo);
        lessonDao.save(lessonThree);

    }

    @Test
    public void listLessons_ShouldReturnAllLessons() {
        List<Lesson> lessons = lessonDao.getAll();
        assertEquals(3, lessons.size());
    }

    @Test
    public void getLessonWithValidId_ShouldReturnLesson() {
        Optional<Lesson> lesson = lessonDao.getOne(lessonOne.getLessonId());
        assertTrue(lesson.isPresent());
    }

    @Test
    public void getLessonWithInvalidId_ShouldReturnEmptyOptional() {
        Optional<Lesson> lesson = lessonDao.getOne(-1);
        assertFalse(lesson.isPresent());
    }

    @Test
    public void validLesson_ShouldBeCreated() {
        Lesson lesson = new Lesson(new Teacher("test", "testSur"), classRoom, new TimeLesson(dateTimeStartFirstLesson, dateTimeEndFirstLesson));
        lessonDao.save(lesson);

        List<Lesson> lessons = lessonDao.getAll();
        assertEquals(5, lessons.size());
        assertEquals("test", lesson.getTeacherId().getTeacherName());
        assertEquals("testSur", lesson.getTeacherId().getTeacherSurname());
    }

    @Test
    public void updateLesson_ShouldBeUpdated() {
        lessonOne.getClassRoom().setNumber(999);
        lessonDao.update(lessonOne);
        assertEquals(999, lessonOne.getClassRoom().getNumber());
    }

    @Test
    public void deleteLesson_ShouldRemoveLesson() {
        lessonDao.remove(lessonThree);
        List<Lesson> lessons = lessonDao.getAll();
        assertEquals(2, lessons.size());
    }
}
