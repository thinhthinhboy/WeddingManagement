package com.wedding.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wedding.databaseconnection.MySqlConnection;
import com.wedding.models.Service;

public class ServiceRepository {

	public List<Service> getAll() {

		String query = "SELECT * FROM SERVICE WHERE NOT isDeleted";

		Connection connection = MySqlConnection.getInstance().getConnection();
		List<Service> serviceList = new ArrayList<Service>();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				Service service = new Service();
				service.setServiceID(res.getInt("serviceID"));
				service.setServiceName(res.getString("serviceName"));
				service.setServicePrice(res.getInt("servicePrice"));
				service.setStartingDate(res.getString("startingDate"));
				service.setEndingDate(res.getString("endingDate"));
				service.setDeleted(res.getBoolean("isDeleted"));
				serviceList.add(service);
			}
			connection.close();
			return serviceList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void add(Service service) {
		String query = "INSERT INTO SERVICE(serviceName,servicePrice,startingDate,endingDate) VALUES (?,?,?,?)";
		Connection connection = MySqlConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, service.getServiceName());
			statement.setInt(2, service.getServicePrice());
			statement.setString(3, service.getStartingDate());
			statement.setString(4, service.getEndingDate());
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
