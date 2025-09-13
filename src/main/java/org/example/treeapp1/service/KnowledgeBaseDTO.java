package org.example.treeapp1.service;

import lombok.Data;
import org.example.treeapp1.model.KnowledgeBaseType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.example.treeapp1.model.KnowledgeBaseEntity}
 */
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