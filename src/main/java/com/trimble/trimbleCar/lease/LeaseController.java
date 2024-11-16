package com.trimble.trimbleCar.lease;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {


    private LeaseService leaseService;

    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @PostMapping("/start")
    public Lease startLease(@RequestBody Lease lease) {
        return leaseService.startLease(lease);
    }

    @DeleteMapping("/{leaseId}")
    public void endLease(@PathVariable Long leaseId) {
        leaseService.endLease(leaseId);
    }

    @GetMapping("/history/{customerId}")
    public List<Lease> getLeaseHistory(@PathVariable Long customerId) {
        return leaseService.getLeaseHistory(customerId);
    }
}
