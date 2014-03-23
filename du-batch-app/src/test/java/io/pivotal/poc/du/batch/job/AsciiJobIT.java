package io.pivotal.poc.du.batch.job;

import static org.junit.Assert.fail;
import io.pivotal.poc.du.batch.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
public class AsciiJobIT {

	@Test
	public void test() {
		//launch the system
		//write a file to the target specified directory
		//watch it execute
		//query through restful service
	}

}
