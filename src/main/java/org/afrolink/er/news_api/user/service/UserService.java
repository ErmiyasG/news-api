package org.afrolink.er.news_api.user.service;


import java.util.UUID;

import org.afrolink.er.news_api.user.entity.User;

public interface UserService {

    User findById(UUID id);

    User findByEmail(String email);

    User getAuthor(UUID id);

    User getReader(UUID id);

    User validateAuthor(UUID id);
}
