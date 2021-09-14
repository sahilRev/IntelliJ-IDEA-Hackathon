package com.assignmebt.techgig.Service;


import com.assignmebt.techgig.Entity.User;
import com.assignmebt.techgig.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    // Display all users
    @Override
    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id){
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()){
            return new User(user.get().getId(), user.get().getFirstName()
            ,user.get().getLastName(), user.get().getPhone());
        }
        else throw new RuntimeException("User not found for id :: " + id);
    }


    //Add User to Database
    @Override
    public void addUser(User user){
        userRepository.save(user);
    }


    //Delete User by Id
    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);

    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return userRepository.findAll(pageable);
    }

}//end class

