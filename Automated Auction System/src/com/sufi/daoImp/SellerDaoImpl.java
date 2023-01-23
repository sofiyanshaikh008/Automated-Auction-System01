package com.sufi.daoImp;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.arijit.bean.Product;
import com.arijit.bean.Seller;
import com.arijit.dao.SellerDao;
import com.arijit.exception.BuyerException;
import com.arijit.exception.ProductException;
import com.arijit.exception.SellerException;
import com.arijit.utility.DBUtil;

public class SellerDaoImpl implements SellerDao{

	private Seller user;
	
	@Override
	public void registerAsSeller(Seller user) throws SellerException{
		
		try(Connection conn = DBUtil.provideConnection()) {
			
			
			PreparedStatement state = conn.prepareStatement("insert into seller(sellerName , sellerUsername , sellerPassword) values (? , ? , ?)");
			
			state.setString(1, user.getName());
			state.setString(2, user.getUsername());
			state.setString(3, user.getPassword());
			
			if(state.executeUpdate() > 0) {
				
				System.out.println("User registered successfully!");
			}
			else {
				
				throw new SellerException("User already registered!");
			}		
			
		}
		catch(SQLException e) {
			
			throw new SellerException(e.getMessage());
			
		} 
		
		
		
	}

	@Override
	public Seller loginAsSeller(String username, String password) throws SellerException{
		
		Seller sell = null;
		
		try(Connection conn = DBUtil.provideConnection()){
			
			PreparedStatement state = conn.prepareStatement("select * from seller where sellerUsername = ? AND sellerUpassword = ?");
			
			state.setString(1, username);
			state.setString(2, password);
			
			ResultSet res = state.executeQuery();
			
			if(res.next()) {
				sell = new Seller();
				
				sell.setId(res.getInt("sellerId"));
				sell.setName(res.getString("sellerName"));
				sell.setUsername(res.getString("sellerUsername"));
				sell.setPassword(res.getString("sellerPassword"));
				
				this.setUser(sell);
				
				System.out.println("Log in successfully !");
			}
			else {
				
				throw new SellerException("user is not registerd or username/password wrong!");
			}
			
		}
		catch(SQLException e) {
			
			throw new SellerException("LogIn Failed !");
			
		} 
		
		return sell;
	}

	@Override
	public List<Product> viewSoldProducts() throws ProductException {
		
		try {
			
			if(this.user == null ) {
				
				
				throw new SellerException("you need to log in first to access this!");
				
			}
		}
		catch (SellerException e) {

			System.out.println(e.getMessage());
		}
		
		
		
		List<Product> products = new ArrayList<>();
		
		
		try(Connection conn = DBUtil.provideConnection()){
			
			
			PreparedStatement state = conn.prepareStatement("select * from product where status = true and sellerId = ?");
			
			state.setInt(1, this.user.getId());
			
			ResultSet res = state.executeQuery();			
			
			
			boolean flag = false;
			Boolean st = res.getBoolean("status");
			
			while(res.next()) {
				
				flag = true;
				int i = res.getInt("ProId");
				String n = res.getString("proName");
				int si = res.getInt("sellerId");
				int p = res.getInt("price");
				int q = res.getInt("quantity");
				Boolean s = res.getBoolean("status");
				String c = res.getString("category");
				
				
				
				products.add(new Product(i,n,si,p, q ,s, c));
				
			}
			
			if(!flag) {
				
				System.out.println("There is no product sold!");
			}
			
		}
		catch(SQLException e) {
			
			
			System.out.println(e.getMessage());
		}
		
		
		
		
		return products;
	}

