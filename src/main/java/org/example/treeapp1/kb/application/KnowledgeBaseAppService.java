package org.example.treeapp1.kb.application;

import org.example.treeapp1.exception.BusinessException;
import org.example.treeapp1.exception.ResourceNotFoundException;
import org.example.treeapp1.kb.domain.knowledgebase.KnowledgeBase;
import org.example.treeapp1.kb.domain.knowledgebase.KnowledgeBaseDomainService;
import org.example.treeapp1.kb.domain.knowledgebase.KnowledgeBaseFactory;
import org.example.treeapp1.kb.domain.knowledgebase.KnowledgeBaseRepository;
import org.example.treeapp1.kb.infrastructure.mapper.KnowledgeBaseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 知识库应用服务，协调领域对象完成业务用例
 */
@Service
public class KnowledgeBaseAppService
{
    private final KnowledgeBaseRepository knowledgeBaseRepository;
    private final KnowledgeBaseDomainService knowledgeBaseDomainService;
    private final KnowledgeBaseFactory knowledgeBaseFactory;
    private final KnowledgeBaseMapper mapper;

    public KnowledgeBaseAppService(
            KnowledgeBaseRepository knowledgeBaseRepository,
            KnowledgeBaseDomainService knowledgeBaseDomainService,
            KnowledgeBaseFactory knowledgeBaseFactory,
            KnowledgeBaseMapper mapper)
    {
        this.knowledgeBaseRepository = knowledgeBaseRepository;
        this.knowledgeBaseDomainService = knowledgeBaseDomainService;
        this.knowledgeBaseFactory = knowledgeBaseFactory;
        this.mapper = mapper;
    }

    @Transactional
    public KnowledgeBaseDTO createKnowledgeBase(KnowledgeBaseDTO.Create dto) throws BusinessException
    {
        // 验证名称唯一性
        knowledgeBaseDomainService.validateNameUniqueness(dto.getName(),
                knowledgeBaseRepository.existsByName(dto.getName()));

        // 使用工厂创建知识库
        KnowledgeBase knowledgeBase = knowledgeBaseFactory.createKnowledgeBase(
                dto.getName(), dto.getType(), dto.getDescription());

        // 保存知识库
        KnowledgeBase saved = knowledgeBaseRepository.save(knowledgeBase);

        // 转换为DTO返回
        return mapper.toDto(saved);
    }

    public KnowledgeBaseDTO getKnowledgeBaseById(Long id) throws BusinessException
    {
        KnowledgeBase knowledgeBase = knowledgeBaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));

        return mapper.toDto(knowledgeBase);
    }

    public List<KnowledgeBaseDTO> getAllKnowledgeBases()
    {
        return knowledgeBaseRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public KnowledgeBaseDTO updateKnowledgeBase(KnowledgeBaseDTO.Update dto) throws BusinessException
    {
        KnowledgeBase existing = knowledgeBaseRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(dto.getId())));

        // 创建更新后的知识库
        KnowledgeBase updated = knowledgeBaseFactory.createUpdatedKnowledgeBase(
                existing, dto.getName(), dto.getType(), dto.getDescription());

        // 验证更新
        knowledgeBaseDomainService.validateUpdate(
                existing, updated,
                !existing.getName().equals(dto.getName()) && knowledgeBaseRepository.existsByName(dto.getName()));

        // 保存更新后的知识库
        KnowledgeBase saved = knowledgeBaseRepository.save(updated);

        return mapper.toDto(saved);
    }

    @Transactional
    public void deleteKnowledgeBase(Long id) throws BusinessException
    {
        KnowledgeBase knowledgeBase = knowledgeBaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));

        // 验证删除
        knowledgeBaseDomainService.validateDelete(knowledgeBase);

        // 删除知识库
        knowledgeBaseRepository.deleteById(id);
    }
}
