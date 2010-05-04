package rnd.mywt.client.bean.action;

import java.util.EventObject;

import rnd.mywt.client.bean.ValueChangeEvent;

public class ValueActionEvent extends EventObject {

	private int changeLevel;

	private Object[] chainObjects;

	private ValueChangeEvent valueChangeEvent;

	public ValueActionEvent(Object source, ValueChangeEvent valueChangeEvent, int changeLevel, Object[] chainObjects) {
		super(source);
		this.valueChangeEvent = valueChangeEvent;
		this.changeLevel = changeLevel;
		this.chainObjects = chainObjects;
	}

	public ValueChangeEvent getValueChangeEvent() {
		return this.valueChangeEvent;
	}

	public int getChangeLevel() {
		return this.changeLevel;
	}

	public Object getChainObject(int level) {
		return this.chainObjects[level];
	}

}