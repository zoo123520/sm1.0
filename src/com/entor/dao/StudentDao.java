package com.entor.dao;

import java.util.List;

import com.entor.entity.Student;

public interface StudentDao {

	/**
	 * 用户登录
	 * @param username	登录账号
	 * @param password	登录密码
	 * @return			登录成功的学生对象
	 */
	public Student login(String username,String password);
	/**
	 * 新增用户
	 * @param s
	 */
	public void add(Student s);
	/**
	 * 分页查询数据
	 * @param currentPage	当前页码
	 * @param pageSize		每页记录数
	 * @return				每页显示的记录对象
	 */
	public List<Student> queryByPage(int currentPage,int pageSize);
	/**
	 * 获取总记录数
	 * @return
	 */
	public int getTotals();
	/**
	 * 根据主键删除
	 * @param id	主键编号
	 */
	public void deleteById(int id);
	/**
	 * 根据主键查询
	 * @param id	主键编号
	 * @return		对象
	 */
	public Student queryById(int id);
	/**
	 * 更新数据
	 * @param s		用户对象
	 */
	public void update(Student s);
	/**
	 * 批量删除记录
	 * @param ids	由主机编号拼接成的字符串，用逗号隔开，格式是：1,2,3,4,5
	 */
	public void deleteMore(String ids);
	/**
	 * 批量新增用户
	 * @param list
	 */
	public void addMore(List<Student> list);
	/**
	 * 查询所有数据
	 * @return
	 */
	public List<Student> queryAll();
}
