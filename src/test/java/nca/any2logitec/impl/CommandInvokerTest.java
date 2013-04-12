package nca.any2logitec.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import nca.any2logitec.api.Command;
import nca.any2logitec.api.CommandInfo;
import nca.any2logitec.api.CommandKey;
import nca.any2logitec.api.MessageConsumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CommandInvokerTest {

	private CommandInvoker invoker;
	
	@Before
	public void before() throws IOException {
		this.invoker = new CommandInvoker(mock(MessageConsumer.class));
		Path p = Paths.get("target/hossa.b1.pressed");
		Files.deleteIfExists(p);
	}
	
	@Test
	public void testRegisterAndInvoke() throws IOException {		

		Command commandB0 = mock(Command.class);
		this.invoker.register(CommandKey.BUTTON_0, commandB0);
		
		this.invoker.invoke(new CommandInfo(CommandKey.BUTTON_0, 1L));
		
		verify(commandB0).execute(1L);
	}
}
