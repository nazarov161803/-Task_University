package com.foxminded.university.dao;

import com.foxminded.university.config.Config;
import com.foxminded.university.model.Course;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;
import com.foxminded.university.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class TeacherTest {

    @Qualifier("teacherDaoImpl")
    @Autowired
    private Dao teacherDao;


    private final Teacher teacherOne = new Teacher("Gastón", " Enya");
    private final Teacher teacherTwo = new Teacher("Wybert", " Przemyna");
    private final Teacher teacherThree = new Teacher("Walter", "Ben");
    private final Teacher teacherFour = new Teacher("Prem", "Mikalay");


    @BeforeEach
    void setUp() {
        teacherDao.removeAll();
        teacherDao.save(teacherOne);
        teacherDao.save(teacherTwo);
        teacherDao.save(teacherThree);
        teacherDao.save(teacherFour);

    }

    @Test
    public void listStudents_ShouldReturnAllCourses() {
        List<Teacher> teachers = teacherDao.getAll();
        assertEquals(4, teachers.size());
    }

    @Test
    public void getStudentWithValidId_ShouldReturnStudent() {
        Optional<Teacher> teacher = teacherDao.getOne(teacherOne.getTeacherId());
        assertTrue(teacher.isPresent());
    }

    @Test
    public void getStudentWithInvalidId_ShouldReturnEmptyOptional() {
        Optional<Teacher> teacher = teacherDao.getOne(-1);
        assertFalse(teacher.isPresent());
    }

    @Test
    public void validStudent_ShouldBeCreated() {
        Teacher teacher = new Teacher("test_name", "test_surname");
        teacherDao.save(teacher);
        

        List<Teacher> teachers = teacherDao.getAll();
        System.out.println(teachers.get(4));
        assertEquals(5, teachers.size());
        assertEquals("test_name", teachers.get(4).getTeacherName());
        assertEquals("test_surname", teachers.get(4).getTeacherSurname());
    }

    @Test
    public void updateStudent_ShouldBeUpdated() {
        teacherOne.setTeacherName("test_name_for_test_method");
        teacherDao.update(teacherOne);
        assertEquals("test_name_for_test_method", teacherOne.getTeacherName());
    }

    @Test
    public void deleteStudent_ShouldRemoveCourse() {
        teacherDao.remove(teacherFour);
        List<Teacher> teachers = teacherDao.getAll();
        assertEquals(3, teachers.size());
    }
}
