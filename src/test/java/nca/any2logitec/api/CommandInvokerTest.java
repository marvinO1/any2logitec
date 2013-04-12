package nca.any2logitec.api;

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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class CommandInvokerTest {

	private CommandInvoker invoker;
	
	@Before
	public void before() throws IOException {
		this.invoker = new CommandInvoker(Paths.get("target"), "hossa");
		Path p = Paths.get("target/hossa.b1.pressed");
		Files.deleteIfExists(p);
	}
	
	@Test
	public void testRegisterAndInvoke() throws IOException {		

		Command commandB0 = mock(Command.class);
		this.invoker.register(CommandKey.BUTTON_0, commandB0);
		
		this.invoker.invoke(CommandKey.BUTTON_0);
		
		verify(commandB0).execute();
	}
	
	
	@Test
	public void testRemoveCommandFile() throws IOException {		
		Path p = Paths.get("target/hossa.b1.pressed");
		Files.createFile(p);
		assertThat(Files.exists(p), equalTo(true)); 
		this.invoker.removeCommandFile(p);
		assertThat(Files.exists(p), equalTo(false)); 
	}
	
	@Test
	public void testRemoveCommandFileWhichDoesNotExist() throws IOException {		
		Path p = Paths.get("target/hossa.b1.pressed");
		assertThat(Files.exists(p), equalTo(false)); 
		this.invoker.removeCommandFile(p);
		assertThat(Files.exists(p), equalTo(false)); 
	}
	
	@Test
	public void testIsCommandExpired() throws IOException {				
		Path p = Paths.get("target/hossa.b1.pressed");
		Files.createFile(p);				
		assertThat(this.invoker.isCommandExpired(p), equalTo(false));
	}
	
	public void testGetFeedPattern() {
		assertThat(this.invoker.getFeedPattern(), equalTo("hossa.*.pressed"));
	}
}
