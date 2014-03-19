package io.pivotal.poc.du.loyalty.s4.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCard, Long> {

}
