package com.foxminded.university.dao;

import com.foxminded.university.config.Config;
import com.foxminded.university.model.Group;
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
public class GroupTest {


    @Qualifier("groupDaoImpl")
    @Autowired
    private Dao groupDao;


    private final Group groupOne = new Group("AA-01");
    private final Group groupTwo = new Group("BB-01");
    private final Group groupThree = new Group("CC-01");
    private final Group groupFour = new Group("DD-01");


    @BeforeEach
    void setUp() {
        groupDao.removeAll();
        groupDao.save(groupOne);
        groupDao.save(groupTwo);
        groupDao.save(groupThree);
        groupDao.save(groupFour);

    }

    @Test
    public void listStudents_ShouldReturnAllCourses() {
        List<Group> groups = groupDao.getAll();
        assertEquals(4, groups.size());
    }

    @Test
    public void getStudentWithValidId_ShouldReturnStudent() {
        Optional<Group> group = groupDao.getOne(groupOne.getGroupId());
        assertTrue(group.isPresent());
    }

    @Test
    public void getStudentWithInvalidId_ShouldReturnEmptyOptional() {
        Optional<Group> group = groupDao.getOne(-1);
        assertFalse(group.isPresent());
    }

    @Test
    public void validStudent_ShouldBeCreated() {
        Group group = new Group("test_name");
        groupDao.save(group);

        List<Group> groups = groupDao.getAll();
        assertEquals(5, groups.size());
        assertEquals("test_name", groups.get(4).getGroupName());


    }

    @Test
    public void updateStudent_ShouldBeUpdated() {
        groupOne.setGroupName("test_name_for_test_method");
        groupDao.update(groupOne);
        assertEquals("test_name_for_test_method", groupOne.getGroupName());
    }

    @Test
    public void deleteStudent_ShouldRemoveCourse() {
        groupDao.remove(groupFour);
        List<Group> groups = groupDao.getAll();
        assertEquals(3, groups.size());
    }
}
