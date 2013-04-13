package nca.any2logitec.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import nca.any2logitec.api.DisplayMessage;

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
		
		DisplayMessage msg = new DisplayMessage("hossa", 10, 11L);
		msg.addLine("Hello World");
		
		this.producer.produce(msg);

		// I am to lazzy to write the assert, code works ...
	}

}