	@Override
	public void removeItem(int product_id) throws ProductException{
		
		try {
			
			if(this.user == null ) {
				
				
				throw new SellerException("you need to log in first to access this!");
				
			}
		}
		catch (SellerException e) {

			System.out.println(e.getMessage());
		}		
		
		
		try(Connection conn = DBUtil.provideConnection()) {
			
			
			PreparedStatement ps = conn.prepareStatement("delete from products where ProId = ?");
			
			ps.setInt(1, product_id);
			
			
			if(!(ps.executeUpdate() > 0)) {
				
				System.out.println("Unable to delete the product");
			}
			else {
				
				System.out.println("Product deleted!");
			}
				
		}
		catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	
	@Override
	public void addProducts(List<Product> list) throws ProductException{
		
		try {
			
			if(this.user == null ) {
				
				
				throw new SellerException("you need to log in first to access this!");
				
			}
		}
		catch (SellerException e) {

			System.out.println(e.getMessage());
		}
		
		
		try(Connection conn = DBUtil.provideConnection()) {
			
			
			
			list.forEach(s -> {
				
				
				try {

					PreparedStatement state = conn.prepareStatement("insert into product (proName,sellerId,price , quantity, status , category ) values(? , ? , ? , ? , ? , ?)");
					state.setString(1 , s.getName());
					state.setInt(2, user.getId());
					state.setInt(3 , s.getPrice());
					state.setInt(4 , s.getQuantity());
					state.setBoolean(0, false);
					state.setString(6 , s.getCategory());
					
					
					
					int k = state.executeUpdate();
					
				} 
				catch (SQLException e) {
				
					System.out.println(e.getMessage());
				}
				
			});
			
			System.out.println("Products added successfully!");
		}
		catch(SQLException e) {
			
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void addProduct(Product s) throws ProductException{
		
		try {
			
			if(this.user == null ) {
				
				
				throw new SellerException("you need to log in first to access this!");
				
			}
		}
		catch (SellerException e) {

			System.out.println(e.getMessage());
		}
		
		
		try(Connection conn = DBUtil.provideConnection()) {
			
			
			PreparedStatement state = conn.prepareStatement("insert into product (proName ,sellerId,price , quantity, status , category ) values(? , ? , ? , ? , ? , ?)");
			state.setString(1 , s.getName());
			state.setInt(2, user.getId());
			state.setInt(3 , s.getPrice());
			state.setInt(4 , s.getQuantity());
			state.setBoolean(0, false);
			state.setString(6 , s.getCategory());
			
			int k = state.executeUpdate();
			
			if(k > 0) {
				
				System.out.println("Product added successfully!");
			}
		}
		catch(SQLException e) {
			
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void updateProduct(int product_id,int k) throws ProductException{
		
		try {
			
			if(this.user == null ) {
				
				
				throw new SellerException("you need to log in first to access this!");
				
			}
		}
		catch (SellerException e) {

			System.out.println(e.getMessage());
		}
		
	
		Scanner sc = new Scanner(System.in);
		
		
		try(Connection conn = DBUtil.provideConnection()) {
		
			
			switch(k) {
			
			case 1: {
				
				PreparedStatement update = conn.prepareStatement("update product set proName = ? where ProId = ?");
				
				update.setString(1, sc.nextLine());
				update.setInt(2, product_id);
				
				int n = update.executeUpdate();
				
				
				if(n == 0) {
					
					throw new ProductException("Unable to update prodcut name! ");
				}
				else {
					
					System.out.println("name update successfully!");
				}
				
				this.performUpdate(product_id);
			}
			break;
			
			
			
			
			case 2: {
				
				
				PreparedStatement update = conn.prepareStatement("update product set price = ? where ProId = ?");
				
				update.setInt(1, sc.nextInt());
				update.setInt(2, product_id);
				
				int n = update.executeUpdate();
				
				
				if(n == 0) {
					
					throw new ProductException("Unable to update prodcut price! ");
				}
				else {
					
					System.out.println("price update successfully!");
				}
				this.performUpdate(product_id);
				
			}
			break;
			
			
			
			
			case 3 : {
				
				
				PreparedStatement update = conn.prepareStatement("update product set quantity = ? where ProId = ?");
				
				update.setInt(1, sc.nextInt());
				update.setInt(2, product_id);
				
				int n = update.executeUpdate();
				
				
				if(n == 0) {
					
					throw new ProductException("Unable to update prodcut quantity! ");
				}
				else {
					
					System.out.println("quantity update successfully!");
				}
				this.performUpdate(product_id);
			}
			break;
			
			
			
			case 4: {
				
				
				PreparedStatement update = conn.prepareStatement("update products set category = ? where ProId = ?");
				
				update.setString(1, sc.nextLine());
				update.setInt(2, product_id);
				
				int n = update.executeUpdate();
				
				
				if(n == 0) {
					
					throw new ProductException("Unable to update prodcut category! ");
				}
				else {
					
					System.out.println("category update successfully!");
				}
				
				this.performUpdate(product_id);
			}
			break;
				
			
			default : {
				
				System.out.println("Invalid Input");
				
			}
			break;
			}
		
		}
		catch(SQLException e) {
			
			System.out.println(e.getMessage());
		} 
		catch (ProductException e) {
			
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	
	
	
	

	public Seller getUser() {
		return user;
	}

	public void setUser(Seller user) {
		this.user = user;
	}
	
	
	public void performUpdate(int product_id) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter 1 to update name \r\n" 
							+"Enter 2 to update price \r\n"
							+ "Enter 3 to update quantity \r\n"
							+ "Enter 4 to update category \r\n"
							+ "Enter 5 to exit");
		
		int k = sc.nextInt();
		
		sc.nextLine();
		
		if(k == 5) {
			
			return;
		}
		
		try {
			this.updateProduct(product_id, k);
		} catch (ProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public List<Product> viewAllProdcuts()throws ProductException{
		
		try {
			
			if(this.user == null ) {
				
				
				throw new SellerException("you need to log in first to access this!");
				
			}
		}
		catch (SellerException e) {

			System.out.println(e.getMessage());
		}
		
		
		
		List<Product> products = new ArrayList<>();
		
		
		try(Connection conn = DBUtil.provideConnection()){
			
			
			PreparedStatement state = conn.prepareStatement("select * from products where sellerId = ?");
			
			state.setInt(1, this.user.getId());
			
			ResultSet res = state.executeQuery();			
			
			
			boolean flag = false;
			
			
			while(res.next()) {
				
				flag = true;
				
				int i = res.getInt("ProId");
				String n = res.getString("proName");
				int si = res.getInt("sellerId");
				int p = res.getInt("price");
				int q = res.getInt("quantity");
				Boolean s = res.getBoolean("status");
				String c = res.getString("category");
				
				
				
				products.add(new Product(i,n,si,p, q ,s, c));
				
				
			}
			
			if(!flag) {
				
				System.out.println("There is no product !");
			}
			
		}
		catch(SQLException e) {
			
			
			System.out.println(e.getMessage());
		}
		
		
		
		return products;
	}
	
	
}
