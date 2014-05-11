package nca.any2logitec.impl;

import java.util.TimerTask;

import nca.any2logitec.api.Adapter;

/**
 * Wrapper to run any component implementing {@link Adapter} as a {@TimerTask}.
 */
class AdapterTask extends TimerTask {

	private Adapter adapter;
	
	AdapterTask(Adapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void run() {
		this.adapter.produceInfo();
	}
}
