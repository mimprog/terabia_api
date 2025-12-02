package com.terabia.terabia.services;

import com.terabia.terabia.dto.UpdateUserDto;
import com.terabia.terabia.models.User;
import com.terabia.terabia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstname"));
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User getUserByIdOrThrow(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }


    public UpdateUserDto updateUser (Integer id, UpdateUserDto updateUserDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            throw new RuntimeException("User not found with id: " + id);
        }

        User user = optionalUser.get();

        user.setFirstname(updateUserDto.getFirstname());
        user.setLastname(updateUserDto.getLastname());
        user.setPhone(updateUserDto.getPhone());

        userRepository.save(user);

        UpdateUserDto updateUserDto1 = new UpdateUserDto();
        updateUserDto1.setFirstname(updateUserDto.getFirstname());
        updateUserDto1.setLastname(updateUserDto.getLastname());
        updateUserDto1.setPhone(updateUserDto.getPhone());

        return updateUserDto1;

    }

}
