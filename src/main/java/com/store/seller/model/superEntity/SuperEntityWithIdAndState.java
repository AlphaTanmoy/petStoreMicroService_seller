package com.store.seller.model.superEntity;

import com.store.seller.enums.DATA_STATUS;
import com.store.seller.utils.GenerateUUID;
import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class SuperEntityWithIdAndState {

    @Id
    private String id = GenerateUUID.generateShortUUID();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DATA_STATUS DATASTATUS = DATA_STATUS.ACTIVE;
}
