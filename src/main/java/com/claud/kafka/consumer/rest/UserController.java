package com.claud.kafka.consumer.rest;

import com.claud.kafka.consumer.vo.CustomerAccountInfo;
import com.claud.kafka.consumer.vo.ResponseError;

import static com.claud.kafka.JsonUtil.restJson;
import static com.claud.kafka.JsonUtil.toRestJson;
import static spark.Spark.*;


public class UserController {
    public UserController(final UserService userService) {

        get("/users", (req, res) -> userService.getAllUsers(), restJson());

        get("/users/:id", (req, res) -> {
            String id = req.params(":id");
            CustomerAccountInfo user = userService.getUser(id);
            if (user != null) {
                return user;
            }
            res.status(400);
            return new ResponseError("No user with id '%s' found", id);
        }, restJson());


        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toRestJson(new ResponseError(e)));
        });
    }
}
