package com.entor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entor.dao.StudentDao;
import com.entor.dao.impl.StudentDaoImpl;
import com.entor.entity.Student;


@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String opType = request.getParameter("opType");
		switch (opType) {
		case "loginForm":
			loginForm(request, response);
			break;
		case "login":
			login(request, response);
			break;
		case "logout":
			logout(request, response);
			break;
		case "addForm":
			addForm(request, response);
			break;
		case "add":
			add(request, response);
			break;
		case "delete":
			delete(request, response);
			break;
		case "deleteMore":
			deleteMore(request, response);
			break;
		case "queryById":
			queryById(request, response);
			break;
		case "update":
			update(request, response);
			break;
		case "queryByPage":
			queryByPage(request, response);
			break;
		case "queryAll":
			queryAll(request, response);
			break;
		default:
			queryByPage(request, response);
			break;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected void loginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.write("<html><head><title>用户登录</title></head><body>");
		out.write("<form action='StudentServlet?opType=login' method='post'>");
		out.write("账号：<input type='text' name='username'/><br/>");
		out.write("密码：<input type='password' name='password'/><br/>");
		out.write("<input type='submit' value='登录'/>&nbsp;&nbsp;没有账号？<a href='StudentServlet?opType=addForm'>注册用户</a><br/>");
		out.write("</form>");
		out.write("</body></html>");
		out.flush();
		out.close();
	}
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		StudentDao dao = new StudentDaoImpl();
		Student s = dao.login(username, password);
		if(s!=null) {
			//登录成功
			System.out.println("登录成功");
			//保存用户登录信息到会话中
			request.getSession().setAttribute("s", s);
			//分页查询所有记录
			queryByPage(request, response);
		}else {
			//登录失败重新回到登录页面
			loginForm(request, response);
		}
	}
	protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//清除保存在会话中的所有数据
		request.getSession().invalidate();
		//跳转到login页面
		loginForm(request, response);
	}
	protected void addForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.write("<html><head><title>用户注册</title></head><body>");
		out.write("<form action='StudentServlet?opType=add' method='post'>");
		out.write("姓名：<input type='text' name='name'/><br/>");
		out.write("账号：<input type='text' name='username'/><br/>");
		out.write("密码：<input type='password' name='password'/><br/>");
		out.write("性别：<input type='radio' name='sex' value='1' checked='checked'/>男<input type='radio' name='sex' value='0'/>女<br/>");
		out.write("年龄：<input type='text' name='age'/><br/>");
		out.write("生日：<input type='date' name='birthday'/><br/>");
		out.write("<input type='submit' value='注册'/>&nbsp;&nbsp;已有账号？<a href='StudentServlet?opType=loginForm'>登录</a><br/>");
		out.write("</form>");
		out.write("</body></html>");
		out.flush();
		out.close();
	}
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取页面传递过来的参数
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String age = request.getParameter("age");
		String birthday = request.getParameter("birthday");
		//封装参数到对象中
		Student s = new Student();
		s.setName(name);
		s.setUsername(username);
		s.setPassword(password);
		//把字符串类型装换成整形
		s.setSex(Integer.parseInt(sex));
		s.setAge(Integer.parseInt(age));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//把字符串类型装换成日期类型
			s.setBirthday(sdf.parse(birthday));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//调用dao保存数据
		StudentDao dao = new StudentDaoImpl();
		dao.add(s);
		//页面跳转
		loginForm(request, response);
	}
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取页面传递过来的参数
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String age = request.getParameter("age");
		String birthday = request.getParameter("birthday");
		//封装参数到对象中
		Student s = new Student();
		s.setId(Integer.parseInt(id));
		s.setName(name);
		s.setUsername(username);
		s.setPassword(password);
		//把字符串类型装换成整形
		s.setSex(Integer.parseInt(sex));
		s.setAge(Integer.parseInt(age));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//把字符串类型装换成日期类型
			s.setBirthday(sdf.parse(birthday));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//调用dao更新数据
		StudentDao dao = new StudentDaoImpl();
		dao.update(s);
		//页面跳转到分页查询列表页面
		queryByPage(request, response);
	}
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		StudentDao dao = new StudentDaoImpl();
		dao.deleteById(Integer.parseInt(id));
		queryByPage(request, response);
	}
	protected void deleteMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ids = request.getParameter("ids");
		StudentDao dao = new StudentDaoImpl();
		dao.deleteMore(ids);
		queryByPage(request, response);
	}
	protected void queryById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String currentPage = request.getParameter("currentPage");
		StudentDao dao = new StudentDaoImpl();
		Student s = dao.queryById(Integer.parseInt(id));
		PrintWriter out = response.getWriter();
		out.write("<html><head><title>用户修改</title></head><body>");
		out.write("<form action='StudentServlet?opType=update' method='post'>");
		//用隐藏域传递参数值，修改需要编号、修改后跳到原来的页面需要传递页码参数，这两个参数不需要在页面上显示，所以使用隐藏域
		out.write("<input type='hidden' name='id' value='"+s.getId()+"'/>");
		out.write("<input type='hidden' name='currentPage' value='"+currentPage+"'/>");
		out.write("姓名：<input type='text' name='name' value='"+s.getName()+"'/><br/>");
		out.write("账号：<input type='text' name='username' value='"+s.getUsername()+"'/><br/>");
		out.write("密码：<input type='password' name='password' value='"+s.getPassword()+"'/><br/>");
		if(s.getSex()==1) {
			out.write("性别：<input type='radio' name='sex' value='1' checked='checked'/>男<input type='radio' name='sex' value='0'/>女<br/>");
		}else {
			out.write("性别：<input type='radio' name='sex' value='1'/>男<input type='radio' name='sex' value='0' checked='checked'/>女<br/>");
		}
		out.write("年龄：<input type='text' name='age' value='"+s.getAge()+"'/><br/>");
		out.write("生日：<input type='date' name='birthday' value='"+s.getBirthday()+"'/><br/>");
		out.write("<input type='submit' value='修改'/><br/>");
		out.write("</form>");
		out.write("</body></html>");
		out.flush();
		out.close();
	}
	protected void queryByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取页面上传递过来的页码
		String currentPage = request.getParameter("currentPage");
		StudentDao dao = new StudentDaoImpl();
		//当前页
		int sp = 1;
		//总记录数
		int totals = dao.getTotals();
		//每页显示记录数
		int pageSize = 20;
		//总页数
		int pageCounts = 0;
		//用异常捕获，避免页码传递错误页码
		try {
			sp = Integer.parseInt(currentPage);
		}catch(Exception e) {
			sp = 1;
		}
		pageCounts = totals/pageSize;
		if(totals%pageSize!=0) {
			pageCounts++;
		}
		if(sp>pageCounts) {
			sp = pageCounts;
		}
		if(sp<1) {
			sp = 1;
		}
		List<Student> list = dao.queryByPage(sp, pageSize);
		
		PrintWriter out = response.getWriter();
		out.write("<html><head><title>用户注册</title>");
		out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\"/>");
		out.write("<script src=\"js/jquery-1.7.2.js\" type=\"text/javascript\" charset=\"utf-8\"></script>");
		out.write("<script src=\"js/myjs.js\" type=\"text/javascript\" charset=\"utf-8\"></script>");
		out.write("</head><body><center>");
		//从会话中获取当前登录的用户对象
		Object o = request.getSession().getAttribute("s");
		if(o!=null) {
			Student stu = (Student)o;
			out.write("欢迎【"+stu.getName()+"】&nbsp;&nbsp;<a href='StudentServlet?opType=logout'>注销用户</a>");
		}
		out.write("<table border='1'>");
		out.write("<tr><th><input type='checkbox' id='all' onchange='checkAll();'/>全选</th><th>学生编号</th><th>学生姓名</th><th>学生账号</th><th>学生密码</th><th>学生性别</th><th>学生年龄</th><th>学生出生日期</th><th>创建时间</th><th>操作&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteMore("+sp+");'>批量删除</a></th></tr>");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Student s:list) {
			out.write("<tr onmouseover='mouseOver(this);' onmouseout='mouseOut(this);'>");
			out.write("<td><input type='checkbox' name='student' value='"+s.getId()+"'/></td>");
			out.write("<td>"+s.getId()+"</td>");
			out.write("<td>"+s.getName()+"</td>");
			out.write("<td>"+s.getUsername()+"</td>");
			out.write("<td>"+s.getPassword()+"</td>");
			out.write("<td>"+(s.getSex()==1?"男":"女")+"</td>");
			out.write("<td>"+s.getAge()+"</td>");
			out.write("<td>"+s.getBirthday()+"</td>");
			out.write("<td>"+sdf.format(s.getCreateTime())+"</td>");
			out.write("<td><a href='StudentServlet?opType=queryById&currentPage="+sp+"&id="+s.getId()+"'>修改</a>&nbsp;|&nbsp;<a href='javascript:void(0);' onclick='deleteById("+sp+","+s.getId()+");'>删除</a></td>");
			out.write("</tr>");
		}
		out.write("</table>");
		out.write("总记录数："+totals+"&nbsp;&nbsp;总页数："+pageCounts+"&nbsp;&nbsp;当前页："+sp+"&nbsp;&nbsp;");
		out.write("<a href='StudentServlet?opType=queryByPage&currentPage=1'>首页</a>&nbsp;&nbsp;");
		out.write("<a href='StudentServlet?opType=queryByPage&currentPage="+(sp-1)+"'>上一页</a>&nbsp;&nbsp;");
		out.write("<form action='StudentServlet?opType=queryByPage' method='post' style='display:inline;'>");
		out.write("<input type='text' name='currentPage' value='"+sp+"' size='3'/>");
		out.write("</form>&nbsp;&nbsp;");
		out.write("<a href='StudentServlet?opType=queryByPage&currentPage="+(sp+1)+"'>下一页</a>&nbsp;&nbsp;");
		out.write("<a href='StudentServlet?opType=queryByPage&currentPage="+pageCounts+"'>尾页</a>&nbsp;&nbsp;");
		out.write("<form action='UploadServlet' method='post' enctype='multipart/form-data' style='display:inline;'>");
		out.write("<input type='file' name='file'/>");
		out.write("<input type='submit' value='导入数据'/>");
		out.write("</form>&nbsp;&nbsp;<a href='DownloadServlet'>导出数据</a>");
		out.write("</center></body></html>");
		out.flush();
		out.close();
		
	}
	protected void queryAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
