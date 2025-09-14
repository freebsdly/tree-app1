package org.example.treeapp1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.treeapp1.exception.BusinessException;
import org.example.treeapp1.service.KnowledgeBaseDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "KnowledgeBase API")
public interface KnowledgeBaseDoc
{
    @Operation(summary = "Create a new knowledge base")
    ApiBody<KnowledgeBaseDTO> createKnowledgeBase(KnowledgeBaseDTO.Create dto) throws BusinessException;

    @Operation(summary = "Get a knowledge base by ID")
    ApiBody<KnowledgeBaseDTO> getKnowledgeBaseById(Long id) throws BusinessException;

    @Operation(summary = "Get all knowledge bases")
    ApiBody<List<KnowledgeBaseDTO>> getAllKnowledgeBases() throws BusinessException;

    @Operation(summary = "Update a knowledge base")
    ApiBody<KnowledgeBaseDTO> updateKnowledgeBase(Long id, @RequestBody KnowledgeBaseDTO.Update dto)
            throws BusinessException;

    @Operation(summary = "Delete a knowledge base")
    ApiBody<Void> deleteKnowledgeBase(Long id) throws BusinessException;
}
