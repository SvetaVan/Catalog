package catalog.users;

import catalog.CatalogRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepo userRepo;

    @Override
    public List<User> loadAll() {
        return userRepo.loadAll();
    }

    @Override
    public User save(User user) {
        if(userRepo.loadAll().contains(user)){
            throw new CatalogRuntimeException(String.format("User with name %s is already exists.", user.getName()));
        }
        return userRepo.save(user);
    }

    @Override
    public void delete(int id) {
        userRepo.delete(id);
    }

    @Override
    public void deleteAll() {
        userRepo.deleteAll();
    }

    @Override
    public User findByName(String name) {
        return userRepo.findByName(name);
    }
}
