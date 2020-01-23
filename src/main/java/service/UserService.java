package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {
    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    /* хранилище данных */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());


    public List<User> getAllUsers() {
        return new ArrayList<>(dataBase.values());
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {
        if (isExistsThisUser(user)) {
            return false;
        } else {
            user.setId(maxId.getAndIncrement());
            dataBase.put(user.getId(), user);
            return true;
        }
    }

    public void deleteAllUser() {
        dataBase.clear();
    }

    public boolean isExistsThisUser(User user) {
        return findUserByEmail(user.getEmail()) != null;
    }

    public List<User> getAllAuth() {
        return new ArrayList<>(authMap.values());
    }

    public boolean authUser(User user) {
        if (!isExistsThisUser(user)) {
            return false;
        }
        User foundUser = findUserByEmail(user.getEmail());

        if (!user.getPassword().equals(foundUser.getPassword())) {
            return false;
        }
        authMap.put(foundUser.getId(), foundUser);
        return true;
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return authMap.containsKey(id);
    }

    public User findUserByEmail(String email) {
        return getAllUsers().stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }
}
