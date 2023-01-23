package com.sufi.dao;

import java.util.List;

import com.arijit.bean.Admin;
import com.arijit.bean.Buyer;
import com.arijit.bean.Product;
import com.arijit.bean.Seller;
import com.arijit.exception.AdminException;
import com.arijit.exception.BuyerException;
import com.arijit.exception.ProductException;
import com.arijit.exception.SellerException;

public interface AdminDao {
	
	public Admin adminLogIn(String username , String password) throws AdminException;
	
	public List<Product> viewProductsDetails() throws ProductException;
	
	public List<Buyer> viewRegisteredBuyers() throws BuyerException;
	
	public List<Seller> viewRegisterdSellers() throws SellerException;
	
	public void viewDailyTotalSales();
	
}
