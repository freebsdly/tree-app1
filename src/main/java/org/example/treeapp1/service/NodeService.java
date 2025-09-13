package org.example.treeapp1.service;

import org.example.treeapp1.exception.BusinessException;

import java.util.List;

public interface NodeService
{
    NodeDTO createRootNode(Long knowledgeBaseId) throws BusinessException;

    NodeDTO createNode(Long knowledgeBaseId, NodeDTO dto) throws BusinessException;

    NodeDTO getNodeById(Long id) throws BusinessException;

    List<NodeDTO> getChildren(Long knowledgeBaseId, Long parentId) throws BusinessException;

    NodeDTO updateNode(Long knowledgeBaseId, Long nodeId, NodeDTO dto) throws BusinessException;

    void deleteNode(Long knowledgeBaseId, Long nodeId) throws BusinessException;

    NodeVO getTree(Long knowledgeBaseId) throws BusinessException;

    NodeVO getSubTree(Long knowledgeBaseId, Long nodeId) throws BusinessException;
}
