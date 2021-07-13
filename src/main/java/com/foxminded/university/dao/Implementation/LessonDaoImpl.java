package com.foxminded.university.dao.Implementation;

import com.foxminded.university.dao.AbstractDao;
import com.foxminded.university.model.Lesson;
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
public class LessonDaoImpl extends AbstractDao {

    private static final Logger log = LoggerFactory.getLogger(LessonDaoImpl.class);

    private JdbcTemplate template;

    private static final String SAVE = "INSERT INTO lessons (teacher_id, class_room, time_start, time_end) VALUES (?,?,?,?)";
    private static final String REMOVE = "DELETE FROM lessons WHERE lesson_id =?";
    private static final String GET_ONE = "SELECT lesson_id, teacher_id, class_room, time_start, time_end FROM lessons WHERE lesson_id = ?";
    private static final String UPDATE = "UPDATE lessons SET teacher_id = ?, class_room = ?, time_start = ?, time_end = ? WHERE lesson_id = ?";
    private static final String GET_ALL = "SELECT lesson_id, teacher_id, class_room, time_start, time_end FROM lessons";
    private static final String REMOVE_ALL = "DELETE FROM lessons";

    public LessonDaoImpl() {
    }

    @Autowired
    public LessonDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    protected Object doSave(Object object) {
        Lesson lesson = (Lesson) object;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int insert = template.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(SAVE, new String[]{"lesson_id"});
                    resultSet.setInt(1, lesson.getTeacherId().getTeacherId());
                    resultSet.setInt(2, lesson.getClassRoom().getNumber());
                    resultSet.setObject(3, lesson.getTime().getStart());
                    resultSet.setObject(4, lesson.getTime().getEnd());
                    return resultSet;
                },
                keyHolder);
        lesson.setLessonId((Integer) keyHolder.getKey());
        if (insert == 1) {
            log.info("New Lesson Created with id: {} ", lesson.getLessonId());
        }
        return lesson;
    }

    @Override
    protected void doRemoveAll() {
        template.update(REMOVE_ALL);
    }

    @Override
    protected void doRemove(Object object) {
        Lesson lesson = (Lesson) object;
        int delete = template.update(REMOVE, lesson.getLessonId());
        if (delete == 1) {
            log.info("Lesson Deleted with id : {}", lesson.getLessonId());
        }
    }

    @Override
    protected Optional doGetOne(int id) {
        Lesson lesson = null;
        try {
            lesson = template.queryForObject(GET_ONE, new BeanPropertyRowMapper<>(Lesson.class), id);
        } catch (DataAccessException ex) {
            System.out.println("Lesson not found with id: " + id);
        }
        return Optional.ofNullable(lesson);
    }


    @Override
    protected void doUpdate(Object object) {
        Lesson lesson = (Lesson) object;
        int update = template.update(UPDATE,
                lesson.getTeacherId(), lesson.getClassRoom().getNumber(),
                lesson.getTime().getStart(), lesson.getTime().getEnd());
        if (update == 1) {
            log.info("Lesson Updated with id: {}", lesson.getLessonId());
        }
    }

    @Override
    protected List doGetAll() {
        return template.query(GET_ALL, new BeanPropertyRowMapper<>(Lesson.class));
    }
}
