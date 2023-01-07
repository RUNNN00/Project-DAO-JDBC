package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("==== TEST 1: Seller findById ====");
		Seller seller = sellerDao.findById(2);
		System.out.println(seller + "\n");
		
		System.out.println("==== TEST 1: Seller findByDepartment ====");
		List<Seller> sellers = sellerDao.findByDepartment(new Department(2, null));
		for (Seller obj : sellers) {
			System.out.println(obj.toString());
		}
		System.out.println();
		
		System.out.println("==== TEST 1: Seller findAll ====");
		sellers = sellerDao.findAll();
		for (Seller obj : sellers) {
			System.out.println(obj.toString());
		}
		System.out.println();
		
		System.out.println("==== TEST 1: Seller insert ====");
		Seller newSeller = new Seller(null, "Gregson", "greg@gmail.com", new Date(), 2500.00, new Department(2, null));
		sellerDao.insert(newSeller);
		System.out.println("Inserted! new id: " + newSeller.getId());
	}
}