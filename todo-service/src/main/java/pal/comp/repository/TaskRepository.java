package pal.comp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pal.comp.entity.TaskEntity;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findByOwnerId(UUID ownerId);
}
