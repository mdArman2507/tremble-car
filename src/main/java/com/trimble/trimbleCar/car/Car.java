package com.trimble.trimbleCar.car;

import com.trimble.trimbleCar.carOwner.Owner;
import com.trimble.trimbleCar.common.BaseEntity;
import com.trimble.trimbleCar.history.CarTransactionHistory;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car extends BaseEntity {
    private String make;
    private String model;
    private int year;
    private String status;  // e.g., "Ideal", "On Lease", "On Service"

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @OneToMany(mappedBy = "car")
    private List<CarTransactionHistory> histories;
}
