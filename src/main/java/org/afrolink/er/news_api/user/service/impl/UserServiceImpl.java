package org.afrolink.er.news_api.user.service.impl;

import org.afrolink.er.news_api.user.entity.Role;
import org.afrolink.er.news_api.user.entity.User;
import org.afrolink.er.news_api.user.repository.UserRepository;
import org.afrolink.er.news_api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(UUID id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "User not found"));
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "User not found"));
    }

    @Override
    public User getAuthor(UUID id) {

        User user = findById(id);

        if (user.getRole() != Role.AUTHOR) {
            throw new RuntimeException(
                    "User is not an author");
        }

        return user;
    }

    @Override
    public User getReader(UUID id) {

        User user = findById(id);

        if (user.getRole() != Role.READER) {
            throw new RuntimeException(
                    "User is not a reader");
        }

        return user;
    }
}
