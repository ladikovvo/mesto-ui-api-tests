package com.company.mesto.unit.repository;

import com.company.mesto.api.models.UserMe;

public interface UserRepository {
    void save(UserMe user);
}
