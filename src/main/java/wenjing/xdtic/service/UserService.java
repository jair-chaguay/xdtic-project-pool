package wenjing.xdtic.service;

import java.util.List;
import java.util.function.Supplier;
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
        syncDataForFront(user);

        return user;
    }

    public User getUser(String username, String password) {
        User user = userDao.getUser(username, password);
        syncDataForFront(user);

        return user;
    }

    public List<User> getUsers(String keyword, int pageNum, int pageSize) {
        
        int offset = pageNum * pageSize;
        List<User> users = userDao.getUsers(keyword, offset, pageSize);
        users.forEach(this::syncDataForFront);

        return users;
    }

    public PagingModel<User> getPagingUsers(String keyword, int pageNum, int pageSize) {

        Supplier<List<User>> users = () -> getUsers(keyword, pageNum, pageSize);
        Supplier<Long> totalNumberOfUsers = () -> countUsers(keyword);

        return PagingModel.of("users", users, totalNumberOfUsers, pageNum, pageSize);
    }

    public String getUsername(Integer id) {
        return userDao.getUsername(id);
    }

    public boolean containsUsername(String username) {
        return userDao.containsUsername(username);
    }

    public long countUsers(String keyword) {
        return userDao.countUsers(keyword);
    }

    public boolean updateUser(User user) {
        syncDataForBack(user);
        return userDao.updateUser(user);
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        return userDao.updatePassword(username, oldPassword, newPassword);
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

    /**
     * 为后端需求的字段同步 User
     *
     * @param user
     */
    public void syncDataForBack(User user) {
        if (user == null) {
            return;
        }
        user.setRealname(user.getName());
        user.setGender(user.getSex());
        user.setSpecialty(user.getProfe());
        user.setStuNum(user.getStunum());
        user.setSkill(user.getProfile());
        user.setExperience(user.getPexperice());
    }

    /**
     * 为前端需求的字段同步 User
     *
     * @param user
     */
    public void syncDataForFront(User user) {
        if (user == null) {
            return;
        }
        user.setName(user.getRealname());
        user.setSex(user.getGender());
        user.setProfe(user.getSpecialty());
        user.setStunum(user.getStuNum());
        user.setProfile(user.getSkill());
        user.setPexperice(user.getExperience());
    }

}
