package org.piyush.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.piyush.models.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class PackageRepository  {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected JdbcTemplate jdbc;

	public List<Package> getAllPackages() {
		List<Package> packages = this.jdbc.query(
		        "select order_id, status, tracking_number, delivery_address from packages",
		        new RowMapper<Package>() {
		            public Package mapRow(ResultSet rs, int rowNum) throws SQLException {
		                Package p = new Package();
		                p.setOrderId(rs.getInt("order_id"));
		                p.setStatus(rs.getString("status"));
		                p.setTrackingNumber(rs.getString("tracking_number"));
		                p.setDeliveryAddress(rs.getString("delivery_address"));
		                return p;
		            }
		        });
		return packages;
	}
}
