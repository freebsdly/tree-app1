package org.example.treeapp1.kb.infrastructure.mapper;

import org.example.treeapp1.kb.application.KnowledgeBaseDTO;
import org.example.treeapp1.kb.domain.knowledgebase.KnowledgeBase;
import org.example.treeapp1.kb.infrastructure.persistence.jpa.repository.JpaKnowledgeBaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface KnowledgeBaseMapper
{
    KnowledgeBase toDomain(JpaKnowledgeBaseEntity knowledgeBaseEntity);

    JpaKnowledgeBaseEntity toEntity(KnowledgeBase knowledgeBase);

    KnowledgeBaseDTO toDto(KnowledgeBase saved);
}