package org.example.treeapp1.service;

import org.example.treeapp1.exception.ResourceNotFoundException;

import java.util.List;

public interface KnowledgeBaseService
{
    KnowledgeBaseDTO createKnowledgeBase(KnowledgeBaseDTO dto);

    KnowledgeBaseDTO getKnowledgeBaseById(Long id) throws ResourceNotFoundException;

    KnowledgeBaseDTO getKnowledgeBaseByName(String name) throws ResourceNotFoundException;

    List<KnowledgeBaseDTO> getAllKnowledgeBases();

    KnowledgeBaseDTO updateKnowledgeBase(Long id, KnowledgeBaseDTO dto) throws ResourceNotFoundException;

    void deleteKnowledgeBase(Long id) throws ResourceNotFoundException;

    boolean existsByName(String name);
}
    