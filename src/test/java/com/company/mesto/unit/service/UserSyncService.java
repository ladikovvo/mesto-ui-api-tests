package com.company.mesto.unit.service;


import com.company.mesto.api.models.UserMe;
import com.company.mesto.unit.api.UsersApi;
import com.company.mesto.unit.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserSyncService {
    private static final Logger log = LoggerFactory.getLogger(UserSyncService.class);
    private final UsersApi usersApi;
    private final UserRepository userRepository;

    public UserSyncService(UsersApi usersApi, UserRepository userRepository) {
        this.usersApi = usersApi;
        this.userRepository = userRepository;
    }


    public void syncMeToDb(){
        try {
            UserMe me = usersApi.getMe();
            if (me != null && me.getName() != null && !me.getName().isBlank()){
                userRepository.save(me);
            }
        } catch (RuntimeException exc) {
            log.error("Failed to sync user", exc);
            throw  exc;
        }
    }
}
