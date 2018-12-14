package testspringboot.controller;

import org.springframework.web.bind.annotation.*;
import testspringboot.aspect.UserAccess;
import testspringboot.dao.User_zRespository;
import org.springframework.beans.factory.annotation.Autowired;
import testspringboot.entity.User_z;
import testspringboot.util.JsonUtil;

import java.util.List;
import java.util.Optional;


@RestController
public class User_zController {
    @Autowired
    User_zRespository user_zRespository;


    /**
     * 查询所有
     *
     * @return
     */
    // @RequestMapping(value = "/user",method = RequestMethod.GET)
    @GetMapping(value = "/user")
    public String user_zList() {
        return JsonUtil.object2Json(user_zRespository.findAll());
    }


    /**
     * 添加
     *
     * @param user_z
     * @return
     */
    @PostMapping(value = "/user")
    public User_z user_zAdd(User_z user_z) {
        user_z.setId(user_z.getId());
        user_z.setUsername(user_z.getUsername());
        user_z.setAge(user_z.getAge());
        user_z.setSex(user_z.getSex());
        user_z.setJob(user_z.getJob());
        return user_zRespository.save(user_z);
    }

    /**
     * 查询一个
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/user/{id}")
    public Optional<User_z> user_zFindById(@PathVariable("id") Integer id) {
        return user_zRespository.findById(id);
    }

    /**
     * 通过id删除
     *
     * @param id
     */
    @DeleteMapping(value = "/user/{id}")
    public void user_zDelete(@PathVariable("id") Integer id) {
        user_zRespository.deleteById(id);
    }


    /**
     * 更新
     *
     * @param user_z
     * @return
     */
    @PutMapping(value = "user/{id}")
    public User_z user_zUpdate(User_z user_z) {
        user_z.setId(user_z.getId());
        user_z.setUsername(user_z.getUsername());
        user_z.setSex(user_z.getSex());
        user_z.setAge(user_z.getAge());
        user_z.setJob(user_z.getJob());
        return user_zRespository.save(user_z);
    }
}
