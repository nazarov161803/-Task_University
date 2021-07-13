package com.foxminded.university.dao;

import com.foxminded.university.config.Config;
import com.foxminded.university.model.Course;
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
public class CourseTest {

    @Qualifier("courseDaoImpl")
    @Autowired
    private Dao courseDao;

    private final Course courseSwim = new Course("Swim", "This course about swimming");
    private final Course courseFight = new Course("Fight", "This course about fighting");
    private final Course courseShoot = new Course("Shoot", "This course about shooting");
    private final Course courseComputer = new Course("Computer", "This course about computer");


    @BeforeEach
    void setUp() {
        courseDao.removeAll();
        courseDao.save(courseSwim);
        courseDao.save(courseFight);
        courseDao.save(courseShoot);
        courseDao.save(courseComputer);


    }

    @Test
    public void listCourses_ShouldReturnAllCourses() {
        List<Course> courses = courseDao.getAll();
        assertEquals(4, courses.size());
    }

    @Test
    public void getCourseWithValidId_ShouldReturnCourse() {
        Optional<Course> course = courseDao.getOne(courseSwim.getCourseId());
        assertTrue(course.isPresent());
    }

    @Test
    public void getCourseWithInvalidId_ShouldReturnEmptyOptional() {
        Optional<Course> course = courseDao.getOne(99);
        assertFalse(course.isPresent());
    }

    @Test
    public void validCourse_ShouldBeCreated() {
        Course course = new Course("test_name", "test_desc");
        courseDao.save(course);

        List<Course> courses = courseDao.getAll();
        assertEquals(5, courses.size());
        assertEquals("test_name", courses.get(4).getCourseName());
        assertEquals("test_desc", courses.get(4).getCourseDescription());

    }

    @Test
    public void updateCourse_ShouldBeUpdated() {
        courseSwim.setCourseName("test_name_for_test_method");
        courseDao.update(courseSwim);
        assertEquals("test_name_for_test_method", courseSwim.getCourseName());
    }

    @Test
    public void deleteCourse_ShouldRemoveCourse() {
        courseDao.remove(courseFight);
        List<Course> courses = courseDao.getAll();
        assertEquals(3, courses.size());
    }
}
