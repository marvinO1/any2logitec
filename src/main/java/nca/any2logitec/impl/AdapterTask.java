package nca.any2logitec.impl;

import java.util.TimerTask;

import nca.any2logitec.api.Adapter;

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
