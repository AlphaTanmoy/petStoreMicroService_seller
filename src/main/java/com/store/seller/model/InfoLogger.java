package com.store.seller.model;

import com.store.seller.enums.INFO_LOG_TYPE;
import com.store.seller.model.superEntity.SuperEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "info_logger")
public class InfoLogger extends SuperEntity {

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private INFO_LOG_TYPE type;

}
