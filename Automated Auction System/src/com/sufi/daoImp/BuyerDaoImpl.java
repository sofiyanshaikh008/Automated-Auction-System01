package com.sufi.daoImp;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.arijit.bean.Buyer;
import com.arijit.bean.Product;
import com.arijit.dao.BuyerDao;
import com.arijit.exception.BuyerException;
import com.arijit.exception.ProductException;
import com.arijit.utility.DBUtil;

public class BuyerDaoImpl implements BuyerDao{

	private Buyer buyer;
	Scanner sc = new Scanner(System.in);
	private int count = 0;
	
	@Override
	public void registerAsBuyer(Buyer user) throws BuyerException{
		
		
		try(Connection conn = DBUtil.provideConnection()) {
			
			
			PreparedStatement state = conn.prepareStatement("insert into buyer(buyerName , buyerUsername , buyerPassword) values (? , ? , ?)");
			
			state.setString(1, user.getName());
			state.setString(2, user.getUsername());
			state.setString(3, user.getPassword());
			
			if(state.executeUpdate() > 0) {
				
				System.out.println("User registered successfully!");
			}
			else {
				
				throw new BuyerException("User already registered!");
			}		
			
		}
		catch(SQLException e) {
			
			throw new BuyerException(e.getMessage());
			
		} 
		
	}
	
	

	@Override
	public Buyer loginAsBuyer(String username, String password) throws BuyerException{
		
		Buyer user = null;
		
		
		try(Connection conn = DBUtil.provideConnection()){
			
			PreparedStatement state = conn.prepareStatement("select * from buyer where buyerUsername = ? AND buyerPassword = ?");
			
			state.setString(1, username);
			state.setString(2, password);
			
			ResultSet res = state.executeQuery();
			
			if(res.next()) {
				user = new Buyer();
				
				user.setId(res.getInt("ProId"));
				user.setName(res.getString("buyerName"));
				user.setUsername(res.getString("buyerUsername"));
				user.setPassword(res.getString("buyerPassword"));
				
				buyer = user;
				
				System.out.println("log in successfully!");
			}
			else {
				
				throw new BuyerException("user is not registerd or username/password wrong!");
			}
			
		}
		catch(SQLException e) {
			
			throw new BuyerException(e.getMessage());
			
		} 
		
		
		return user;
	}

