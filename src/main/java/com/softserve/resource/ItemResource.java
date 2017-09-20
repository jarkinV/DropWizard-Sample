
package com.softserve.resource;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.softserve.dao.ItemDaoImp;
import com.softserve.domain.Item;

import io.swagger.annotations.Api;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Api("Item")
@Controller
public class ItemResource {

    @Autowired
    private ItemDaoImp itemDao;

    @PUT
    public ResponseEntity<Object> saveItem(Item item) {
        return (itemDao.save(item)) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GET
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<List<Item>>(itemDao.findAll(), HttpStatus.OK);
    }

    @GET
    @Path("/{id}")
    public ResponseEntity<Item> getOneItemById(@PathParam("id") int id) {
        return new ResponseEntity<Item>(itemDao.findOne(id), HttpStatus.OK);
    }

    @DELETE
    @Path("/{id}")
    public ResponseEntity<Object> removeItemById(@PathParam("id") int id) {
        return (itemDao.removeById(id)) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DELETE
    public ResponseEntity<Object> removeItem(Item item) {
        return (itemDao.remove(item)) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
