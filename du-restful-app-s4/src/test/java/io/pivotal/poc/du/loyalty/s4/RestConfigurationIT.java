package io.pivotal.poc.du.loyalty.s4;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import io.pivotal.poc.du.loyalty.s4.domain.JpaConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={JpaConfiguration.class,RestConfiguration.class})
public class RestConfigurationIT {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Before
	public void before() {
		mockMvc = webAppContextSetup(context).build();
	}
	
	@Test
	public void test() throws Exception {
		MvcResult result = mockMvc.perform(get("/loyaltyCardRepository").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

}
