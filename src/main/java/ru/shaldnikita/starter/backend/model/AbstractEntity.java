package ru.shaldnikita.starter.backend.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    private LocalDateTime creationDate;
    private LocalDateTime lastChangedDate;


    public boolean isNew() {
        return id == null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(LocalDateTime lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        }

        return 31 + id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (id == null) {
            // New entities are only equal if the instance if the same
            return super.equals(other);
        }

        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractEntity)) {
            return false;
        }
        return id.equals(((AbstractEntity) other).id);
    }

}
