package org.example.treeapp1.service.impl;

import org.example.treeapp1.model.KnowledgeBaseEntity;
import org.example.treeapp1.model.dto.KnowledgeBaseDTO;
import org.example.treeapp1.repository.KnowledgeBaseRepository;
import org.example.treeapp1.repository.NodeRepository;
import org.example.treeapp1.service.KnowledgeBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final KnowledgeBaseRepository knowledgeBaseRepository;
    private final NodeRepository nodeRepository;

    public KnowledgeBaseServiceImpl(KnowledgeBaseRepository knowledgeBaseRepository, NodeRepository nodeRepository) {
        this.knowledgeBaseRepository = knowledgeBaseRepository;
        this.nodeRepository = nodeRepository;
    }

    @Override
    public KnowledgeBaseEntity createKnowledgeBase(KnowledgeBaseDTO dto) {
        if (knowledgeBaseRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("知识库名称已存在: " + dto.getName());
        }

        KnowledgeBaseEntity knowledgeBaseEntity = new KnowledgeBaseEntity();
        knowledgeBaseEntity.setName(dto.getName());
        knowledgeBaseEntity.setDescription(dto.getDescription());

        return knowledgeBaseRepository.save(knowledgeBaseEntity);
    }

    @Override
    public KnowledgeBaseEntity getKnowledgeBaseById(Long id) throws ResourceNotFoundException {
        return knowledgeBaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("知识库不存在，ID: " + id));
    }

    @Override
    public KnowledgeBaseEntity getKnowledgeBaseByName(String name) throws ResourceNotFoundException {
        return knowledgeBaseRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("知识库不存在，名称: " + name));
    }

    @Override
    public List<KnowledgeBaseEntity> getAllKnowledgeBases() {
        return knowledgeBaseRepository.findAll();
    }

    @Override
    public KnowledgeBaseEntity updateKnowledgeBase(Long id, KnowledgeBaseDTO dto) throws ResourceNotFoundException {
        KnowledgeBaseEntity existing = getKnowledgeBaseById(id);

        if (!existing.getName().equals(dto.getName()) && knowledgeBaseRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("知识库名称已存在: " + dto.getName());
        }

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());

        return knowledgeBaseRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteKnowledgeBase(Long id) {
        // 先删除该知识库下的所有节点
        nodeRepository.deleteById(id);
        // 再删除知识库
        knowledgeBaseRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return knowledgeBaseRepository.existsByName(name);
    }
}
    