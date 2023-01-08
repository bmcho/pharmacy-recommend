package com.recommand.pharmacy.domain.repository;

import com.recommand.pharmacy.domain.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

}
