package wenjing.xdtic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.PagingModel;
import wenjing.xdtic.model.User;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean addUser(String username, String password) {
        return userDao.addUser(username, password);
    }

    public User getUser(Integer id) {
        User user = userDao.getUser(id);
        User.syncDataForFront(user);

        return user;
    }

    public User getUser(String username, String password) {
        User user = userDao.getUser(username, password);
        User.syncDataForFront(user);

        return user;
    }

    public List<User> getUsers(String keyword, int pageNum, int size) {
        int offset = pageNum * size;
        List<User> users = userDao.getUsers(keyword, offset, size);
        users.forEach(User::syncDataForFront);

        return users;
    }

    public PagingModel<User> getPagingUsers(String keyword, int pageNum, int size) {
        List<User> users = getUsers(keyword, pageNum, size);
        PagingModel<User> pagingUsers = new PagingModel<>(users, pageNum, users.size(), "users");
        long count = getUsersCount(keyword);
        pagingUsers.setHasMore((pageNum + 1) * size < count);

        return pagingUsers;
    }

    public String getUsername(Integer id) {
        return userDao.getUsername(id);
    }

    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        return userDao.updatePassword(username, oldPassword, newPassword);
    }

    public long getUsersCount(String keyword) {
        return userDao.getUsersCount(keyword);
    }

    public boolean containsUsername(String username) {
        return userDao.containsUsername(username);
    }

    public boolean deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }

    public boolean deleteUsers(List<Integer> ids) {
        if (ids.isEmpty()) {
            return true;
        }

        return userDao.deleteUsers(ids);
    }

}
