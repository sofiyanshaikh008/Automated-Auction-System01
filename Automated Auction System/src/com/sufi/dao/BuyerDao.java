package com.sufi.dao;

import java.util.List;

import com.arijit.bean.Buyer;
import com.arijit.bean.Product;
import com.arijit.exception.BuyerException;
import com.arijit.exception.ProductException;

public interface BuyerDao {
	
	public void registerAsBuyer(Buyer user) throws BuyerException;
	
	public Buyer loginAsBuyer(String username , String password) throws BuyerException;
	
	public List<Product> viewByCategory(String cate) throws ProductException;
	
	public void buyProduct(int product_id) throws ProductException;
	
	public List<Buyer> viewAllBuyers() throws BuyerException;
}
