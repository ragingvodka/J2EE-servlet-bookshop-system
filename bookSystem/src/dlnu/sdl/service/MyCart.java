package dlnu.sdl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import dlnu.sdl.domain.Book;

public class MyCart {

	HashMap<String,Book> hm = new HashMap<String ,Book>();
	 
	//�����ĵڶ��ַ���
	public void addBook2(String id){
		
		if(hm.containsKey(id)){
			Book book = hm.get(id);
			int shoppingNum = book.getShoppingNum();
			book.setShoppingNum(shoppingNum+1);
			
		}else{
			hm.put(id, new BookService().getBookById(id));
		}
		
	}
	
	
	//���ַ�ʽ�����⣬ÿ����Ӷ����˷�һ��BookBean��
	public void add(String id, Book book){
		if(hm.containsKey(id)){
			book = hm.get(id);
			int shoppingNum = book.getShoppingNum();
			book.setShoppingNum(shoppingNum+1);
			hm.put(id, book); 
			
		}else{
			hm.put(id, book);
		}
	}
	
	public void delBook(String id){
		hm.remove(id);
	}
	
	public void updateBook(String id,String nums){
		
		Book book = hm.get(id);
		book.setShoppingNum(Integer.parseInt(nums));
		
	}
	
	@SuppressWarnings({"unchecked" })
	public ArrayList showMyCart(){
		ArrayList<Book> al = new ArrayList<Book>();
		Iterator iterator = hm.keySet().iterator();
		while(iterator.hasNext()){
			
			String id =(String)iterator.next();
			
			Book book = hm.get(id);
			al.add(book);
		}
		return al;
	}
	
	public void clearBook(){
		hm.clear();
	}
	//���ظù��ﳵ���ܼ۸�
	@SuppressWarnings({ "unused", "unchecked" })
	public  float getTotalPrice(){
		
		float totalPrice = 0.0f;
		ArrayList<Book> al = new ArrayList<Book>();
		Iterator iterator = hm.keySet().iterator();
		while(iterator.hasNext()){
			String bookId = (String) iterator.next();
			Book book = hm.get(bookId);
			totalPrice+=book.getPrice()*book.getShoppingNum();
		}
		return totalPrice;
	}
	
	
}
