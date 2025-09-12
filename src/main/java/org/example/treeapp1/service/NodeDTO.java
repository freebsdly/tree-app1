package org.example.treeapp1.service;

import lombok.Data;

@Data
public class NodeDTO {
    private String name;
    private Long parentId;
    private boolean isDirectory;
    private String path;
}
