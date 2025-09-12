package org.example.treeapp1.service;

import org.example.treeapp1.exception.ResourceNotFoundException;

import java.util.List;

public interface NodeService
{
    NodeDTO createRootNode(Long knowledgeBaseId) throws ResourceNotFoundException;

    NodeDTO createNode(Long knowledgeBaseId, NodeDTO dto) throws ResourceNotFoundException;

    NodeDTO getNodeById(Long id) throws ResourceNotFoundException;

    List<NodeDTO> getChildren(Long knowledgeBaseId, Long parentId);

    NodeDTO updateNode(Long knowledgeBaseId, Long nodeId, NodeDTO dto) throws ResourceNotFoundException;

    void deleteNode(Long knowledgeBaseId, Long nodeId) throws ResourceNotFoundException;

    NodeVO getTree(Long knowledgeBaseId) throws ResourceNotFoundException;

    NodeVO getSubTree(Long knowledgeBaseId, Long nodeId) throws ResourceNotFoundException;
}
