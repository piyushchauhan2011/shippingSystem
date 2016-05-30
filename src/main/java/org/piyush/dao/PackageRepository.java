package org.piyush.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.piyush.models.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

@Repository
public class PackageRepository  {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected JdbcTemplate jdbc;

	public List<Package> getAllPackages() {
		List<Package> packages = this.jdbc.query(
		        "select id, order_id, status, tracking_number, delivery_address from packages",
		        packageMapper);
		return packages;
	}
	
	public Package getPackageById(long id) {
		Package p = this.jdbc.queryForObject(
				"select id, order_id, status, tracking_number, delivery_address from packages where id=?",
				packageMapper, id);
		return p;
	}
	
	public Package insertPackage(final Package p) {	    
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		this.jdbc.update(new PreparedStatementCreator() {
		    @Override
		    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		        PreparedStatement statement = con.prepareStatement("insert into packages(order_id, status, tracking_number, delivery_address) values(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		        statement.setLong(1, p.getOrderId());
		        statement.setString(2, "in-transit");
		        statement.setString(3, p.getRandomHexString(32));
		        statement.setString(4, p.getDeliveryAddress());
		        return statement;
		    }
		}, holder);
		long primaryKey = holder.getKey().longValue();
		Package packag = this.getPackageById(primaryKey);
		return packag;
	}
	
	public Package updatePackage(long id, final Package p) {
		this.jdbc.update("update packages set status = ? where id = ?", p.getStatus(), id);
		Package packag = this.getPackageById(id);
		return packag;
	}
	
	public long deletePackage(long id) {
		return this.jdbc.update("delete from packages where id = ?", id);
	}
	
	private static final RowMapper<Package> packageMapper = new RowMapper<Package>() {
        public Package mapRow(ResultSet rs, int rowNum) throws SQLException {
            Package p = new Package();
            p.setId(rs.getInt("id"));
            p.setOrderId(rs.getInt("order_id"));
            p.setStatus(rs.getString("status"));
            p.setTrackingNumber(rs.getString("tracking_number"));
            p.setDeliveryAddress(rs.getString("delivery_address"));
            return p;
        }
    };
}
