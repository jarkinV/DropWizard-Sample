package com.softserve.controller;

import com.codahale.metrics.annotation.Timed;
import com.softserve.domain.Item;
import com.softserve.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class ItemController {

    @Autowired
    private Test test;

    @GET
    @Timed
    public Item getItem(){
        String t = test.getTest();
        return new Item(2L, t);
    }
}
