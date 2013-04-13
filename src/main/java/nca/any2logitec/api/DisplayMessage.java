package nca.any2logitec.api;
import java.util.ArrayList;
import java.util.List;

public class DisplayMessage {

	private final List<String> lines = new ArrayList<String>();
	private final String feedName;
	private final int desiredDisplayTimeInSeconds;
	private final long correlationId;
	
	public DisplayMessage(String feedName, int desiredDisplayTimeInSeconds, long correlationId) {
		super();
		this.feedName = feedName;
		this.desiredDisplayTimeInSeconds = desiredDisplayTimeInSeconds;
		this.correlationId = correlationId;
	}
	
	public void setLines(List<String> lines) {
		this.lines.clear();
		this.lines.addAll(lines);		
	}
	
	public DisplayMessage addLine(String line) {
		if (this.lines.size() < 4) {
			this.lines.add(line);
		}	
		return this;
	}
	

	public List<String> getLines() {
		return lines;
	}

	public String getFeedName() {
		return feedName;
	}

	public int getDesiredDisplayTimeInSeconds() {
		return desiredDisplayTimeInSeconds;
	}

	public long getCorrelationId() {
		return correlationId;
	}
}
