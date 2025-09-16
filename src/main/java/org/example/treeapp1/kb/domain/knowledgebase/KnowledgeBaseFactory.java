package org.example.treeapp1.kb.domain.knowledgebase;

import org.springframework.stereotype.Component;

/**
 * 知识库工厂，负责创建知识库领域对象
 */
@Component
public class KnowledgeBaseFactory
{

    private final KnowledgeBaseDomainService knowledgeBaseDomainService;

    public KnowledgeBaseFactory(KnowledgeBaseDomainService knowledgeBaseDomainService)
    {
        this.knowledgeBaseDomainService = knowledgeBaseDomainService;
    }

    // 创建知识库
    public KnowledgeBase createKnowledgeBase(String name, KnowledgeBaseType type, String description)
    {
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        knowledgeBase.setName(name);
        knowledgeBase.setType(type);
        knowledgeBase.setDescription(description);

        // 初始化知识库
        knowledgeBaseDomainService.initializeKnowledgeBase(knowledgeBase);

        return knowledgeBase;
    }

    // 从现有知识库创建更新后的知识库
    public KnowledgeBase createUpdatedKnowledgeBase(
            KnowledgeBase existing,
            String name,
            KnowledgeBaseType type,
            String description)
    {

        KnowledgeBase updated = new KnowledgeBase();
        updated.setName(name);
        updated.setType(type);
        updated.setDescription(description);

        return updated;
    }
}

