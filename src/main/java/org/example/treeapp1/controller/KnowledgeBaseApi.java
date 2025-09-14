package org.example.treeapp1.controller;

import org.example.treeapp1.exception.BusinessException;
import org.example.treeapp1.service.KnowledgeBaseDTO;
import org.example.treeapp1.service.KnowledgeBaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge-bases")
public class KnowledgeBaseApi implements KnowledgeBaseDoc
{

    private final KnowledgeBaseService knowledgeBaseService;

    public KnowledgeBaseApi(KnowledgeBaseService knowledgeBaseService)
    {
        this.knowledgeBaseService = knowledgeBaseService;
    }

    @Override
    @PostMapping
    public ApiBody<KnowledgeBaseDTO> createKnowledgeBase(@RequestBody KnowledgeBaseDTO.Create dto)
            throws BusinessException
    {
        KnowledgeBaseDTO knowledgeBase = knowledgeBaseService.createKnowledgeBase(dto);
        return ApiBody.success(knowledgeBase);
    }

    @Override
    @GetMapping("/{id}")
    public ApiBody<KnowledgeBaseDTO> getKnowledgeBaseById(@PathVariable Long id) throws BusinessException
    {
        return ApiBody.success(knowledgeBaseService.getKnowledgeBaseById(id));
    }

    @Override
    @GetMapping
    public ApiBody<List<KnowledgeBaseDTO>> getAllKnowledgeBases() throws BusinessException
    {
        return ApiBody.success(knowledgeBaseService.getAllKnowledgeBases());
    }

    @Override
    @PutMapping("/{id}")
    public ApiBody<KnowledgeBaseDTO> updateKnowledgeBase(
            @PathVariable Long id,
            @RequestBody KnowledgeBaseDTO.Update dto) throws BusinessException
    {
        dto.setId(id);
        return ApiBody.success(knowledgeBaseService.updateKnowledgeBase(dto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ApiBody<Void> deleteKnowledgeBase(@PathVariable Long id) throws BusinessException
    {
        knowledgeBaseService.deleteKnowledgeBase(id);
        return ApiBody.success(null);
    }
}
    