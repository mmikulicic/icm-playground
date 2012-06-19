package eu.dnetlib.playground;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.sarasvati.Arc;
import com.googlecode.sarasvati.Engine;
import com.googlecode.sarasvati.NodeToken;

import eu.dnetlib.workflow.AbstractJobNode;

public class PlaygroundStepJob extends AbstractJobNode {

	private static final Log log = LogFactory.getLog(PlaygroundStepJob.class);

	ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

	private String flavour;
	private int duration;

	@Override
	public void execute(final Engine engine, final NodeToken token) {
		log.info("Executing node");

		token.getEnv().setAttribute("somePrecomputedParameter", "somePrecomputedValue");

		executor.schedule(new Runnable() {
			@Override
			public void run() {
				log.info("Timer fired");
				
				token.getEnv().setAttribute("someParameter", "someComputedValue");
				engine.complete(token, Arc.DEFAULT_ARC);
			}
		}, duration, TimeUnit.SECONDS);
	}

	public String getFlavour() {
		return flavour;
	}

	public void setFlavour(String flavour) {
		this.flavour = flavour;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
