package com.foxminded.university.dao.Implementation;

import com.foxminded.university.dao.AbstractDao;
import com.foxminded.university.model.Student;
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
public class StudentDaoImpl extends AbstractDao {

    private static final Logger log = LoggerFactory.getLogger(StudentDaoImpl.class);

    private JdbcTemplate template;

    private static final String SAVE = "INSERT INTO students (first_name, last_name, group_id) VALUES (?,?,?)";
    private static final String REMOVE = "DELETE FROM students WHERE student_id =?";
    private static final String GET_ONE = "SELECT student_id, first_name, last_name FROM students WHERE student_id = ?";
    private static final String UPDATE = "UPDATE students SET first_name = ?, last_name =?, group_id=? WHERE student_id = ?";
    private static final String GET_ALL = "SELECT student_id, first_name, last_name FROM students";
    private static final String REMOVE_ALL = "DELETE FROM students";

    public StudentDaoImpl() {
    }

    @Autowired
    public StudentDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    @Override
    protected Object doSave(Object object) {
        Student student = (Student) object;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int insert = template.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(SAVE, new String[]{"student_id"});
                    resultSet.setString(1, student.getFirstName());
                    resultSet.setString(2, student.getLastName());
                    resultSet.setInt(3, student.getGroup().getGroupId());
                    return resultSet;
                },
                keyHolder);
        student.setStudentId((Integer) keyHolder.getKey());
        if (insert == 1) {
            log.info("New Student Created with id: {} ", student.getStudentId());
        }
        return student;
    }

    @Override
    protected void doRemoveAll() {
        template.update(REMOVE_ALL);
    }

    @Override
    protected void doRemove(Object object) {
        Student student = (Student) object;
        int delete = template.update(REMOVE, student.getStudentId());
        if (delete == 1) {
            log.info("Student Deleted with id : {}", student.getStudentId());
        }
    }


    @Override
    protected Optional doGetOne(int id) {
        Student student = null;
        try {

            student = template.queryForObject(GET_ONE, new BeanPropertyRowMapper<>(Student.class), id);
        } catch (DataAccessException ex) {
            System.out.println("Student not found with id: " + id);
        }
        return Optional.ofNullable(student);
    }


    @Override
    protected void doUpdate(Object object) {
        Student student = (Student) object;
        int update = template.update(
                UPDATE, student.getFirstName(), student.getLastName(), student.getGroup().getGroupId(), student.getStudentId());
        if (update == 1) {
            log.info("Student Updated with id: {}", student.getStudentId());
        }
    }

    @Override
    protected List doGetAll() {
        return template.query(GET_ALL, new BeanPropertyRowMapper<>(Student.class));
    }
}
