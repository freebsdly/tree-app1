package org.example.treeapp1.model;

import lombok.Getter;

@Getter
public enum KnowledgeBaseType
{
    PERSONAL(1),
    PUBLIC(2);

    private final int code;

    KnowledgeBaseType(int code)
    {
        this.code = code;
    }
}
