package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("==== TEST 1: Department findById ====");
		Department dep = departmentDao.findById(2);
		System.out.println(dep + "\n");
		
		System.out.println("==== TEST 2: Department findAll ====");
		List<Department> departments = departmentDao.findAll();
		for (Department obj : departments) {
			System.out.println(obj);
		}
		
		System.out.println("\n==== TEST 3: Department insert ====");
		Department dep2 = new Department(null, "Public Relationship");
		departmentDao.insert(dep2);
		System.out.println("New department inserted: " + dep2);
		
		System.out.println("\n==== TEST 4: Department delete ====");
		departmentDao.deleteById(14);
		System.out.println("delete test completed\n");
		
		System.out.println("==== TEST 5: Department update ====");
		dep2.setName("Designer");
		dep2.setId(2);
		departmentDao.update(dep2);
		System.out.println("Update department completed");
	}
}