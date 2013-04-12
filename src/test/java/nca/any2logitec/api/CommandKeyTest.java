package nca.any2logitec.api;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(JUnit4.class)
public class CommandKeyTest {

	@Test
	public void testFromFoleRepresentation() {		
		Path p = Paths.get("hossa/bla/b1.pressed");
		CommandKey key = CommandKey.fromFileRepresentation(p);		
		assertThat(key, equalTo(CommandKey.BUTTON_1));
	}
	
	@Test
	public void testFromFoleRepresentationNoMatch() {		
		Path p = Paths.get("hossa/bla/b1.pre");
		CommandKey key = CommandKey.fromFileRepresentation(p);		
		assertThat(key, equalTo(null));
	}
}
