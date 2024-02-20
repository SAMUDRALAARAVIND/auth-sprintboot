package com.auth.authinsta.User;

import com.auth.authinsta.User.entity.UserEntity;
import com.auth.authinsta.User.model.AuthorizedUserInfoProjection;
import com.auth.authinsta.User.model.IamResponseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query(value = "SELECT U.id as id FROM UserEntity U WHERE U.email = :email")
    List<Integer> findByEmail(@Param("email") String email);

    @Query(value = "SELECT U.id as id, U.role as role FROM UserEntity U WHERE U.email = :email AND U.password = :password")
    List<AuthorizedUserInfoProjection> findUserProperties(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT U.firstName as firstName, U.lastName as lastName, U.email as email,U.password as password FROM UserEntity U WHERE U.id = :userId")
    List<IamResponseProjection> findIamUserInfo(@Param("userId") Integer userId);
}
