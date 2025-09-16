package org.example.treeapp1.infrastructure.persistence;

import org.example.treeapp1.domain.knowledgebase.model.KnowledgeBase;
import org.example.treeapp1.domain.knowledgebase.repository.KnowledgeBaseRepository;
import org.example.treeapp1.infrastructure.mapper.KnowledgeBaseMapper;
import org.example.treeapp1.infrastructure.persistence.jpa.repository.JpaKnowledgeBaseEntity;
import org.example.treeapp1.infrastructure.persistence.jpa.repository.JpaKnowledgeBaseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class KnowledgeBaseRepositoryImpl implements KnowledgeBaseRepository
{
    private final JpaKnowledgeBaseRepository knowledgeBaseRepository;
    private final KnowledgeBaseMapper knowledgeBaseMapper;

    public KnowledgeBaseRepositoryImpl(JpaKnowledgeBaseRepository knowledgeBaseRepository,
            KnowledgeBaseMapper knowledgeBaseMapper)
    {
        this.knowledgeBaseRepository = knowledgeBaseRepository;
        this.knowledgeBaseMapper = knowledgeBaseMapper;
    }

    @Override
    public KnowledgeBase save(KnowledgeBase knowledgeBase)
    {
        JpaKnowledgeBaseEntity entity = knowledgeBaseMapper.toEntity(knowledgeBase);
        return knowledgeBaseMapper.toDomain(knowledgeBaseRepository.save(entity));
    }

    @Override
    public Optional<KnowledgeBase> findById(Long id)
    {
        return Optional.empty();
    }

    @Override
    public Optional<KnowledgeBase> findByName(String name)
    {
        return knowledgeBaseRepository.findByName(name).map(knowledgeBaseMapper::toDomain);
    }

    @Override
    public List<KnowledgeBase> findAll()
    {
        return List.of();
    }

    @Override
    public boolean existsByName(String name)
    {
        return knowledgeBaseRepository.existsByName(name);
    }

    @Override
    public void deleteById(Long id)
    {
        knowledgeBaseRepository.deleteById(id);
    }
}
