package com.jdbc.demo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;
import com.jdbc.demo.service.SignUpService;

@Component
public class SignUpDao implements SignUpService {
	Response res = new Response();
	String url = "jdbc:mysql://127.0.0.1:3306/kgm";
	String username = "root";
	String password = "TharaThiru123";

	public Response createUser(SignUpModel values) {
		String uuid = UUID.randomUUID().toString();
		values.setsNo(uuid);
		values.setCreatedBy(uuid);
		values.setUpdatedBy(uuid);		
		values.setIs_active(1);

		Date date = new Date(Calendar.getInstance().getTime().getTime());
		values.setCreatedDate(date);
		values.setUpdatedDate(date);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {
				String insertQuerry = "INSERT INTO kgm.user_details(s_no,first_name,last_name,email,dob,gender,mobile_number,pwd,created_by,updated_by,created_date,updated_date,is_active)"
						+ "VALUES('" + values.getsNo() + "','" + values.getFirstName() + "','" + values.getLastName()
						+ "','" + values.getEmail() + "','" + values.getDob() + "','" + values.getGender() + "',"
						+ values.getMobileNumber() + ",'" + values.getPwd() + "','" + values.getCreatedBy() + "','"
						+ values.getUpdatedBy() + "','" + values.getCreatedDate() + "','" + values.getUpdatedDate()
						+ "','" + values.getIs_active()+ "')";
				System.out.println(insertQuerry);
				st.executeUpdate(insertQuerry);

				res.setData("user created successfully");
				res.setResponseCode(200);
				res.setResponseMessage("Success");

			} catch (Exception e) {
				e.printStackTrace();
				res.setData("Cannot create user!");
				res.setResponseCode(500);
				res.setResponseMessage("Error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setData("Driver class error");
			res.setResponseCode(500);
			res.setResponseMessage("Error");
		}

		return res;
	}

//get for admin//
	@SuppressWarnings("unchecked")
	public Response getUser() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String selectQuerry = "select * from user_details where is_active=1;";
			JSONArray jsonArray = new JSONArray();
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(selectQuerry);) {
				while (rs.next()) {
					JSONObject jsonobject = new JSONObject();
					jsonobject.put("s_no", rs.getObject("s_no"));
					jsonobject.put("firstName", rs.getObject("first_name"));
					jsonobject.put("Last_Name", rs.getObject("last_name"));
					jsonobject.put("Email", rs.getObject("email"));
					jsonobject.put("Dob", rs.getObject("dob"));
					jsonobject.put("Gender", rs.getObject("gender"));
					jsonobject.put("Mobile_Number", rs.getObject("mobile_number"));
					jsonobject.put("Pwd", rs.getObject("pwd"));

					jsonArray.add(jsonobject);
				}
				res.setData("fetch data successfully");
				res.setjData(jsonArray);
				res.setResponseCode(200);
				res.setResponseMessage("Success");

			} catch (Exception e) {
				res.setData("not fetch data");
				res.setResponseCode(500);
				res.setResponseMessage("Error");
			}

		} catch (Exception e) {
			res.setData("Driver class error");
			res.setResponseCode(500);
			res.setResponseMessage("Error");
		}
		return res;
	}

	// getOne
	@SuppressWarnings("unchecked")
	public Response getOneUser(String s_no) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String selectQuerry = "select * from user_details where s_no='" + s_no + "';";
			JSONObject jsonobject = new JSONObject();

			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(selectQuerry)) {

				while (rs.next()) {

					jsonobject.put("s_no", rs.getObject("s_no"));
					jsonobject.put("firstName", rs.getObject("first_name"));
					jsonobject.put("last_Name", rs.getObject("last_name"));
					jsonobject.put("email", rs.getObject("email"));
					jsonobject.put("dob", rs.getObject("dob"));
					jsonobject.put("gender", rs.getObject("gender"));
					jsonobject.put("mobile_Number", rs.getObject("mobile_number"));
					jsonobject.put("password", rs.getObject("password"));

				}
				res.setData(" get one data successfully");
				res.setResponseCode(200);
				res.setResponseMessage("Success");
				res.setjData(jsonobject);

			} catch (Exception e) {
				res.setData("not get data");
				res.setResponseCode(500);
				res.setResponseMessage("Error");
			}

		} catch (Exception e) {
			res.setData("Driver class error");
			res.setResponseCode(500);
			res.setResponseMessage("Error");
		}
		return res;
	}

	// update email//

	public Response updateUser(String s_no, String email) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String UpdateQuerry = "update user_details set email='" + email + "'where s_no='" + s_no + "';";

			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {
				st.executeUpdate(UpdateQuerry);
				res.setData("email updated successfully");
				res.setResponseCode(200);
				res.setResponseMessage("Success");

			} catch (Exception e) {
				res.setData("email not updated");
				res.setResponseCode(500);
				res.setResponseMessage("Error");
			}

		} catch (Exception e) {
			res.setData("Driver class error");
			res.setResponseCode(500);
			res.setResponseMessage("Error");
		}
		return res;
	}

	// delete
	public Response deleteUser(String s_no) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String DeleteQuerry = "delete from user_details where s_no='" + s_no + "';";
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {

				st.executeUpdate(DeleteQuerry);
				res.setjData("Deleted data");
				res.setResponseCode(200);
				res.setResponseMessage("Success");
			} catch (Exception e) {
				res.setjData("Not deleted data");
				res.setResponseCode(500);
				res.setResponseMessage("Error");
			}
		} catch (Exception e) {
			res.setData("Driver class error");
			res.setResponseCode(500);
			res.setResponseMessage("Error");
		}
		return res;
	}

	// scam-put//
	public Response scamUser(String s_no) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String ScamQuerry = "update user_details set is_active=0 where s_no='" + s_no + "';";
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {
				st.executeUpdate(ScamQuerry);
				res.setjData("User can be soft deleted");
				res.setResponseCode(200);
				res.setResponseMessage("Success");
			} catch (Exception e) {
				res.setjData("User cannot be soft deleted");
				res.setResponseCode(500);
				res.setResponseMessage("Error");
			}
		} catch (Exception e) {
			res.setData("Driver class error");
			res.setResponseCode(500);
			res.setResponseMessage("Error");
		}
		return res;
	}

	// login
	public Response login(String email, String pwd) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String loginQuerry = "select * from user_details where email='" + email + "'and  pwd='" + pwd + "'";
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(loginQuerry);) {
				String result;
				if (rs.next()) {
					result = "Existing user";
				} else {
					result = "No such users found";
				}

				res.setData(result);
				res.setResponseCode(200);
				res.setResponseMessage("Success");
			} catch (Exception e) {
				res.setjData("Unable to login");
				res.setResponseCode(500);
				res.setResponseMessage("Error");
			}
		} catch (Exception e) {
			res.setData("Driver class error");
			res.setResponseCode(500);
			res.setResponseMessage("Error");
		}
		return res;
	}

}
