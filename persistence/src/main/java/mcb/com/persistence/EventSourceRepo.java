package mcb.com.persistence;

import mcb.com.domain.entity.EventSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface EventSourceRepo extends JpaRepository<EventSource, Long> {
    Optional<EventSource> findByPid(UUID pid);
    @Query("select distinct (st.transactionCurrency) from EventSource st")
    Set<String> listCurrencies();
}
