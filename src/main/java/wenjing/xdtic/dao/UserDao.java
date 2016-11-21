package wenjing.xdtic.dao;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wenjing.xdtic.model.User;

/**
 *
 * @author admin
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUser(Integer id) {
        String SQL = "SELECT * FROM user WHERE id = " + id;

        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(SQL);
            //Map数据集返回对象名为string类型的值
            User user = new User();
            user.setId((Integer) map.get("id"));//将得到的数据赋值，并返回
            user.setUsername((String) map.get("username"));
            user.setPassword((String) map.get("password"));
            user.setEmail((String) map.get("email"));
            user.setNickname((String) map.get("nickname"));
            user.setProfe((String) map.get("profe"));
            user.setSex((String) map.get("sex"));
            user.setPhone((String) map.get("phone"));
            user.setStunum((String) map.get("stunum"));
            user.setPexperice((String) map.get("pexperice"));
            user.setProfile((String) map.get("profile"));

            return user;

        } catch (EmptyResultDataAccessException ex) {
            return null;// 捕获异常      spring查询不到输入数据时返回null
        }
    }

    public User selectuser(String username, String password) {
        String SQL = "SELECT * from  user where username='" + username + "'AND password='" + password + "'";

        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(SQL);
            //Map数据集返回对象名为string类型的值
            User user = new User();
            user.setId((Integer) map.get("id"));//将得到的数据赋值，并返回
            user.setUsername((String) map.get("username"));
            user.setPassword((String) map.get("password"));
            user.setEmail((String) map.get("email"));
            user.setNickname((String) map.get("nickname"));
            user.setProfe((String) map.get("profe"));
            user.setSex((String) map.get("sex"));
            user.setPhone((String) map.get("phone"));
            user.setStunum((String) map.get("stunum"));
            user.setPexperice((String) map.get("pexperice"));
            user.setProfile((String) map.get("profile"));
            return user;
        } catch (EmptyResultDataAccessException ex) {
            return null;// 捕获异常      spring查询不到输入数据时返回null
        }

        //    User resultUser = jdbcTemplate.queryForObject(SQL, User.class);
        //    return resultUser;
    }

    //判断 username 是否已经存在
    public boolean containsUser(String username) {
        String SQL = "SELECT id FROM user where username='" + username + "'";
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(SQL);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean addUser(String username, String password, String email) {
        String SQL = "INSERT INTO user SET username = ?, password = ?, email = ?";
        int result = jdbcTemplate.update(SQL, username, password, email);

        return result == 1;
    }

    public  boolean addUser(String username,String password) {
        String SQL = "INSERT INTO user SET username = ?, password = ?";
        int result = jdbcTemplate.update(SQL, username, password);

        return result == 1;
    }
            
    public User updateUser(User user) {
        String SQL = "UPDATE user "
                + "set nickname=?, name=?, sex=?, phone=?, profe=?, stunum=?,"
                + "profile=?,pexperice=?,email=? "
                + "where id=" + user.getId();
        try {
            int result = jdbcTemplate.update(SQL,
                    user.getNickname(),
                    user.getName(),
                    user.getSex(),
                    user.getPhone(),
                    user.getProfe(),
                    user.getStunum(),
                    user.getProfile(),
                    user.getPexperice(),
                    user.getEmail());

            if (result == 1) {
                return user;
            }
        } catch (EmptyResultDataAccessException ex) {
            // 捕获异常      spring查询不到输入数据时返回null
        }
        return null;
    }

    public User updatepassword(User user) {
        String SQL = "UPDATE user SET password = ? WHERE id = " + user.getId();

        int result = jdbcTemplate.update(SQL, user.getPassword());
        if (result == 1) {
            getUser(user.getId());
        }

        return null;
    }

}
