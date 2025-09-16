package org.example.treeapp1.kb.domain.knowledgebase;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class KnowledgeBase {
    private Long id;
    private String name;
    private KnowledgeBaseType type;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 领域行为
    public void updateName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("知识库名称不能为空");
        }
        this.name = newName;
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }

    public void changeType(KnowledgeBaseType newType) {
        if (newType == null) {
            throw new IllegalArgumentException("知识库类型不能为空");
        }
        this.type = newType;
    }

    // 领域规则：验证知识库是否可以删除
    public boolean canBeDeleted() {
        // 可以在这里添加业务规则，例如检查是否有未完成的任务等
        return true;
    }
}
