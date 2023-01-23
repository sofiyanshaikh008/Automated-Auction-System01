package com.sufi.dao;

import java.util.List;
import com.arijit.bean.Product;
import com.arijit.bean.Seller;
import com.arijit.exception.ProductException;
import com.arijit.exception.SellerException;

public interface SellerDao {


	public void registerAsSeller(Seller user) throws SellerException;
	
	public Seller loginAsSeller(String username , String password) throws SellerException;
	
	public void addProducts(List<Product> list) throws ProductException;
	
	public List<Product> viewSoldProducts() throws ProductException;
	
	public void removeItem(int product_id) throws ProductException;
	
	public void addProduct(Product item) throws ProductException;
	
	public void updateProduct(int product_id , int k) throws ProductException;
}

