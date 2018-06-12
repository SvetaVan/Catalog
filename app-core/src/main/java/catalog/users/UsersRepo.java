package catalog.users;

import java.util.List;

public interface UsersRepo {

    List<User> loadAll();

    //save user and return  user with id
    User save(User user);

    void delete(int id);

    void deleteAll();

    User findByName(String name);


}
