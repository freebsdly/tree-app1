package org.example.treeapp1.service;

import lombok.Data;
import org.example.treeapp1.model.NodeEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link NodeEntity}
 */
@Data
public class NodeDTO implements Serializable
{
    Long id;
    String name;
    String path;
    boolean isDirectory;
    boolean isRoot;
    KnowledgeBaseDTO knowledgeBase;
    NodeDTO parent;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}