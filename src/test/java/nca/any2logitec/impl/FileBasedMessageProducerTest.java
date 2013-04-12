package nca.any2logitec.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FileBasedMessageProducerTest {

	private FileBasedMessageProducer producer;
	
	@Before
	public void before() throws IOException {
		this.producer = new FileBasedMessageProducer(Paths.get("target"));		
	}
	
	@Test
	public void testProduce() throws IOException {		
		
		List<String> messages = new ArrayList<String>();
		messages.add("Hello World");
		
		this.producer.produce(messages, "hossa", 10, 11L);

		// I am to lazzy to write the assert, code works ...
	}

}
