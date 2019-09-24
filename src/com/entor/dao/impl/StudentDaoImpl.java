package com.entor.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.entor.dao.StudentDao;
import com.entor.entity.Student;
import com.entor.util.DBUtil;

public class StudentDaoImpl implements StudentDao{

	@Override
	public Student login(String username, String password) {
		Student s = null;
		Connection con = DBUtil.getConnection();
		String sql = "select * from student where username=? and password=?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if(rs.next()) {
				s = new Student();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				s.setUsername(rs.getString("username"));
				s.setPassword(rs.getString("password"));
				s.setSex(rs.getInt("sex"));
				s.setAge(rs.getInt("age"));
				s.setBirthday(rs.getDate("birthday"));
				s.setCreateTime(rs.getTimestamp("createTime"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(con, pst, rs);
		}
		return s;
	}

	@Override
	public void add(Student s) {
		Connection con = DBUtil.getConnection();
		String sql = "insert into student(id,name,username,password,sex,age,birthday,createTime) values(stu_seq.nextval,?,?,?,?,?,?,sysdate)";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, s.getName());
			pst.setString(2, s.getUsername());
			pst.setString(3, s.getPassword());
			pst.setInt(4, s.getSex());
			pst.setInt(5, s.getAge());
			pst.setDate(6, new Date(s.getBirthday().getTime()));
			System.out.println("成功新增"+pst.executeUpdate()+"条数据。");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(con, pst, null);
		}
	}

	@Override
	public List<Student> queryByPage(int currentPage, int pageSize) {
		List<Student> list = new ArrayList<Student>();
		Connection con = DBUtil.getConnection();
		String sql = "select ss.* from (select s.*,rownum r from (select * from student order by id) s where rownum<=?) ss where ss.r>?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, currentPage*pageSize);
			pst.setInt(2, (currentPage-1)*pageSize);
			rs = pst.executeQuery();
			while(rs.next()) {
				Student s = new Student();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				s.setUsername(rs.getString("username"));
				s.setPassword(rs.getString("password"));
				s.setSex(rs.getInt("sex"));
				s.setAge(rs.getInt("age"));
				s.setBirthday(rs.getDate("birthday"));
				s.setCreateTime(rs.getTimestamp("createTime"));
				list.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(con, pst, rs);
		}
		return list;
	}

	@Override
	public int getTotals() {
		Connection con = DBUtil.getConnection();
		String sql = "select count(*) from student";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(con, pst, rs);
		}
		return 0;
	}

	@Override
	public void deleteById(int id) {
		Connection con = DBUtil.getConnection();
		String sql = "delete from student where id=?";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			System.out.println("成功删除"+pst.executeUpdate()+"条数据。");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(con, pst, null);
		}
	}

	@Override
	public Student queryById(int id) {
		Student s = null;
		Connection con = DBUtil.getConnection();
		String sql = "select * from student where id=?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if(rs.next()) {
				s = new Student();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				s.setUsername(rs.getString("username"));
				s.setPassword(rs.getString("password"));
				s.setSex(rs.getInt("sex"));
				s.setAge(rs.getInt("age"));
				s.setBirthday(rs.getDate("birthday"));
				s.setCreateTime(rs.getTimestamp("createTime"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(con, pst, rs);
		}
		return s;
	}

	@Override
	public void update(Student s) {
		Connection con = DBUtil.getConnection();
		String sql = "update student set name=?,username=?,password=?,sex=?,age=?,birthday=?,createTime=sysdate where id=?";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, s.getName());
			pst.setString(2, s.getUsername());
			pst.setString(3, s.getPassword());
			pst.setInt(4, s.getSex());
			pst.setInt(5, s.getAge());
			pst.setDate(6, new Date(s.getBirthday().getTime()));
			pst.setInt(7, s.getId());
			System.out.println("成功更新"+pst.executeUpdate()+"条数据。");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(con, pst, null);
		}
	}

	@Override
	public void deleteMore(String ids) {
		Connection con = DBUtil.getConnection();
		String sql = "delete from student where id in ("+ids+")";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql);
			System.out.println("成功删除"+pst.executeUpdate()+"条数据。");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(con, pst, null);
		}
	}

	@Override
	public void addMore(List<Student> list) {
		Connection con = DBUtil.getConnection();
		String sql = "insert into student(id,name,username,password,sex,age,birthday,createTime) values(stu_seq.nextval,?,?,?,?,?,?,sysdate)";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql);
			for(int i=0;i<list.size();i++) {
				Student s = list.get(i);
				pst.setString(1, s.getName());
				pst.setString(2, s.getUsername());
				pst.setString(3, s.getPassword());
				pst.setInt(4, s.getSex());
				pst.setInt(5, s.getAge());
				pst.setDate(6, new Date(s.getBirthday().getTime()));
				pst.addBatch();
				if(i%100==0) {
					pst.executeBatch();
					pst.clearBatch();
				}
			}
			pst.executeBatch();
			pst.clearBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(con, pst, null);
		}
	}

	@Override
	public List<Student> queryAll() {
		List<Student> list = new ArrayList<Student>();
		Connection con = DBUtil.getConnection();
		String sql = "select * from student order by id";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				Student s = new Student();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				s.setUsername(rs.getString("username"));
				s.setPassword(rs.getString("password"));
				s.setSex(rs.getInt("sex"));
				s.setAge(rs.getInt("age"));
				s.setBirthday(rs.getDate("birthday"));
				s.setCreateTime(rs.getTimestamp("createTime"));
				list.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(con, pst, rs);
		}
		return list;
	}

}
