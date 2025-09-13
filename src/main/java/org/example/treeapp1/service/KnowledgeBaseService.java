package org.example.treeapp1.service;

import org.example.treeapp1.exception.BusinessException;

import java.util.List;

public interface KnowledgeBaseService
{
    KnowledgeBaseDTO createKnowledgeBase(KnowledgeBaseDTO.Create dto) throws BusinessException;

    KnowledgeBaseDTO getKnowledgeBaseById(Long id) throws BusinessException;

    List<KnowledgeBaseDTO> getAllKnowledgeBases() throws BusinessException;

    KnowledgeBaseDTO updateKnowledgeBase(KnowledgeBaseDTO.Update dto) throws BusinessException;

    void deleteKnowledgeBase(Long id) throws BusinessException;
}
    