package rnd.mywt.client.bean.action;

public class ValueChangeActionInfo {

	private String chain;

	private String propertyName;

	private ValueChangeAction valueChangeAction;

	public ValueChangeActionInfo(String chain, String propertyName, ValueChangeAction valueChangeAction) {
		this.chain = chain;
		this.propertyName = propertyName;
		this.valueChangeAction = valueChangeAction;
	}

	public String getChain() {
		return this.chain;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public ValueChangeAction getValueChangeListener() {
		return this.valueChangeAction;
	}

}
