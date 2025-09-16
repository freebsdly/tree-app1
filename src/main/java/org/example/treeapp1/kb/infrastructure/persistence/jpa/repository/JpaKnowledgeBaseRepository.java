package org.example.treeapp1.kb.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaKnowledgeBaseRepository extends JpaRepository<JpaKnowledgeBaseEntity, Long>,
        QuerydslPredicateExecutor<JpaKnowledgeBaseEntity>
{

    boolean existsByName(String name);

    Optional<JpaKnowledgeBaseEntity> findByName(String name);
}
