package org.example.treeapp1.repository;

import org.example.treeapp1.model.KnowledgeBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBaseEntity, Long> {
    Optional<KnowledgeBaseEntity> findByName(String name);
    boolean existsByName(String name);
}
    