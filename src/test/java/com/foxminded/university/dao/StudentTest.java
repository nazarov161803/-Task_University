package com.foxminded.university.dao;

import com.foxminded.university.config.Config;
import com.foxminded.university.model.Course;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;
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
public class StudentTest {

    @Qualifier("studentDaoImpl")
    @Autowired
    private Dao studentDao;

    private final Group group = new Group( "AA-01");

    private final Student studentOne = new Student("Bill", "Jordan", group);
    private final Student studentTwo = new Student("Bob", "Kelvin", group);
    private final Student studentThree = new Student("Greg", "Marley", group);
    private final Student studentFour = new Student("Tim", "Rot", group);


    @BeforeEach
    void setUp() {
        studentDao.removeAll();
        studentDao.save(studentOne);
        studentDao.save(studentTwo);
        studentDao.save(studentThree);
        studentDao.save(studentFour);

    }

    @Test
    public void listStudents_ShouldReturnAllCourses() {
        List<Course> courses = studentDao.getAll();
        assertEquals(4, courses.size());
    }

    @Test
    public void getStudentWithValidId_ShouldReturnStudent() {
        Optional<Student> student = studentDao.getOne(studentOne.getStudentId());
        assertTrue(student.isPresent());
    }

    @Test
    public void getStudentWithInvalidId_ShouldReturnEmptyOptional() {
        Optional<Course> course = studentDao.getOne(-1);
        assertFalse(course.isPresent());
    }

    @Test
    public void validStudent_ShouldBeCreated() {
        Student student = new Student("test_name", "test_surname", group);
        studentDao.save(student);

        List<Student> students = studentDao.getAll();
        assertEquals(5, students.size());
        assertEquals("test_name", students.get(4).getFirstName());
        assertEquals("test_surname", students.get(4).getLastName());

    }

    @Test
    public void updateStudent_ShouldBeUpdated() {
        studentOne.setFirstName("test_name_for_test_method");
        studentDao.update(studentOne);
        assertEquals("test_name_for_test_method", studentOne.getFirstName());
    }

    @Test
    public void deleteStudent_ShouldRemoveCourse() {
        studentDao.remove(studentFour);
        List<Student> students = studentDao.getAll();
        assertEquals(3, students.size());
    }
}
