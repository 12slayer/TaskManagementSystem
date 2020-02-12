package com.nepdroid.taskmanagement.login.repo;

import com.nepdroid.taskmanagement.login.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Apprepositery extends JpaRepository <AppUser,Integer> {
    AppUser findByEmail(String Email);

    //@Query(value="SELECT  a FROM AppUser WHERE a.role='USER'",nativeQuery = true);//mysql
    @Query(value="SELECT appuser FROM AppUser appuser WHERE appuser.role='USER'")
    List<AppUser> fetchAllUser();

}
