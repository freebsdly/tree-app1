package org.example.treeapp1.service;

import org.example.treeapp1.exception.ResourceExistException;
import org.example.treeapp1.exception.ResourceNotFoundException;
import org.example.treeapp1.model.NodeEntity;
import org.example.treeapp1.repository.NodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeServiceImpl implements NodeService
{

    private final NodeRepository nodeRepository;
    private final KnowledgeBaseService knowledgeBaseService;
    private final ServiceMapper serviceMapper;

    public NodeServiceImpl(NodeRepository nodeRepository,
                           KnowledgeBaseService knowledgeBaseService,
                           ServiceMapper serviceMapper)
    {
        this.nodeRepository = nodeRepository;
        this.knowledgeBaseService = knowledgeBaseService;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public NodeDTO createRootNode(Long knowledgeBaseId) throws ResourceNotFoundException
    {
        KnowledgeBaseDTO kb = knowledgeBaseService.getKnowledgeBaseById(knowledgeBaseId);

        // 检查是否已存在根节点
        if (nodeRepository.findByIdAndParentIdIsNull(knowledgeBaseId).isPresent()) {
            throw new IllegalArgumentException("该知识库已存在根节点");
        }

        NodeEntity root = new NodeEntity();
        root.setName("root");
        root.setParent(null);
        root.setPath("/");
        root.setDirectory(true);
        root.setKnowledgeBase(serviceMapper.toEntity(kb));

        NodeEntity save = nodeRepository.save(root);
        return serviceMapper.toDto(save);
    }

    @Override
    public NodeDTO createNode(Long knowledgeBaseId, NodeDTO dto) throws ResourceNotFoundException
    {
        KnowledgeBaseDTO kb = knowledgeBaseService.getKnowledgeBaseById(knowledgeBaseId);

        // 验证父节点是否存在
        NodeEntity parentNodeEntity;
        if (dto.getParent() == null) {
            // 只能有一个根节点
            throw new IllegalArgumentException("除根节点外，其他节点必须指定父节点");
        } else {
            parentNodeEntity = nodeRepository.findById(dto.getParent().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(dto.getParent().getId())));

            // 验证父节点是否属于当前知识库
            if (!parentNodeEntity.getKnowledgeBase().getId().equals(knowledgeBaseId)) {
                throw new IllegalArgumentException("父节点不属于当前知识库");
            }

            // 验证父节点是否为目录
            if (!parentNodeEntity.isDirectory()) {
                throw new IllegalArgumentException("父节点必须是目录");
            }
        }

        // 检查同一目录下是否有同名节点
        if (nodeRepository.existsByIdAndParentIdAndName(knowledgeBaseId, dto.getParent().getId(), dto.getName())) {
            throw new ResourceExistException(dto.getName());
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
        nodeEntity.setParent(parentNodeEntity);
        nodeEntity.setPath(path);
        nodeEntity.setDirectory(dto.isDirectory());
        nodeEntity.setKnowledgeBase(serviceMapper.toEntity(kb));

        NodeEntity save = nodeRepository.save(nodeEntity);
        return serviceMapper.toDto(save);
    }

    @Override
    public NodeDTO getNodeById(Long id) throws ResourceNotFoundException
    {
        NodeEntity nodeEntity = nodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));
        return serviceMapper.toDto(nodeEntity);
    }

    @Override
    public List<NodeDTO> getChildren(Long knowledgeBaseId, Long parentId)
    {
        List<NodeEntity> list = nodeRepository.findByKnowledgeBaseIdAndParentId(
                knowledgeBaseId,
                parentId);
        return list.stream()
                .map(serviceMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public NodeDTO updateNode(Long knowledgeBaseId, Long nodeId, NodeDTO dto) throws ResourceNotFoundException
    {
        NodeEntity nodeById = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(nodeId)));

        // 验证节点是否属于当前知识库
        if (!nodeById.getKnowledgeBase().getId().equals(knowledgeBaseId)) {
            throw new IllegalArgumentException("节点不属于当前知识库");
        }

        // 如果修改了名称，需要更新自身路径和所有子节点路径
        if (!nodeById.getName().equals(dto.getName())) {
            String oldName = nodeById.getName();
            String newName = dto.getName();

            // 检查新名称在同一目录下是否已存在
            if (nodeRepository.existsByIdAndParentIdAndName(
                    knowledgeBaseId, nodeById.getParent().getId(), newName)) {
                throw new ResourceExistException(newName);
            }

            // 更新当前节点名称
            nodeById.setName(newName);

            // 计算新路径
            String oldPath = nodeById.getPath();
            String parentPath = nodeById.getParent().getId() == null ? "/" : nodeRepository
                    .findById(nodeById.getParent().getId())
                    .get()
                    .getPath();

            String newPath = parentPath.equals("/")
                    ? "/" + newName
                    : parentPath + "/" + newName;

            if (nodeById.isDirectory() && !newPath.endsWith("/")) {
                newPath += "/";
            }

            nodeById.setPath(newPath);
            nodeRepository.save(nodeById);

            // 更新所有子节点的路径
            updateChildPaths(knowledgeBaseId, oldPath, newPath);
        }

        return serviceMapper.toDto(nodeById);
    }

    @Transactional
    public void updateChildPaths(Long knowledgeBaseId, String oldParentPath, String newParentPath)
    {
        // 获取所有以oldParentPath为前缀的节点
        List<NodeEntity> children = nodeRepository.findByKnowledgeBaseIdAndParentId(
                knowledgeBaseId,
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
    public void deleteNode(Long knowledgeBaseId, Long nodeId) throws ResourceNotFoundException
    {
        NodeDTO nodeById = getNodeById(nodeId);

        // 验证节点是否属于当前知识库
        if (!nodeById.getKnowledgeBase().getId().equals(knowledgeBaseId)) {
            throw new IllegalArgumentException("节点不属于当前知识库");
        }

        // 递归删除所有子节点
        deleteChildren(knowledgeBaseId, nodeId);

        // 删除当前节点
        nodeRepository.delete(serviceMapper.toEntity(nodeById));
    }

    private void deleteChildren(Long knowledgeBaseId, Long parentId)
    {
        List<NodeEntity> children = nodeRepository.findByKnowledgeBaseIdAndParentId(knowledgeBaseId, parentId);
        for (NodeEntity child : children) {
            // 递归删除子节点的子节点
            deleteChildren(knowledgeBaseId, child.getId());
            // 删除子节点
            nodeRepository.delete(child);
        }
    }

    @Override
    public NodeVO getTree(Long knowledgeBaseId) throws ResourceNotFoundException
    {
        // 获取根节点
        NodeEntity rootNodeEntity = nodeRepository.findByIdAndParentIdIsNull(knowledgeBaseId)
                .orElseThrow(() -> new ResourceNotFoundException("知识库不存在根节点，请先创建根节点"));

        return buildTree(knowledgeBaseId, rootNodeEntity);
    }

    @Override
    public NodeVO getSubTree(Long knowledgeBaseId, Long nodeId) throws ResourceNotFoundException
    {
        NodeEntity nodeById = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new ResourceNotFoundException("节点不存在"));

        // 验证节点是否属于当前知识库
        if (!nodeById.getKnowledgeBase().getId().equals(knowledgeBaseId)) {
            throw new IllegalArgumentException("节点不属于当前知识库");
        }

        return buildTree(knowledgeBaseId, nodeById);
    }

    private NodeVO buildTree(Long knowledgeBaseId, NodeEntity nodeEntity)
    {
        NodeVO nodeVO = new NodeVO();
        nodeVO.setId(nodeEntity.getId());
        nodeVO.setName(nodeEntity.getName());
        nodeVO.setPath(nodeEntity.getPath());
        nodeVO.setDirectory(nodeEntity.isDirectory());

        // 递归构建子树
        List<NodeEntity> children = nodeRepository.findByKnowledgeBaseIdAndParentId(
                knowledgeBaseId,
                nodeEntity.getId());
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
    