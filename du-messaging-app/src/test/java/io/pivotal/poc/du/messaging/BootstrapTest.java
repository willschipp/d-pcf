package io.pivotal.poc.du.messaging;

import static org.junit.Assert.assertTrue;
import io.pivotal.poc.du.messaging.domain.RequestMessage;
import io.pivotal.poc.du.messaging.domain.RequestMessageRepository;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Bootstrap.class)
@ActiveProfiles("junit")
public class BootstrapTest {

	@Autowired
	private Queue queue;
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private RequestMessageRepository repository;
	
	@Before
	public void before() {
		repository.deleteAllInBatch();
	}
	
	@Test
	public void test() throws Exception {
		//send a message
		JmsTemplate template = new JmsTemplate(connectionFactory);
		template.convertAndSend(queue, "hello world");
		//wait
		Thread.sleep(500);
		//check
		assertTrue(repository.count() > 0);
	}

}