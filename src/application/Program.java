package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

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
		
		System.out.println("==== TEST 2: Seller findByDepartment ====");
		List<Seller> sellers = sellerDao.findByDepartment(new Department(2, null));
		for (Seller obj : sellers) {
			System.out.println(obj.toString());
		}
		System.out.println();
		
		System.out.println("==== TEST 3: Seller findAll ====");
		sellers = sellerDao.findAll();
		for (Seller obj : sellers) {
			System.out.println(obj.toString());
		}
		System.out.println();
		
		System.out.println("==== TEST 4: Seller insert ====");
		Seller newSeller = new Seller(null, "Gregson", "greg@gmail.com", new Date(), 2500.00, new Department(2, null));
		sellerDao.insert(newSeller);
		System.out.println("Inserted! new id: " + newSeller.getId() + "\n");
		
		System.out.println("==== TEST 5: Seller Update ====");
		newSeller = sellerDao.findById(1);
		newSeller.setName("Giovanni");
		sellerDao.update(newSeller);
		System.out.println("Update completed\n");
		
		System.out.println("==== TEST 6: Seller delete ====");
		System.out.print("Enter id for delete test: ");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete completed");
		
		sc.close();
	}
}