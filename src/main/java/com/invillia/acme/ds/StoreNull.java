package com.invillia.acme.ds;

public class StoreNull extends Store {
	@Override
	public Integer getId() {
		return 0;
	}
	
	@Override
	public String getName() {
		return "NOTFOUND";
	}
	
	@Override
	public Address getAddress() {
		return new Address();
	}
}
