package org.skplanetx.openapi.melon;

public class MelonDJTask extends MelonTask {

	public MelonDJTask(OnMelonListener listener) {
		super(listener);
	}

	@Override
	protected String getUrl() {
		return "http://apis.skplanetx.com/melon/melondj";
	}

}
