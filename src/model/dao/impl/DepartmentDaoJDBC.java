package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department obj) {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			conn.setAutoCommit(false);
			
			pst = conn.prepareStatement("insert into department (Name) values (?)", Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, obj.getName());
			
			int rows = pst.executeUpdate();
			if (rows > 0) {
				rs = pst.getGeneratedKeys();
				if (rs.next())
					obj.setId(rs.getInt(1));
			}
			else {
				throw new DbException("Unexpected Error! no rows affected");
			}
			conn.commit();
			conn.setAutoCommit(true);
		}
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back caused by " + e.getMessage());
			}
			catch (SQLException rollBackError) {
				throw new DbException("Error trying to rollback caused by " + rollBackError.getMessage());
			}
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}		
	}

	@Override
	public void update(Department obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Department findById(Integer id) {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("select * from department where Id = ?");
			
			pst.setInt(1, id);
			
			rs = pst.executeQuery();
			
			if (rs.next())
				return createDepartment(rs);
			else
				throw new DbException("Unexpected error! no rows affected");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}		
	}

	@Override
	public List<Department> findAll() {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Department> departments = new ArrayList<>();
		
		try {
			pst = conn.prepareStatement("select * from department order by Id");
			rs = pst.executeQuery();
			while (rs.next()) {
				departments.add(createDepartment(rs));
			}
			
			return departments;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}		
	}

	private Department createDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}
}
