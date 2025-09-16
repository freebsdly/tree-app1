package org.example.treeapp1.kb.application;

import lombok.Data;
import org.example.treeapp1.kb.domain.knowledgebase.KnowledgeBaseType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class KnowledgeBaseDTO implements Serializable
{
    Long id;
    String name;
    KnowledgeBaseType type;
    String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @Data
    public static class Create
    {
        String name;
        KnowledgeBaseType type;
        String description;
    }

    @Data
    public static class Update
    {
        Long id;
        String name;
        KnowledgeBaseType type;
        String description;
    }
}