package org.example.treeapp1.service;

import org.example.treeapp1.model.KnowledgeBaseEntity;
import org.example.treeapp1.model.NodeEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceMapper
{
    KnowledgeBaseEntity toEntity(KnowledgeBaseDTO knowledgeBaseDTO);

    KnowledgeBaseDTO toDto(KnowledgeBaseEntity knowledgeBaseEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    KnowledgeBaseEntity partialUpdate(KnowledgeBaseDTO knowledgeBaseDTO,
                                      @MappingTarget KnowledgeBaseEntity knowledgeBaseEntity);

    NodeEntity toEntity(NodeDTO nodeDTO);

    NodeDTO toDto(NodeEntity nodeEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NodeEntity partialUpdate(NodeDTO nodeDTO, @MappingTarget NodeEntity nodeEntity);
}