package mcb.com.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;
    @CreationTimestamp
    @Column(name = "dateCreated")
    protected LocalDateTime dateCreated;
    @Column(name = "lastModified")
    protected LocalDateTime lastModified;
     @Column(name = "pid", updatable = false, nullable = false, columnDefinition =
     "VARCHAR(36)")
     @Type(type = "uuid-char")
     private UUID pid;

}
