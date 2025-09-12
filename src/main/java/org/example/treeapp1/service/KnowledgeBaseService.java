package org.example.treeapp1.service;

import org.example.treeapp1.model.KnowledgeBaseEntity;
import org.example.treeapp1.model.dto.KnowledgeBaseDTO;
import org.example.treeapp1.service.impl.ResourceNotFoundException;

import java.util.List;

public interface KnowledgeBaseService {
    KnowledgeBaseEntity createKnowledgeBase(KnowledgeBaseDTO dto);
    
    KnowledgeBaseEntity getKnowledgeBaseById(Long id) throws ResourceNotFoundException;
    
    KnowledgeBaseEntity getKnowledgeBaseByName(String name) throws ResourceNotFoundException;
    
    List<KnowledgeBaseEntity> getAllKnowledgeBases();
    
    KnowledgeBaseEntity updateKnowledgeBase(Long id, KnowledgeBaseDTO dto) throws ResourceNotFoundException;
    
    void deleteKnowledgeBase(Long id);
    
    boolean existsByName(String name);
}
    