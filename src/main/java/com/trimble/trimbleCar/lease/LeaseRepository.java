package com.trimble.trimbleCar.lease;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByCustomerId(Long customerId);
    List<Lease> findByCarOwnerId(Long ownerId);

    List<Lease> findByCustomerIdAndEndDateIsNull(Integer customerId);
}