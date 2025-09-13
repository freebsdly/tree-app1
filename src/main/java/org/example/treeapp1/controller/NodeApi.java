package org.example.treeapp1.controller;

import lombok.RequiredArgsConstructor;
import org.example.treeapp1.exception.BusinessException;
import org.example.treeapp1.service.NodeDTO;
import org.example.treeapp1.service.NodeService;
import org.example.treeapp1.service.NodeVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge-bases/{knowledgeBaseId}/nodes")
@RequiredArgsConstructor
public class NodeApi
{

    private final NodeService nodeService;

    @PostMapping("/root")
    public ApiBody<NodeDTO> createRootNode(@PathVariable Long knowledgeBaseId) throws BusinessException
    {
        NodeDTO rootNodeDTO = nodeService.createRootNode(knowledgeBaseId);
        return ApiBody.success(rootNodeDTO);
    }

    @PostMapping
    public ApiBody<NodeDTO> createNode(
            @PathVariable Long knowledgeBaseId,
            @RequestBody NodeDTO dto) throws BusinessException
    {
        NodeDTO nodeDTO = nodeService.createNode(knowledgeBaseId, dto);
        return ApiBody.success(nodeDTO);
    }

    @GetMapping("/{id}")
    public ApiBody<NodeDTO> getNodeById(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long id) throws BusinessException
    {
        NodeDTO nodeDTO = nodeService.getNodeById(id);
        return ApiBody.success(nodeDTO);
    }

    @GetMapping("/parent/{parentId}")
    public ApiBody<List<NodeDTO>> getChildren(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long parentId) throws BusinessException
    {
        List<NodeDTO> children = nodeService.getChildren(knowledgeBaseId, parentId);
        return ApiBody.success(children);
    }

    @PutMapping("/{id}")
    public ApiBody<NodeDTO> updateNode(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long id,
            @RequestBody NodeDTO dto) throws BusinessException
    {
        NodeDTO nodeDTO = nodeService.updateNode(knowledgeBaseId, id, dto);
        return ApiBody.success(nodeDTO);
    }

    @DeleteMapping("/{id}")
    public ApiBody<Void> deleteNode(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long id) throws BusinessException
    {
        nodeService.deleteNode(knowledgeBaseId, id);
        return ApiBody.success(null);
    }

    @GetMapping("/tree")
    public ResponseEntity<NodeVO> getTree(@PathVariable Long knowledgeBaseId) throws BusinessException
    {
        return ResponseEntity.ok(nodeService.getTree(knowledgeBaseId));
    }

    @GetMapping("/subtree/{nodeId}")
    public ResponseEntity<NodeVO> getSubTree(
            @PathVariable Long knowledgeBaseId,
            @PathVariable Long nodeId) throws BusinessException
    {
        return ResponseEntity.ok(nodeService.getSubTree(knowledgeBaseId, nodeId));
    }
}
    