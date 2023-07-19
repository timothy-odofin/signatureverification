package mcb.com.api.controller;

import mcb.com.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

public abstract class BaseControllerTest {
    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public JpaProperties jpaProperties;

    @MockBean
    public DataSource dataSource;
    @MockBean
    public UsersRepo usersRepo;
    @MockBean
    public UserRolesRepo userRolesRepo;
    @MockBean
    public RolesRepo rolesRepo;
    @MockBean
    public EventSourceRepo eventSourceRepo;
    @MockBean
    public EventSourceSummaryRepo eventSourceSummaryRepo;
    @MockBean
    public RefreshTokenRepo refreshTokenRepo;
}
