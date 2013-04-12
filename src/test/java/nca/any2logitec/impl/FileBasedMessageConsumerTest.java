package nca.any2logitec.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FileBasedMessageConsumerTest {

	private FileBasedMessageConsumer consumer;
	
	@Before
	public void before() throws IOException {
		this.consumer = new FileBasedMessageConsumer(Paths.get("target"), "hossa");
		Path p = Paths.get("target/hossa.b1.pressed");
		Files.deleteIfExists(p);
	}
	
	@Test
	public void testRemoveCommandFile() throws IOException {		
		Path p = Paths.get("target/hossa.b1.pressed");
		Files.createFile(p);
		assertThat(Files.exists(p), equalTo(true)); 
		this.consumer.removeCommandFile(p);
		assertThat(Files.exists(p), equalTo(false)); 
	}
	
	@Test
	public void testRemoveCommandFileWhichDoesNotExist() throws IOException {		
		Path p = Paths.get("target/hossa.b1.pressed");
		assertThat(Files.exists(p), equalTo(false)); 
		this.consumer.removeCommandFile(p);
		assertThat(Files.exists(p), equalTo(false)); 
	}
	
	@Test
	public void testIsCommandExpired() throws IOException {				
		Path p = Paths.get("target/hossa.b1.pressed");
		Files.createFile(p);				
		assertThat(this.consumer.isCommandExpired(p), equalTo(false));
	}
	
	@Test
	public void testGetFeedPattern() {
		assertThat(this.consumer.getFeedPattern(), equalTo("hossa.*.pressed"));
	}
	
	@Test
	public void testStripCorrelationId() {
		Path p = Paths.get("hossa.10.12344.message.b3.pressed");
		assertThat(this.consumer.stripCorrelationId(p), equalTo(12344L));
	}
}
