package com.sufi.daoImp;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.arijit.bean.Admin;
import com.arijit.bean.Buyer;
import com.arijit.bean.Product;
import com.arijit.bean.Seller;
import com.arijit.dao.AdminDao;
import com.arijit.exception.AdminException;
import com.arijit.exception.BuyerException;
import com.arijit.exception.ProductException;
import com.arijit.exception.SellerException;
import com.arijit.utility.DBUtil;

public class AdminDaoImpl implements AdminDao{
	
	private Admin admin;
	
	@Override
	public Admin adminLogIn(String username , String password) throws AdminException{
		
		Admin user = null;
		
		try(Connection conn = DBUtil.provideConnection()){
				
			PreparedStatement state = conn.prepareStatement("select * from admin where adminusername = ? AND adminpassword = ?");
			
			
			state.setString(1, username);
			state.setString(2, password);
			
			
			ResultSet res = state.executeQuery();
			
			
			if(res.next()) {
				
				user = new Admin(res.getInt("adminId"), res.getString("adminName"), res.getString("AdminUsername"), res.getString("AdminPassword"));
				this.admin = user;
				
				System.out.println("Log in successfully !" );
			}
			else {
				
				System.out.println("unable to log in please try again!");
			}
			
			
		}
		catch(NullPointerException e) {
			
			
		}
		catch(SQLException e) {
			
			System.out.println(e.getMessage());
			
		}
		
		return user;
			
	}

	@Override
	public List<Product> viewProductsDetails() throws ProductException{
		
		
		
		if(this.admin == null) {
			
			System.out.println("To access this functionality you need to login first!");
			
			return null;
		}
		
		
		List<Product> products = new ArrayList<>();
		
		try(Connection conn = DBUtil.provideConnection()) {
			
			PreparedStatement state = conn.prepareStatement("select * from products");	
			
			ResultSet res = state.executeQuery();
			
			
			
			while(res.next()) {
				
				
				int i = res.getInt("ProId");
				String n = res.getString("proName");
				int si = res.getInt("sellerId");
				int p = res.getInt("price");
				int q = res.getInt("quantity");
				Boolean s = res.getBoolean("status");
				String c = res.getString("category");
				
				
				
				products.add(new Product(i,n,si,p, q ,s, c));
				
				
			}
			
			
			if(products.size() == 0) {
				
				System.out.println("There is no product for sale!");
			}
			
		}
		catch(SQLException e) {
			
			System.out.println(e.getMessage());
		}
		
		
		
		
		return products;
	}

	@Override
	public List<Buyer> viewRegisteredBuyers() throws BuyerException{
		
		if(this.admin == null) {
			
			System.out.println("To access this functionality you need to login first!");
			
			return null;
		}
		
		
		List<Buyer> users = new ArrayList<>();
		
		try(Connection conn = DBUtil.provideConnection()) {
			
			PreparedStatement state = conn.prepareStatement("select * from buyers");	
			
			ResultSet res = state.executeQuery();
			
			
			
			while(res.next()) {
				
				
				int id = res.getInt("buyerId");
				String n = res.getString("buyerName");
				String s = res.getString("buyerUsername");
				String p = res.getString("buyerPassword");
				
				users.add(new Buyer(id, n, s, p));				
			}
			
			
			if(users.size() == 0) {
				
				System.out.println("There is no registerd buyer!");
			}
			
		}
		catch(SQLException e) {
			
			System.out.println(e.getMessage());
		}
		
		
		
		
		return users;
	}

	@Override
	public List<Seller> viewRegisterdSellers() throws SellerException{
		
		if(this.admin == null) {
			
			System.out.println("To access this functionality you need to login first!");
			
			return null;
		}
		
		
		List<Seller> users = new ArrayList<>();
		
		try(Connection conn =DBUtil.provideConnection()) {
			
			PreparedStatement state = conn.prepareStatement("select * from seller");	
			
			ResultSet res = state.executeQuery();
			
			
			
			while(res.next()) {
				
				
				int id = res.getInt("sellerId");
				String n = res.getString("sellerName");
				String s = res.getString("sellerUsername");
				String p = res.getString("sellerPassword");
				
				users.add(new Seller(id, n, s, p));				
			}
			
			
			if(users.size() == 0) {
				
				System.out.println("There is no registerd Seller!");
			}
			
		}
		catch(SQLException e) {
			
			System.out.println(e.getMessage());
		}
		
		
		
		
		return users;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@Override
	public void viewDailyTotalSales() {
		
		if(this.admin == null) {
			
			System.out.println("To access this functionality you need to login first!");
			
			return ;
		}
		
		
		
		try(Connection conn = DBUtil.provideConnection()) {
			
			
			
			PreparedStatement state =  conn.prepareStatement("select * from SoldProduct where date = ?");
			

			LocalDate date = LocalDate.now();
			
			state.setDate(1, Date.valueOf(date));
			
			
			int total = 0;
			
			ResultSet res = state.executeQuery();
			
			
			
			
			while(res.next() ) {
				
				total += res.getInt("price");
			}
			
			
			System.out.println("Total Sale of Today is : "  + total);
			
		}
		catch(SQLException e) {
			
			
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	
}
