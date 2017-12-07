package com.example.demo.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by zuoqing on 2017/12/7.
 */
@RestController
@RequestMapping(value = "users")
//@Api(value="用户controller",tags={"用户操作接口"})
public class LoginController {

    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value={"getUserList"}, method=RequestMethod.GET)
    public List<User> getUserList() {
        List<User> r = new ArrayList<User>(users.values());
        User user = new User(111L,"zuoqing",18);
        r.add(user);
        return r;
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value="addUser", method=RequestMethod.POST)
    public String postUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return "success";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息", position = 1)
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public User getUser(@ApiParam(value = "请输入id")  @PathVariable Long id) {
        return users.get(id);
    }

    @ApiOperation(value = "Hellow World", notes = "测试功能", position = 1)
    @RequestMapping(value = { "/hello/{who}" }, method = RequestMethod.GET)
    public String hello(
            @ApiParam(value = "填写名称") @PathVariable("who") String who) {
        return "Hellow World";
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @RequestBody User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@ApiParam(value = "请输入id")  @PathVariable Long id) {
        users.remove(id);
        return "success";
    }

}
