package org.example.treeapp1.controller;

import lombok.RequiredArgsConstructor;
import org.example.treeapp1.exception.ResourceNotFoundException;
import org.example.treeapp1.service.NodeDTO;
import org.example.treeapp1.service.NodeService;
import org.example.treeapp1.service.NodeVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge-bases/{knowledgeBaseId}/nodes")
@RequiredArgsConstructor
public class NodeController
{

    private final NodeService nodeService;

    @PostMapping("/root")
    public ApiBody<NodeDTO> createRootNode(@PathVariable Long knowledgeBaseId) throws ResourceNotFoundException
    {
        NodeDTO rootNodeDTO = nodeService.createRootNode(knowledgeBaseId);
        return ApiBody.success(rootNodeDTO);
    }

    @PostMapping
    public ApiBody<NodeDTO> createNode(
            @PathVariable Long knowledgeBaseId,
            @RequestBody NodeDTO dto) throws ResourceNotFoundException
    {
        NodeDTO nodeDTO = nodeService.createNode(knowledgeBaseId, dto);
        return ApiBody.success(nodeDTO);
    }

    @GetMapping("/{id}")
    public ApiBody<NodeDTO> getNodeById(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long id) throws ResourceNotFoundException
    {
        NodeDTO nodeDTO = nodeService.getNodeById(id);
        return ApiBody.success(nodeDTO);
    }

    @GetMapping("/parent/{parentId}")
    public ApiBody<List<NodeDTO>> getChildren(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long parentId)
    {
        List<NodeDTO> children = nodeService.getChildren(knowledgeBaseId, parentId);
        return ApiBody.success(children);
    }

    @PutMapping("/{id}")
    public ApiBody<NodeDTO> updateNode(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long id,
            @RequestBody NodeDTO dto) throws ResourceNotFoundException
    {
        NodeDTO nodeDTO = nodeService.updateNode(knowledgeBaseId, id, dto);
        return ApiBody.success(nodeDTO);
    }

    @DeleteMapping("/{id}")
    public ApiBody<Void> deleteNode(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long id) throws ResourceNotFoundException
    {
        nodeService.deleteNode(knowledgeBaseId, id);
        return ApiBody.success(null);
    }

    @GetMapping("/tree")
    public ResponseEntity<NodeVO> getTree(@PathVariable Long knowledgeBaseId) throws ResourceNotFoundException
    {
        return ResponseEntity.ok(nodeService.getTree(knowledgeBaseId));
    }

    @GetMapping("/subtree/{nodeId}")
    public ResponseEntity<NodeVO> getSubTree(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long nodeId) throws ResourceNotFoundException
    {
        return ResponseEntity.ok(nodeService.getSubTree(knowledgeBaseId, nodeId));
    }
}
    