package com.claud.kafka.consumer.rest;

import com.claud.kafka.consumer.vo.ResponseError;
import com.claud.kafka.consumer.vo.UserProfile;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;
import org.javasimon.utils.SimonUtils;

import java.util.List;

import static com.claud.kafka.JsonUtil.restJson;
import static com.claud.kafka.JsonUtil.toRestJson;
import static spark.Spark.*;


public class UserController {
    public UserController(final UserService userService) {

        get("/users", (req, res) -> {

            Stopwatch stopwatch = SimonManager.getStopwatch(userService.getStopWatchName(SimonUtils.generateName()));
            Split split = stopwatch.start();

            List<UserProfile> profileList = userService.getAllUsers();

            split.stop();

            return profileList;

        }, restJson());

        get("/users/:id", (req, res) -> {
            String id = req.params(":id");

            Stopwatch stopwatch = SimonManager.getStopwatch(userService.getStopWatchName(SimonUtils.generateName()));
            Split split = stopwatch.start();

            UserProfile user = userService.getUser(id);

            split.stop();
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
