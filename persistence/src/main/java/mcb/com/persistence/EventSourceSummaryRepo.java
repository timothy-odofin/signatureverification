package mcb.com.persistence;

import mcb.com.domain.dto.response.EventSourceSummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventSourceSummaryRepo  {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventSourceSummaryRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EventSourceSummaryResponse> getEventSourceSummary() {
        String sql = "SELECT MAX(id) AS id, " +
                "CASE " +
                "    WHEN verified IS NULL THEN 'Not yet verified' " +
                "    WHEN verified = 'Yes' THEN 'Signature OK' " +
                "    WHEN verified = 'No' THEN 'Signature not OK' " +
                "END AS label, " +
                "COUNT(*) AS frequency " +
                "FROM EVENT_SOURCE " +
                "GROUP BY label";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            EventSourceSummaryResponse dto = new EventSourceSummaryResponse();
            dto.setLabel(rs.getString("label"));
            dto.setFrequency(rs.getLong("frequency"));
            return dto;
        });
    }
}
