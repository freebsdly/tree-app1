package org.example.treeapp1.controller;

import org.example.treeapp1.exception.ResourceNotFoundException;
import org.example.treeapp1.service.KnowledgeBaseDTO;
import org.example.treeapp1.service.KnowledgeBaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge-bases")
public class KnowledgeBaseController
{

    private final KnowledgeBaseService knowledgeBaseService;

    public KnowledgeBaseController(KnowledgeBaseService knowledgeBaseService)
    {
        this.knowledgeBaseService = knowledgeBaseService;
    }

    @PostMapping
    public ApiBody<KnowledgeBaseDTO> createKnowledgeBase(@RequestBody KnowledgeBaseDTO dto)
    {
        KnowledgeBaseDTO knowledgeBase = knowledgeBaseService.createKnowledgeBase(dto);
        return ApiBody.success(knowledgeBase);
    }

    @GetMapping("/{id}")
    public ApiBody<KnowledgeBaseDTO> getKnowledgeBaseById(@PathVariable Long id) throws ResourceNotFoundException
    {
        return ApiBody.success(knowledgeBaseService.getKnowledgeBaseById(id));
    }

    @GetMapping("/name/{name}")
    public ApiBody<KnowledgeBaseDTO> getKnowledgeBaseByName(@PathVariable String name) throws ResourceNotFoundException
    {
        return ApiBody.success(knowledgeBaseService.getKnowledgeBaseByName(name));
    }

    @GetMapping
    public ApiBody<List<KnowledgeBaseDTO>> getAllKnowledgeBases()
    {
        return ApiBody.success(knowledgeBaseService.getAllKnowledgeBases());
    }

    @PutMapping("/{id}")
    public ApiBody<KnowledgeBaseDTO> updateKnowledgeBase(
            @PathVariable Long id,
            @RequestBody KnowledgeBaseDTO dto) throws ResourceNotFoundException
    {
        return ApiBody.success(knowledgeBaseService.updateKnowledgeBase(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiBody<Void> deleteKnowledgeBase(@PathVariable Long id) throws ResourceNotFoundException
    {
        knowledgeBaseService.deleteKnowledgeBase(id);
        return ApiBody.success(null);
    }
}
    