package org.example.treeapp1.controller;

import lombok.RequiredArgsConstructor;
import org.example.treeapp1.model.NodeEntity;
import org.example.treeapp1.service.NodeDTO;
import org.example.treeapp1.service.NodeService;
import org.example.treeapp1.service.NodeVO;
import org.example.treeapp1.service.impl.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge-bases/{knowledgeBaseId}/nodes")
@RequiredArgsConstructor
public class NodeController {

    private final NodeService nodeService;

    @PostMapping("/root")
    public ResponseEntity<NodeEntity> createRootNode(@PathVariable Long knowledgeBaseId) throws ResourceNotFoundException {
        NodeEntity rootNodeEntity = nodeService.createRootNode(knowledgeBaseId);
        return new ResponseEntity<>(rootNodeEntity, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<NodeEntity> createNode(
            @PathVariable Long knowledgeBaseId,
            @RequestBody NodeDTO dto) throws ResourceNotFoundException {
        NodeEntity nodeEntity = nodeService.createNode(knowledgeBaseId, dto);
        return new ResponseEntity<>(nodeEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NodeEntity> getNodeById(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(nodeService.getNodeById(id));
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<NodeEntity>> getChildren(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long parentId) {
        return ResponseEntity.ok(nodeService.getChildren(knowledgeBaseId, parentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NodeEntity> updateNode(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long id,
            @RequestBody NodeDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(nodeService.updateNode(knowledgeBaseId, id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long id) throws ResourceNotFoundException {
        nodeService.deleteNode(knowledgeBaseId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tree")
    public ResponseEntity<NodeVO> getTree(@PathVariable Long knowledgeBaseId) throws ResourceNotFoundException {
        return ResponseEntity.ok(nodeService.getTree(knowledgeBaseId));
    }

    @GetMapping("/subtree/{nodeId}")
    public ResponseEntity<NodeVO> getSubTree(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long nodeId) throws ResourceNotFoundException {
        return ResponseEntity.ok(nodeService.getSubTree(knowledgeBaseId, nodeId));
    }
}
    