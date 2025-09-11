package org.example.treeapp1.controller;

import lombok.AllArgsConstructor;
import org.example.treeapp1.model.KnowledgeBaseEntity;
import org.example.treeapp1.model.dto.KnowledgeBaseDTO;
import org.example.treeapp1.service.KnowledgeBaseService;
import lombok.RequiredArgsConstructor;
import org.example.treeapp1.service.impl.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge-bases")
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;

    public KnowledgeBaseController(KnowledgeBaseService knowledgeBaseService) {
        this.knowledgeBaseService = knowledgeBaseService;
    }

    @PostMapping
    public ResponseEntity<KnowledgeBaseEntity> createKnowledgeBase(@RequestBody KnowledgeBaseDTO dto) {
        KnowledgeBaseEntity knowledgeBaseEntity = knowledgeBaseService.createKnowledgeBase(dto);
        return new ResponseEntity<>(knowledgeBaseEntity, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeBaseEntity> getKnowledgeBaseById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(knowledgeBaseService.getKnowledgeBaseById(id));
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<KnowledgeBaseEntity> getKnowledgeBaseByName(@PathVariable String name) throws ResourceNotFoundException {
        return ResponseEntity.ok(knowledgeBaseService.getKnowledgeBaseByName(name));
    }
    
    @GetMapping
    public ResponseEntity<List<KnowledgeBaseEntity>> getAllKnowledgeBases() {
        return ResponseEntity.ok(knowledgeBaseService.getAllKnowledgeBases());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeBaseEntity> updateKnowledgeBase(
            @PathVariable Long id, 
            @RequestBody KnowledgeBaseDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(knowledgeBaseService.updateKnowledgeBase(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKnowledgeBase(@PathVariable Long id) {
        knowledgeBaseService.deleteKnowledgeBase(id);
        return ResponseEntity.noContent().build();
    }
}
    