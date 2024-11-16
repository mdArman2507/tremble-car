package com.trimble.trimbleCar.carOwner;


import com.trimble.trimbleCar.car.Car;
import com.trimble.trimbleCar.common.BaseEntity;
import jakarta.persistence.*;
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
public class Owner extends BaseEntity {


    private String name;

    @OneToMany(mappedBy = "owner")
    private List<Car> cars;
}
