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
	}
}