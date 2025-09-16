package org.example.treeapp1.domain.knowledgebase.repository;

import org.example.treeapp1.domain.knowledgebase.model.KnowledgeBase;

import java.util.List;
import java.util.Optional;

/**
 * 知识库领域仓储接口，定义对知识库的持久化操作
 */
public interface KnowledgeBaseRepository
{
    KnowledgeBase save(KnowledgeBase knowledgeBase);

    Optional<KnowledgeBase> findById(Long id);

    Optional<KnowledgeBase> findByName(String name);

    List<KnowledgeBase> findAll();

    boolean existsByName(String name);

    void deleteById(Long id);
}
