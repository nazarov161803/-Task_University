package com.foxminded.university.dao.Implementation;

import com.foxminded.university.dao.AbstractDao;
import com.foxminded.university.model.Student;
import com.foxminded.university.model.Teacher;
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
public class TeacherDaoImpl extends AbstractDao {

    private static final Logger log = LoggerFactory.getLogger(StudentDaoImpl.class);

    private JdbcTemplate template;

    private static final String SAVE = "INSERT INTO teachers (teacher_name, teacher_surname) VALUES (?,?)";
    private static final String REMOVE = "DELETE FROM teachers WHERE teacher_id =?";
    private static final String GET_ONE = "SELECT teacher_id, teacher_name, teacher_surname FROM teachers WHERE teacher_id = ?";
    private static final String UPDATE = "UPDATE teachers SET teacher_name = ?, teacher_surname =? WHERE teacher_id = ?";
    private static final String GET_ALL = "SELECT teacher_id, teacher_name, teacher_surname FROM teachers";
    private static final String REMOVE_ALL = "DELETE FROM teachers";

    public TeacherDaoImpl() {
    }

    @Autowired
    public TeacherDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    protected Object doSave(Object object) {
        Teacher teacher = (Teacher) object;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int insert = template.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(SAVE, new String[]{"teacher_id"});
                    resultSet.setString(1, teacher.getTeacherName());
                    resultSet.setString(2, teacher.getTeacherSurname());
                    return resultSet;
                },
                keyHolder);
        teacher.setTeacherId((Integer) keyHolder.getKey());
        if (insert == 1) {
            log.info("New Teacher Created with id: {} ", teacher.getTeacherId());
        }
        return teacher;
    }


    @Override
    protected void doRemoveAll() {
        template.update(REMOVE_ALL);
    }

    @Override
    protected void doRemove(Object object) {
        Teacher teacher = (Teacher) object;
        int delete = template.update(REMOVE, teacher.getTeacherId());
        if (delete == 1) {
            log.info("Teacher Deleted with id : {}", teacher.getTeacherId());
        }
    }

    @Override
    protected Optional doGetOne(int id) {
        Teacher teacher = null;
        try {

            teacher = template.queryForObject(GET_ONE, new BeanPropertyRowMapper<>(Teacher.class), id);
        } catch (DataAccessException ex) {
            System.out.println("Teacher not found with id: " + id);
        }
        return Optional.ofNullable(teacher);
    }

    @Override
    protected void doUpdate(Object object) {
        Teacher teacher = (Teacher) object;
        int update = template.update(
                UPDATE, teacher.getTeacherName(), teacher.getTeacherSurname(), teacher.getTeacherId());
        if (update == 1) {
            log.info("Teacher Updated with id: {}",teacher.getTeacherId());
        }
    }

    @Override
    protected List doGetAll() {
        return template.query(GET_ALL, new BeanPropertyRowMapper<>(Teacher.class));
    }
}
