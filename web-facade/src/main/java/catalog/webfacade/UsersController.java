package catalog.webfacade;


import catalog.users.User;
import catalog.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {


    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    //, produces =  "text/html;charset=UTF-8"
    @GetMapping(value = "/load")
    @ResponseBody
    public List<User> loadAllUsers() {
        return userService.loadAll();
    }

    @PostMapping("/save")
    public void save(@RequestParam("name") String name, @RequestParam("password") String password) {
        userService.save(new User(name, password));
    }

    @GetMapping("/deleteAll")
    public void deleteAll() {
        userService.deleteAll();
    }

    @GetMapping("/delete")
    public void delete(@RequestParam("id") int id){
        userService.delete(id);
    }
}
