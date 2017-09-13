package com.softserve.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.softserve.domain.Item;

@Component
public class ItemDaoImp implements BaseDao<Item> {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Item> findAll() {
		String sql = "SELECT * FROM Item";
		return jdbcTemplate.query(sql,
				(ResultSet rs, int rowNum) -> new Item(rs.getInt("Id"), rs.getString("Text"), rs.getString("State")));
	}

	@Override
	public boolean save(Item item) {
		String sql = "INSERT INTO Item (text, state) VALUES (?, ?)";
		int result = jdbcTemplate.update(sql, item.getText(), item.getState());
		return (result > 0) ? true : false;
	}

	@Override
	public boolean remove(Item item) {
		String sql = "DELETE FROM Item WHERE text = ? and state = ?";
		int result = jdbcTemplate.update(sql, item.getText(), item.getState());
		return (result > 0) ? true : false;
	}

	@Override
	public boolean removeById(int id) {
		String sql = "DELETE FROM Item WHERE id = ?";
		int result = jdbcTemplate.update(sql, id);
		return (result > 0) ? true : false;
	}

	@Override
	public Item findOne(int id) {
		String sql = "SELECT * FROM item WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { id },
				(ResultSet rs, int rowNum) -> new Item(rs.getInt("Id"), rs.getString("text"), rs.getString("state")));
	}
}
