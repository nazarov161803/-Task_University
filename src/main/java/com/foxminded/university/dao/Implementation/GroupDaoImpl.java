package com.foxminded.university.dao.Implementation;

import com.foxminded.university.dao.AbstractDao;
import com.foxminded.university.model.Group;
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
public class GroupDaoImpl extends AbstractDao {

    private static final Logger log = LoggerFactory.getLogger(StudentDaoImpl.class);

    private JdbcTemplate template;

    private static final String SAVE = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String REMOVE = "DELETE FROM groups WHERE group_id =?";
    private static final String GET_ONE = "SELECT group_id, group_name FROM groups WHERE group_id = ?";
    private static final String UPDATE = "UPDATE groups SET group_name = ? WHERE group_id = ?";
    private static final String GET_ALL = "SELECT group_id, group_name FROM groups";
    private static final String REMOVE_ALL = "DELETE FROM groups";

    public GroupDaoImpl() {
    }

    @Autowired
    public GroupDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    @Override
    protected Object doSave(Object object) {
        Group group = (Group) object;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int insert = template.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(SAVE, new String[]{"group_id"});
                    resultSet.setString(1, group.getGroupName());
                    return resultSet;
                },
                keyHolder);
        group.setGroupId((Integer) keyHolder.getKey());
        if (insert == 1) {
            log.info("New Group Created with id: {} ", group.getGroupId());
        }
        return group;
    }

    @Override
    protected void doRemoveAll() {
        template.update(REMOVE_ALL);
    }

    @Override
    protected void doRemove(Object object) {
        Group group = (Group) object;
        int delete = template.update(REMOVE, group.getGroupId());
        if (delete == 1) {
            log.info("Group Deleted with id : {}", group.getGroupId());
        }
    }

    @Override
    protected Optional doGetOne(int id) {
        Group group = null;
        try {
            group = template.queryForObject(GET_ONE, new BeanPropertyRowMapper<>(Group.class), id);
        } catch (DataAccessException ex) {
            System.out.println("Group not found with id: " + id);
        }
        return Optional.ofNullable(group);
    }

    @Override
    protected void doUpdate(Object object) {
        Group group = (Group) object;
        int update = template.update(
                UPDATE, group.getGroupName(), group.getGroupId());
        if (update == 1) {
            log.info("Group Updated with id: {}", group.getGroupId());
        }
    }

    @Override
    protected List doGetAll() {
        return template.query(GET_ALL, new BeanPropertyRowMapper<>(Group.class));
    }
}
