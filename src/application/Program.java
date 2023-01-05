package application;

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
		
		System.out.println("==== TEST 1: Seller findById ====");
		List<Seller> sellers = sellerDao.findByDepartment(new Department(2, null));
		for (Seller obj : sellers) {
			System.out.println(obj.toString());
		}
		System.out.println();
	}
}