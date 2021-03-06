package img.imaginary.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import img.imaginary.service.entity.Subject;

@Component
public class SubjectMapper implements RowMapper<Subject> {

    @Override
    public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Subject(rs.getInt("subject_id"), rs.getString("name"), rs.getString("description"));
    }
}
