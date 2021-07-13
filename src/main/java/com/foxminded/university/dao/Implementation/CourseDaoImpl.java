package com.foxminded.university.dao.Implementation;

import com.foxminded.university.dao.AbstractDao;
import com.foxminded.university.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseDaoImpl extends AbstractDao {

    private static final Logger log = LoggerFactory.getLogger(CourseDaoImpl.class);

    private JdbcTemplate template;

    private static final String SAVE = "INSERT INTO courses (course_name, course_description) VALUES (?,?)";
    private static final String REMOVE = "DELETE FROM courses where course_id =?";
    private static final String GET_ONE = "SELECT course_id, course_name, course_description FROM courses WHERE course_id = ?";
    private static final String UPDATE = "UPDATE courses set course_name = ?, course_description =? WHERE course_id = ?";
    private static final String GET_ALL = "SELECT course_id, course_name, course_description FROM courses";
    private static final String REMOVE_ALL = "DELETE from courses";


    public CourseDaoImpl() {
    }

    @Autowired
    public CourseDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    @Override
    protected Object doSave(Object object) {
        Course course = (Course) object;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int insert = template.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(SAVE, new String[]{"course_id"});
                    resultSet.setString(1, course.getCourseName());
                    resultSet.setString(2, course.getCourseDescription());
                    return resultSet;
                },
                keyHolder);
        course.setCourseId((Integer) keyHolder.getKey());
        if (insert == 1) {
            log.info("New Course Created with name: {} ", course.getCourseName());
        }
        return course;
    }


    @Override
    protected void doRemove(Object object) {
        Course course = (Course) object;
        int delete = template.update(REMOVE, course.getCourseId());
        if (delete == 1) {
            log.info("Course Deleted with id : {}", course.getCourseId());
        }
    }

    @Override
    protected Optional doGetOne(int id) {
        Course course = null;
        try {
            course = template.queryForObject(GET_ONE, new BeanPropertyRowMapper<>(Course.class), id);
        } catch (DataAccessException ex) {
            System.out.println("Course not found with id: " + id);
        }
        return Optional.ofNullable(course);
    }

    @Override
    protected void doUpdate(Object object) {
        Course course = (Course) object;
        int update = template.update(UPDATE, course.getCourseName(), course.getCourseDescription(), course.getCourseId());
        if (update == 1) {
            log.info("Course Updated with id: {}", course.getCourseId());
        }
    }

    @Override
    protected List<Course> doGetAll() {
        return template.query(GET_ALL, new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    protected void doRemoveAll() {
        template.update(REMOVE_ALL);
    }

    @Override
    public List<Course> getAll() {
        return template.query(GET_ALL, new BeanPropertyRowMapper<>(Course.class));
    }
}
