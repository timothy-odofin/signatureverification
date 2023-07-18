package mcb.com.persistence;

import mcb.com.domain.entity.EventSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EventSourceRepo extends JpaRepository<EventSource, Long> {
    Optional<EventSource> findByPid(UUID pid);
}
