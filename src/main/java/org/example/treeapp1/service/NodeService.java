package org.example.treeapp1.service;

import org.example.treeapp1.model.NodeEntity;
import org.example.treeapp1.service.impl.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NodeService {
    NodeEntity createRootNode(Long knowledgeBaseId) throws ResourceNotFoundException;

    NodeEntity createNode(Long knowledgeBaseId, NodeDTO dto) throws ResourceNotFoundException;

    NodeEntity getNodeById(Long id) throws ResourceNotFoundException;

    List<NodeEntity> getChildren(Long knowledgeBaseId, Long parentId);

    @Transactional
    NodeEntity updateNode(Long knowledgeBaseId, Long nodeId, NodeDTO dto) throws ResourceNotFoundException;

    @Transactional
    void deleteNode(Long knowledgeBaseId, Long nodeId) throws ResourceNotFoundException;

    NodeVO getTree(Long knowledgeBaseId) throws ResourceNotFoundException;

    NodeVO getSubTree(Long knowledgeBaseId, Long nodeId) throws ResourceNotFoundException;
}
