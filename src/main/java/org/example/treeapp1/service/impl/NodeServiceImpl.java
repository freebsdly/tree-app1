package org.example.treeapp1.service.impl;

import org.example.treeapp1.model.KnowledgeBaseEntity;
import org.example.treeapp1.model.NodeEntity;
import org.example.treeapp1.repository.NodeRepository;
import org.example.treeapp1.service.KnowledgeBaseService;
import org.example.treeapp1.service.NodeDTO;
import org.example.treeapp1.service.NodeService;
import org.example.treeapp1.service.NodeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeServiceImpl implements NodeService {

    private final NodeRepository nodeRepository;
    private final KnowledgeBaseService knowledgeBaseService;

    public NodeServiceImpl(NodeRepository nodeRepository, KnowledgeBaseService knowledgeBaseService) {
        this.nodeRepository = nodeRepository;
        this.knowledgeBaseService = knowledgeBaseService;
    }

    @Override
    public NodeEntity createRootNode(Long knowledgeBaseId) throws ResourceNotFoundException {
        KnowledgeBaseEntity knowledgeBaseEntity = knowledgeBaseService.getKnowledgeBaseById(knowledgeBaseId);

        // 检查是否已存在根节点
        if (nodeRepository.findByIdAndParentIdIsNull(knowledgeBaseId).isPresent()) {
            throw new IllegalArgumentException("该知识库已存在根节点");
        }

        NodeEntity root = new NodeEntity();
        root.setName("root");
        root.setParentId(null);
        root.setPath("/");
        root.setDirectory(true);
        root.setKnowledgeBaseEntity(knowledgeBaseEntity);

        return nodeRepository.save(root);
    }

    @Override
    public NodeEntity createNode(Long knowledgeBaseId, NodeDTO dto) throws ResourceNotFoundException {
        KnowledgeBaseEntity knowledgeBaseEntity = knowledgeBaseService.getKnowledgeBaseById(knowledgeBaseId);

        // 验证父节点是否存在
        NodeEntity parentNodeEntity;
        if (dto.getParentId() == null) {
            // 只能有一个根节点
            throw new IllegalArgumentException("除根节点外，其他节点必须指定父节点");
        } else {
            parentNodeEntity = nodeRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("父节点不存在，ID: " + dto.getParentId()));

            // 验证父节点是否属于当前知识库
            if (!parentNodeEntity.getKnowledgeBaseEntity().getId().equals(knowledgeBaseId)) {
                throw new IllegalArgumentException("父节点不属于当前知识库");
            }

            // 验证父节点是否为目录
            if (!parentNodeEntity.isDirectory()) {
                throw new IllegalArgumentException("父节点必须是目录");
            }
        }

        // 检查同一目录下是否有同名节点
        if (nodeRepository.existsByIdAndParentIdAndName(knowledgeBaseId, dto.getParentId(), dto.getName())) {
            throw new IllegalArgumentException("同一目录下已存在同名节点: " + dto.getName());
        }

        // 计算路径
        String path = parentNodeEntity.getPath().equals("/")
                ? "/" + dto.getName()
                : parentNodeEntity.getPath() + "/" + dto.getName();

        // 如果是目录，路径以/结尾
        if (dto.isDirectory() && !path.endsWith("/")) {
            path += "/";
        }

        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setName(dto.getName());
        nodeEntity.setParentId(dto.getParentId());
        nodeEntity.setPath(path);
        nodeEntity.setDirectory(dto.isDirectory());
        nodeEntity.setKnowledgeBaseEntity(knowledgeBaseEntity);

        return nodeRepository.save(nodeEntity);
    }

    @Override
    public NodeEntity getNodeById(Long id) throws ResourceNotFoundException {
        return nodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("节点不存在，ID: " + id));
    }

    @Override
    public List<NodeEntity> getChildren(Long knowledgeBaseId, Long parentId) {
        return nodeRepository.findByIdAndParentId(knowledgeBaseId, parentId);
    }

    @Override
    @Transactional
    public NodeEntity updateNode(Long knowledgeBaseId, Long nodeId, NodeDTO dto) throws ResourceNotFoundException {
        NodeEntity nodeEntity = getNodeById(nodeId);

        // 验证节点是否属于当前知识库
        if (!nodeEntity.getKnowledgeBaseEntity().getId().equals(knowledgeBaseId)) {
            throw new IllegalArgumentException("节点不属于当前知识库");
        }

        // 如果修改了名称，需要更新自身路径和所有子节点路径
        if (!nodeEntity.getName().equals(dto.getName())) {
            String oldName = nodeEntity.getName();
            String newName = dto.getName();

            // 检查新名称在同一目录下是否已存在
            if (nodeRepository.existsByIdAndParentIdAndName(
                    knowledgeBaseId, nodeEntity.getParentId(), newName)) {
                throw new IllegalArgumentException("同一目录下已存在同名节点: " + newName);
            }

            // 更新当前节点名称
            nodeEntity.setName(newName);

            // 计算新路径
            String oldPath = nodeEntity.getPath();
            String parentPath = nodeEntity.getParentId() == null ? "/" : nodeRepository.findById(nodeEntity.getParentId()).get().getPath();

            String newPath = parentPath.equals("/")
                    ? "/" + newName
                    : parentPath + "/" + newName;

            if (nodeEntity.isDirectory() && !newPath.endsWith("/")) {
                newPath += "/";
            }

            nodeEntity.setPath(newPath);
            nodeRepository.save(nodeEntity);

            // 更新所有子节点的路径
            updateChildPaths(knowledgeBaseId, oldPath, newPath);
        }

        return nodeEntity;
    }

    private void updateChildPaths(Long knowledgeBaseId, String oldParentPath, String newParentPath) {
        // 获取所有以oldParentPath为前缀的节点
        List<NodeEntity> children = nodeRepository.findByIdAndParentId(knowledgeBaseId,
                nodeRepository.findByIdAndPath(knowledgeBaseId, oldParentPath).get().getId());

        for (NodeEntity child : children) {
            String oldChildPath = child.getPath();
            String newChildPath = oldChildPath.replaceFirst(oldParentPath, newParentPath);

            child.setPath(newChildPath);
            nodeRepository.save(child);

            // 递归更新子节点的子节点
            if (child.isDirectory()) {
                updateChildPaths(knowledgeBaseId, oldChildPath, newChildPath);
            }
        }
    }

    @Override
    @Transactional
    public void deleteNode(Long knowledgeBaseId, Long nodeId) throws ResourceNotFoundException {
        NodeEntity nodeEntity = getNodeById(nodeId);

        // 验证节点是否属于当前知识库
        if (!nodeEntity.getKnowledgeBaseEntity().getId().equals(knowledgeBaseId)) {
            throw new IllegalArgumentException("节点不属于当前知识库");
        }

        // 递归删除所有子节点
        deleteChildren(knowledgeBaseId, nodeId);

        // 删除当前节点
        nodeRepository.delete(nodeEntity);
    }

    private void deleteChildren(Long knowledgeBaseId, Long parentId) {
        List<NodeEntity> children = nodeRepository.findByIdAndParentId(knowledgeBaseId, parentId);
        for (NodeEntity child : children) {
            // 递归删除子节点的子节点
            deleteChildren(knowledgeBaseId, child.getId());
            // 删除子节点
            nodeRepository.delete(child);
        }
    }

    @Override
    public NodeVO getTree(Long knowledgeBaseId) throws ResourceNotFoundException {
        // 获取根节点
        NodeEntity rootNodeEntity = nodeRepository.findByIdAndParentIdIsNull(knowledgeBaseId)
                .orElseThrow(() -> new ResourceNotFoundException("知识库不存在根节点，请先创建根节点"));

        return buildTree(knowledgeBaseId, rootNodeEntity);
    }

    @Override
    public NodeVO getSubTree(Long knowledgeBaseId, Long nodeId) throws ResourceNotFoundException {
        NodeEntity nodeEntity = getNodeById(nodeId);

        // 验证节点是否属于当前知识库
        if (!nodeEntity.getKnowledgeBaseEntity().getId().equals(knowledgeBaseId)) {
            throw new IllegalArgumentException("节点不属于当前知识库");
        }

        return buildTree(knowledgeBaseId, nodeEntity);
    }

    private NodeVO buildTree(Long knowledgeBaseId, NodeEntity nodeEntity) {
        NodeVO nodeVO = new NodeVO();
        nodeVO.setId(nodeEntity.getId());
        nodeVO.setName(nodeEntity.getName());
        nodeVO.setPath(nodeEntity.getPath());
        nodeVO.setDirectory(nodeEntity.isDirectory());

        // 递归构建子树
        List<NodeEntity> children = nodeRepository.findByIdAndParentId(knowledgeBaseId, nodeEntity.getId());
        if (!children.isEmpty()) {
            nodeVO.setChildren(children.stream()
                    .map(child -> buildTree(knowledgeBaseId, child))
                    .collect(Collectors.toList()));
        } else {
            nodeVO.setChildren(new ArrayList<>());
        }

        return nodeVO;
    }
}
    