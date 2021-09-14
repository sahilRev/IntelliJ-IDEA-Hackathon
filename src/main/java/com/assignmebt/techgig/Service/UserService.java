package com.assignmebt.techgig.Service;

import com.assignmebt.techgig.Entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
     List<User> getAllUsers();
     User getUserById(Long id);
     void addUser(User user);
     void deleteUser(Long id);
     Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
