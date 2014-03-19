package io.pivotal.poc.du.loyalty;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import io.pivotal.poc.du.loyalty.domain.LoyaltyCard;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes=Bootstrap.class)
@ActiveProfiles("junit")
public class BootstrapIT {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void before() {
		mockMvc = webAppContextSetup(context).build();
	}
	
	@Test
	public void test() throws Exception {
		//build the object
		LoyaltyCard card = new LoyaltyCard();
		card.setName("test_card_1");
		//convert to json
		String json = mapper.writeValueAsString(card);
		//send
		mockMvc.perform(put("/loyaltyCard").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		//check it's in there
		MvcResult result = mockMvc.perform(get("/loyaltyCard").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		json = result.getResponse().getContentAsString();
		//convert
		List<LoyaltyCard> cards = mapper.readValue(json,new TypeReference<List<LoyaltyCard>>(){});
		assertNotNull(cards);
		assertFalse(cards.isEmpty());
	}

}
