package org.example.treeapp1.service;

import org.example.treeapp1.model.KnowledgeBaseEntity;
import org.example.treeapp1.model.dto.KnowledgeBaseDTO;

import java.util.List;

public interface KnowledgeBaseService {
    KnowledgeBaseEntity createKnowledgeBase(KnowledgeBaseDTO dto);
    
    KnowledgeBaseEntity getKnowledgeBaseById(Long id);
    
    KnowledgeBaseEntity getKnowledgeBaseByName(String name);
    
    List<KnowledgeBaseEntity> getAllKnowledgeBases();
    
    KnowledgeBaseEntity updateKnowledgeBase(Long id, KnowledgeBaseDTO dto);
    
    void deleteKnowledgeBase(Long id);
    
    boolean existsByName(String name);
}
    