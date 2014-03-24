package io.pivotal.poc.du.loyalty.s4.web;

import io.pivotal.poc.du.loyalty.s4.domain.LoyaltyCard;
import io.pivotal.poc.du.loyalty.s4.domain.LoyaltyCardRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loyaltyCard")
public class LoyaltyCardEndpoint {

	@Autowired
	private LoyaltyCardRepository loyaltyCardRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<LoyaltyCard> findAll() {
		return loyaltyCardRepository.findAll();
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public LoyaltyCard save(@RequestBody LoyaltyCard loyaltyCard) {
		return loyaltyCardRepository.save(loyaltyCard);
	}
	
}
