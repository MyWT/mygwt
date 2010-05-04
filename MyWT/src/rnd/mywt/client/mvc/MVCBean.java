package rnd.mywt.client.mvc;

import rnd.mywt.client.bean._BoundBean;

public interface MVCBean extends MVC, _BoundBean {

	// String NAME = "name";

	// String getName();

	// void setName(String newName);

	String BOUND_TO = "boundTo";

	void setBoundTo(String boundTo);

	String getBoundTo();

}