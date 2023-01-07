package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.Statement;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = conn.prepareStatement("insert into seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "values " + "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			pst.setDouble(4, obj.getBaseSalary());
			pst.setInt(5, obj.getDepartment().getId());

			int rowsAffected = pst.executeUpdate();

			if (rowsAffected > 0) {
				rs = pst.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			} else {
				throw new DbException("Unexpected error! No rows affected");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = conn.prepareStatement(
					"select seller.*,department.Name as DepName "
							+ "from seller inner join department "
							+ "on seller.DepartmentId = department.Id "
							+ "where seller.Id = ?");

			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);
				return seller;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = conn.prepareStatement(
					"select seller.*, department.Name as DepName "
							+ "from seller inner join department "
							+ "on seller.DepartmentId = department.Id "
							+ "order by Name");

			rs = pst.executeQuery();
			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> departments = new HashMap<>();

			while (rs.next()) {
				Department dep = departments.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					departments.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);
				sellers.add(seller);
			}
			return sellers;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = conn.prepareStatement(
					"select seller.*, department.Name as DepName "
							+ "from seller inner join department "
							+ "on seller.DepartmentId = department.Id "
							+ "where DepartmentId = ? " + "order by Name");

			pst.setInt(1, department.getId());
			rs = pst.executeQuery();
			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> departments = new HashMap<>();

			while (rs.next()) {
				Department dep = departments.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					departments.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);
				sellers.add(seller);
			}
			return sellers;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep)
			throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setDepartment(dep);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
}