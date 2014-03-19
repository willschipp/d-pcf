package io.pivotal.poc.du.loyalty.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCard, Long> {

	LoyaltyCard findByName(String name);
	
}
