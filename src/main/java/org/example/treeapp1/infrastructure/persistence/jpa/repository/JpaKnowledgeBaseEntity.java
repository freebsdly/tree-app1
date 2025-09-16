package org.example.treeapp1.infrastructure.persistence.jpa.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.treeapp1.domain.knowledgebase.model.KnowledgeBaseType;

@Entity
@Table(name = "knowledge_bases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaKnowledgeBaseEntity extends JpaBaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private KnowledgeBaseType type;

    private String description;
}
    