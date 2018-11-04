package catalog.webfacade;


import catalog.users.User;
import catalog.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ServiceResult loadAllUsers() {
        return ServiceResult.guardedInvoke(() -> userService.loadAll());
    }

    @PostMapping("/save")
    public ServiceResult save(@RequestParam("name") String name, @RequestParam("password") String password) {
        return ServiceResult.guardedInvoke(()->userService.save(new User(name, password)));
    }

    @GetMapping("/deleteAll")
    public ServiceResult deleteAll() {
        return ServiceResult.guardedInvoke(()->userService.deleteAll());
    }

    @GetMapping("/delete")
    public ServiceResult delete(@RequestParam("id") int id){
        return ServiceResult.guardedInvoke(()->userService.delete(id));
    }
}
