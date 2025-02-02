package com.store.seller.model.superEntity;

import com.store.seller.enums.DATA_STATUS;
import com.store.seller.utils.GenerateUUID;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class SuperEntityWithoutExpiry {

    @Id
    private String id = GenerateUUID.generateShortUUID();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DATA_STATUS DATASTATUS = DATA_STATUS.ACTIVE;
}
