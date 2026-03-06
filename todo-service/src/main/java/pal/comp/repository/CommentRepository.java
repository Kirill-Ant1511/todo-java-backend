package pal.comp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pal.comp.entity.CommentEntity;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
}
