package com.db4o.test;

public class IncrementedId {
	
	public long id;

	public IncrementedId() {
		this.id = 0;
	}

	// end IncrementedId

	public long getNextID() {
		return ++id;
	}

	public void resetId(){
		id = 0;
	}
}
