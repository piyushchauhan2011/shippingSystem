package org.piyush.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.piyush.models.PackageItem;
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
public class PackageItemRepository  {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected JdbcTemplate jdbc;

	public List<PackageItem> getAllPackageItems() {
		List<PackageItem> packageItems = this.jdbc.query(
		        "select id, title, description, quantity, price from package_items",
		        packageItemMapper);
		return packageItems;
	}
	
	public PackageItem getPackageItemById(long id) {
		PackageItem pi = this.jdbc.queryForObject(
				"select id, title, description, quantity, price from package_items where id=?",
				packageItemMapper, id);
		return pi;
	}
	
	public Package insertPackageItem(final PackageItem pi, Package p) {
		PackageItem oldPi = p.isPresent(pi);
		if (oldPi != null) {
			// Update the quantity
			oldPi.increaseQuantity();
			this.jdbc.update("update package_items set quantity = ? where id = ?", oldPi.getQuantity(), oldPi.getId());
		} else {
			// Insert as a new cartItem
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			this.jdbc.update(new PreparedStatementCreator() {
			    @Override
			    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			        PreparedStatement statement = con.prepareStatement("insert into package_items(package_id, item_id, title, description, quantity, price) values(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			        statement.setLong(1, p.getId());
			        statement.setLong(2, pi.getItemId());
			        statement.setString(3, pi.getTitle());
			        statement.setString(4, pi.getDescription());
			        statement.setLong(5, 1);
			        statement.setDouble(6, pi.getPrice());
			        return statement;
			    }
			}, holder);
			long primaryKey = holder.getKey().longValue();
			oldPi = this.getPackageItemById(primaryKey);
		}
		
		p.insertPackageItem(oldPi);
		return p;
	}
	
	public Package deletePackageItem(Package p, long id) {
		PackageItem pi = p.findPackageItemById(id);
		if (pi != null) {
			// Update the quantity
			pi.decreaseQuantity();
			this.jdbc.update("update package_items set quantity = ? where id = ?", pi.getQuantity(), pi.getId());
		}
		
		p.insertPackageItem(pi);
		return p;
	}
	
	private static final RowMapper<PackageItem> packageItemMapper = new RowMapper<PackageItem>() {
        public PackageItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            PackageItem pi = new PackageItem();
            pi.setId(rs.getInt("id"));
            pi.setTitle(rs.getString("title"));
            pi.setDescription(rs.getString("description"));
            pi.setPrice(rs.getDouble("price"));
            pi.setQuantity(rs.getInt("quantity"));
            return pi;
        }
    };
}
