package img.imaginary.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import img.imaginary.service.entity.Group;
import img.imaginary.service.entity.Student;

@Component
public class GroupResultSetExtractor implements ResultSetExtractor<List<Group>> {

    private RowMapper<Student> studentMapper;
    
    @Autowired
    public GroupResultSetExtractor(@Qualifier("studentMapper") RowMapper<Student> studentMapper) {
        this.studentMapper = studentMapper;
    }
    
    @Override
    public List<Group> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Group> groups = new ArrayList<>();
        Group nextGroup = new Group();
        if (rs.next()) {
            nextGroup = mapToGroup(rs);
            addStudent(rs, nextGroup);
            groups.add(nextGroup);
        }
        while (rs.next()) {
            int groupId = rs.getInt("group_id");
            if (nextGroup.getGroupId() != groupId) {
                nextGroup = mapToGroup(rs);
                groups.add(nextGroup);
            }
            addStudent(rs, nextGroup);
        }
        return groups;
    }

    private Group mapToGroup(ResultSet rs) throws SQLException {
        return new Group(rs.getInt("group_id"), rs.getString("group_name"), new ArrayList<>(),
                rs.getString("specialty"));
    }

    private void addStudent(ResultSet rs, Group group) throws SQLException {
        if (rs.getInt("student_id") != 0) {
            group.addStudent(studentMapper.mapRow(rs, 0));
        }
    }
}
