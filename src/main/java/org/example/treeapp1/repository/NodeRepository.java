package org.example.treeapp1.repository;

import org.example.treeapp1.model.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NodeRepository extends JpaRepository<NodeEntity, Long> {
    // 根据知识库ID和父节点ID查询子节点
    List<NodeEntity> findByIdAndParentId(Long knowledgeBaseId, Long parentId);

    // 根据知识库ID和路径查询节点
    Optional<NodeEntity> findByIdAndPath(Long knowledgeBaseId, String path);

    // 查询知识库的根节点
    Optional<NodeEntity> findByIdAndParentIdIsNull(Long knowledgeBaseId);

    // 检查同名节点是否存在
    boolean existsByIdAndParentIdAndName(Long knowledgeBaseId, Long parentId, String name);
}
    