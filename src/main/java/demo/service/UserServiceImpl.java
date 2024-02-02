package demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import demo.model.Role;
import demo.model.User;
import demo.repositore.RoleRepository;
import demo.repositore.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

private final UserRepository userRepository;

private final RoleRepository roleRepository;


public UserServiceImpl( UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
}


@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional user = userRepository.findByUsername(username);
    if (user.isEmpty()) {
        throw new UsernameNotFoundException("User not found");
    }
    return (User) user.get();
}

public User findUserById(Long userId) {
    Optional<User> userFromDb = userRepository.findById(userId);
    return userFromDb.orElse(new User());
}

public List<User> allUsers() {
    return userRepository.findAll();
}

@Transactional
public boolean saveUser(User user) {
    Optional userFromDb = userRepository.findByUsername(user.getUsername());
    if (userFromDb.isPresent()) {
        return false;
    }
    user.addRoles(roleRepository.getById(2L));
    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    userRepository.save(user);
    return true;
}

@Transactional
public boolean deleteUser(Long userId) {
    if (userRepository.findById(userId).isPresent()) {
        userRepository.deleteById(userId);
        return true;
    }
    return false;
}

@Transactional
public void updateUser(User userinfo, long id) {
    User old = userRepository.getById(id);
    old.setUsername(userinfo.getUsername());
    old.setPassword(new BCryptPasswordEncoder().encode(userinfo.getPassword()));
    old.setRoles(userinfo.getRoles());
    userRepository.save(old);
}

public List<Role> listRole() {
    return new ArrayList<>(roleRepository.findAll());
}


}

