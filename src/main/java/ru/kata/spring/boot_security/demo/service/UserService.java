package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User findByEmail(String name);
    void addUser(User user, List<Long> roleIds); // Измененная сигнатура
    void updateUser(User user, String newPassword, List<Long> roleIds); // Измененная сигнатура
    void deleteUser(Long id);
    List<Role> getAllRoles(); // Новый метод
}
