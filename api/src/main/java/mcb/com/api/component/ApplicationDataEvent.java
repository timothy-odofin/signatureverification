package mcb.com.api.component;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mcb.com.api.utils.Constant;
import mcb.com.domain.entity.EventSource;
import mcb.com.domain.entity.Roles;
import mcb.com.domain.entity.UserRoles;
import mcb.com.domain.entity.Users;
import mcb.com.persistence.EventSourceRepo;
import mcb.com.persistence.RolesRepo;
import mcb.com.persistence.UserRolesRepo;
import mcb.com.persistence.UsersRepo;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationDataEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final UsersRepo usersRepo;
    private final RolesRepo rolesRepo;
    private final EventSourceRepo eventSourceRepo;
    private final UserRolesRepo userRolesRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Roles roles = new Roles();
        roles.setRoleName("ADMINISTRATOR");
        roles.setDescription("User with higher authority");
        roles.setPid(UUID.randomUUID());
        Roles role2 = new Roles();
        role2.setRoleName("USER");
        role2.setPid(UUID.randomUUID());
        role2.setDescription("Ordinary user with view only");

        Roles role3 = new Roles();
        role3.setRoleName("SUPER");
        role3.setPid(UUID.randomUUID());
        role3.setDescription("Overall boss");
        rolesRepo.saveAll(List.of(roles,  role3,role2));
        loadData();
    }
    private void loadData(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the JSON file from the classpath
            log.info("Loading event_source.json and user.json");
            ClassPathResource resource = new ClassPathResource("event_source.json");
            ClassPathResource userResource = new ClassPathResource("user.json");
            List<EventSource> eventSources = objectMapper.readValue(resource.getInputStream(),
                    new TypeReference<>() {});
            List<Users> userList = objectMapper.readValue(userResource.getInputStream(),
                    new TypeReference<>() {});
            log.info("Completed loading of event_source.json and user.json");
            log.info("Created respective data in the database");
            saveUser(userList);
            eventSourceRepo.saveAll(eventSources);
            log.info("Completed initialization of various tables with data read from the json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveUser(List<Users> users){
        for(Users user: users){
            user.setPassword(passwordEncoder.encode("test"));
            user = usersRepo.save(user);
            if(user.getAccountStatus().equals(Constant.HOD)){
                UserRoles userRoles = new UserRoles();
                userRoles.setPid(UUID.randomUUID());
                userRoles.setUser(user);
                userRoles.setRole(new Roles(1));
                UserRoles userRoles2 = new UserRoles();
                userRoles2.setPid(UUID.randomUUID());
                userRoles2.setUser(user);
                userRoles2.setRole(new Roles(2));
                userRolesRepo.saveAll(List.of(userRoles, userRoles2));
            }else{
                UserRoles userRoles = new UserRoles();
                userRoles.setPid(UUID.randomUUID());
                userRoles.setUser(user);
                userRoles.setRole(new Roles(3));
                userRolesRepo.save(userRoles);
            }
        }

    }
}