	@Override
	public List<Product> viewByCategory(String cate) throws ProductException{
		
		List<Product> products = new ArrayList<>();
		
		try(Connection conn = DBUtil.provideConnection()) {
			
			
		    PreparedStatement state = conn.prepareStatement("select * from product where category = ?");
			state.setString(1, cate);
			
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
				
				throw new ProductException("Product not available for this category!");
			}
			
		}
		catch(SQLException e) {
			
			throw new ProductException(e.getMessage());
		} 
		
		
		return products;
	}

	@Override
	public void buyProduct(int ProId) throws ProductException {
		
		try {
			
			if(this.buyer == null) {
			
				throw new BuyerException("You need to login first to buy product!");
			}
		}
		catch (BuyerException e) {
			
			System.out.println(e.getMessage());
		}
		
		
		try(Connection conn = DBUtil.provideConnection()){
			
			
			
			Scanner sc = new Scanner(System.in);
				
				System.out.println("Enter quanity: ");
				
				int qun = sc.nextInt();
				sc.nextLine();
				
				PreparedStatement state	= conn.prepareStatement("select * from product where ProId = ?");
				
				state.setInt(1, ProId);
				
				
				ResultSet res = state.executeQuery();
				
				if(res.next()) {
					
					int quantity = res.getInt("quantity");
					
					if(quantity < qun) {
						
						throw new ProductException("Not enough quantity to buy!");
					}
					else {
						
						System.out.println("Amount to be paid : " + res.getInt("price") * qun);
						
						if(payment()) {
							
							System.out.println("Payment done... ");
						}
						else {
							
							System.out.println("Too Many Attemps, please try after some time. ");
						}
						
						if(quantity == qun) {
							
							
							PreparedStatement getId = conn.prepareStatement("select * from product where ProId = ?");
							getId.setInt(1, ProId);
							
							ResultSet res1 = getId.executeQuery();
							
							if(res1.next()) {
								
								 int sid = res1.getInt("sellerId");
								 int bid = this.buyer.getId();
								 
								 PreparedStatement insertIntoSales = conn.prepareStatement("insert into SoldProduct(buyerId,sellerId,proName,price,quantity,category,date,) values(? , ? , ?,?,?,?,?)");
									
								
								 
								insertIntoSales.setInt(1, bid);
								insertIntoSales.setInt(2, sid);
								insertIntoSales.setString(3, res.getString("proName"));
								insertIntoSales.setInt(4, (res.getInt("price") * qun));								
								insertIntoSales.setInt(5,  qun);
								insertIntoSales.setString(6, res.getString("category"));
								LocalDate date = LocalDate.now();
								insertIntoSales.setDate(7, Date.valueOf(date));
								
								insertIntoSales.executeUpdate();
								
								
								PreparedStatement update = conn.prepareStatement("update product set status = true, quantity = 0 where proId = ? AND quantity = ?");
								
								
								update.setInt(1, ProId);
								update.setInt(2, qun);
								int n = update.executeUpdate();
								
				
								
								if(n > 0) {
									
									System.out.println("Order Placed succesfully!");
								}
								else {
									
									throw new ProductException("Unable to place order! ");
								}
							}
						}
						else {
							
							
							PreparedStatement getId = conn.prepareStatement("select * from product where proIid = ?");
							getId.setInt(1, ProId);
							
							ResultSet s = getId.executeQuery();
							
							if(s.next()) {
								int sid = s.getInt("sellerId");
								 int bid = this.buyer.getId();
								 
								 PreparedStatement insertIntoSales = conn.prepareStatement("insert into SoldProduct(buyerId,sellerId,proName,price,quantity,category,date,) values(? , ? , ?,?,?,?,?)");
									
								
								 
								insertIntoSales.setInt(1, bid);
								insertIntoSales.setInt(2, sid);
								insertIntoSales.setString(3, res.getString("proName"));
								insertIntoSales.setInt(4, (res.getInt("price") * qun));								
								insertIntoSales.setInt(5,  qun);
								insertIntoSales.setString(6, res.getString("category"));
								LocalDate date = LocalDate.now();
								insertIntoSales.setDate(7, Date.valueOf(date));
								
								insertIntoSales.executeUpdate();
								
								
								
								PreparedStatement update = conn.prepareStatement("update product set quantity = quantity - ? where ProId = ?");
								
								update.setInt(1, qun); 
								update.setInt(2, ProId);
								
								int n = update.executeUpdate();
								
	
								if(n > 0 ) {
									
									System.out.println("Order placed!");
								}
								
							}
							else {
								
								throw new ProductException("unable to place order! ");
							}
							
						}
					}
					
					
				}
				else {
					
					throw new ProductException("There is no product with this id!");
				}			
		}
		catch(SQLException e) {
			
			throw new ProductException(e.getMessage());
		} 
		
		
	}

	@Override
	public List<Buyer> viewAllBuyers() throws BuyerException{
		
		
		List<Buyer> buyers = new ArrayList<>();
		
		
		try(Connection conn = DBUtil.provideConnection()) {
			
			PreparedStatement state = conn.prepareStatement("select * from buyer");
			
			ResultSet res = state.executeQuery();
			
			boolean flag = false;
			
			while(res.next()) {
				
				flag = true;
				
				buyers.add(new Buyer(res.getInt("buyerId"), res.getString("buyerName"), res.getString("buyerUsername"), res.getString("buyerPassword")));
				
			}
			
			if(!flag) {
				
				throw new BuyerException("There is not buyer registerd!");
			}
			
		} 
		catch (SQLException e) {
			
			throw new BuyerException(e.getMessage());
		} 
		
		
		return buyers;
	}
	
	private boolean payment() {
		
		
		if(count == 3) {
			
			return false;
		}
		
		System.out.println("Enter delivary address: ");
		
		String address = sc.nextLine();
		
		System.out.println("Enter mobile number : ");
		
		String mobile = sc.nextLine();
		
		if(mobile.length() > 10) {
			
			System.out.println("Invalid mobile number !");
			payment();
		}
		
		System.out.println("Enter your card number [8375 9283 8293 9393] : ");
		
		String card = sc.nextLine();
		
		System.out.println("Enter card expire date(MM/YYYY) : ");
		
		String date = sc.nextLine();
		
		System.out.println("Enter card CVV [835]: ");
		String cvv = sc.nextLine();
		

		if(card.length() > 16 || cvv.length() > 3 || !(card.equals("8375928382939393")) || !(cvv.equals("835"))) {
			
			System.out.println("Worng card details!");
			count++;
			payment();
			
		}
		
		return true;
	}
	
	


}
