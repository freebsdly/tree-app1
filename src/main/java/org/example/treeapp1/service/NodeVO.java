package org.example.treeapp1.service;

import lombok.Data;

import java.util.List;

@Data
public class NodeVO
{

    private Long id;
    private String name;
    private String path;
    private boolean isDirectory;
    private List<NodeVO> children;

}
