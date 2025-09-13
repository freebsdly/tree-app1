package org.example.treeapp1.service;

import org.example.treeapp1.exception.BusinessException;
import org.example.treeapp1.exception.ResourceExistException;
import org.example.treeapp1.exception.ResourceNotFoundException;
import org.example.treeapp1.model.KnowledgeBaseEntity;
import org.example.treeapp1.repository.KnowledgeBaseRepository;
import org.example.treeapp1.repository.NodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService
{

    private final KnowledgeBaseRepository knowledgeBaseRepository;
    private final NodeRepository nodeRepository;
    private final ServiceMapper serviceMapper;

    public KnowledgeBaseServiceImpl(
            KnowledgeBaseRepository knowledgeBaseRepository,
            NodeRepository nodeRepository,
            ServiceMapper serviceMapper)
    {
        this.knowledgeBaseRepository = knowledgeBaseRepository;
        this.nodeRepository = nodeRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public KnowledgeBaseDTO createKnowledgeBase(KnowledgeBaseDTO.Create dto) throws BusinessException
    {
        if (knowledgeBaseRepository.findByName(dto.getName()).isPresent()) {
            throw new ResourceExistException(dto.getName());
        }
        KnowledgeBaseEntity knowledgeBaseEntity = serviceMapper.toEntity(dto);
        KnowledgeBaseEntity save = knowledgeBaseRepository.save(knowledgeBaseEntity);
        return serviceMapper.toDto(save);
    }

    @Override
    public KnowledgeBaseDTO getKnowledgeBaseById(Long id) throws BusinessException
    {
        KnowledgeBaseEntity knowledgeBaseEntity = knowledgeBaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));
        return serviceMapper.toDto(knowledgeBaseEntity);
    }

    @Override
    public KnowledgeBaseDTO getKnowledgeBaseByName(String name) throws BusinessException
    {
        KnowledgeBaseEntity knowledgeBaseEntity = knowledgeBaseRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(name));
        return serviceMapper.toDto(knowledgeBaseEntity);
    }

    @Override
    public List<KnowledgeBaseDTO> getAllKnowledgeBases()
    {
        return knowledgeBaseRepository.findAll().stream().map(serviceMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public KnowledgeBaseDTO updateKnowledgeBase(KnowledgeBaseDTO.Update dto) throws BusinessException
    {
        KnowledgeBaseEntity existing = knowledgeBaseRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(dto.getId())));

        if (!existing.getName().equals(dto.getName()) && knowledgeBaseRepository.existsByName(dto.getName())) {
            throw new ResourceExistException(dto.getName());
        }

        serviceMapper.partialUpdate(dto, existing);
        KnowledgeBaseEntity save = knowledgeBaseRepository.save(existing);
        return serviceMapper.toDto(save);
    }

    @Override
    @Transactional
    public void deleteKnowledgeBase(Long id) throws ResourceNotFoundException
    {
        knowledgeBaseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));
        // 先删除该知识库下的所有节点
        nodeRepository.deleteByKnowledgeBaseId(id);
        // 再删除知识库
        knowledgeBaseRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name)
    {
        return knowledgeBaseRepository.existsByName(name);
    }
}
    