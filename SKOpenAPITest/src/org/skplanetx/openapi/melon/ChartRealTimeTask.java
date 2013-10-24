package org.skplanetx.openapi.melon;

public class ChartRealTimeTask extends ChartTask {

	public ChartRealTimeTask(OnMelonListener listener) {
		super(listener);
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/charts/realtime";
	}

}
