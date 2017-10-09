
package com.softserve.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.softserve.domain.Item;

@Component
public class ItemDaoImp implements BaseDao<Item> {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertItem;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertItem = new SimpleJdbcInsert(dataSource).withTableName("Item");
    }

    @Override
    public List<Item> findAll() {
        String sql = "SELECT * FROM Item";
        return jdbcTemplate.query(sql,
                (ResultSet rs, int rowNum) -> new Item(rs.getInt("Id"), rs.getString("Text"), rs.getString("State")));
    }

    @Override
    public void save(Item item) {
        Map<String, Object> data = new HashMap<>(2);
        data.put("text", item.getText());
        data.put("state", item.getState());
        insertItem.execute(data);
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
        String sql = "SELECT * FROM item WHERE Id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id},
                (ResultSet rs, int rowNum) -> new Item(rs.getInt("Id"), rs.getString("text"), rs.getString("state")));
    }
}
