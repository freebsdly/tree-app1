package org.example.treeapp1.kb.domain.knowledgebase;

import org.example.treeapp1.exception.BusinessException;
import org.example.treeapp1.exception.ResourceExistException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 知识库领域服务，封装跨实体或复杂的业务逻辑
 */
@Component
public class KnowledgeBaseDomainService
{

    // 领域规则：验证知识库名称唯一性
    public void validateNameUniqueness(String name, boolean exists) throws ResourceExistException
    {
        if (exists) {
            throw new ResourceExistException(name);
        }
    }

    // 领域规则：初始化知识库
    public void initializeKnowledgeBase(KnowledgeBase knowledgeBase)
    {
        LocalDateTime now = LocalDateTime.now();
        knowledgeBase.setCreatedAt(now);
        knowledgeBase.setUpdatedAt(now);

        // 可以添加其他初始化逻辑
    }

    // 领域规则：更新知识库前的验证
    public void validateUpdate(KnowledgeBase existing, KnowledgeBase updated, boolean nameExists)
            throws ResourceExistException
    {
        // 检查名称是否已被使用（如果名称已更改）
        if (!existing.getName().equals(updated.getName()) && nameExists) {
            throw new ResourceExistException(updated.getName());
        }

        // 更新时间戳
        updated.setUpdatedAt(LocalDateTime.now());
        updated.setCreatedAt(existing.getCreatedAt());
        updated.setId(existing.getId());
    }

    // 领域规则：删除知识库前的验证
    public void validateDelete(KnowledgeBase knowledgeBase) throws BusinessException
    {
        if (!knowledgeBase.canBeDeleted()) {
            throw new BusinessException(200, "知识库当前无法删除");
        }
    }
}
