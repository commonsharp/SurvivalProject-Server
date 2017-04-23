package net.objects;

public class BigUserShop {
	public String username;
	public String shopName;
	
	UserShop[] userShops;
	
	public BigUserShop(String username, String shopName) {
		userShops = new UserShop[4];
		this.username = username;
		this.shopName = shopName;
	}
	
	public void addShop(UserShop userShop, int index) {
		userShops[index] = userShop;
	}
	
	public UserShop getShop(int index) {
		return userShops[index];
	}
}
