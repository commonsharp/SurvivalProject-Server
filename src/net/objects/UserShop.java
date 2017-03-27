package net.objects;

public class UserShop {
	String username;
	Item item;
	int elementType;
	int elementAmount;
	int code;
	
	public UserShop(String username, Item item, int code) {
		super();
		this.username = username;
		this.item = item;
		this.code = code;
	}
	
	public UserShop(String username, int elementType, int elementAmount, int code) {
		this.username = username;
		this.elementType = elementType;
		this.elementAmount = elementAmount;
		this.code = code;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getCode() {
		return code;
	}

	public Item getItem() {
		return item;
	}

	public int getElementType() {
		return elementType;
	}

	public int getElementAmount() {
		return elementAmount;
	}
}
