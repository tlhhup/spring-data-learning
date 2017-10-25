package com.woniuxy.springdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.woniuxy.springdata.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User>{

	List<User> findUserByUserName(String username);
	
	//@Query("from User where userName like %?1")//这种写法最后转换成jpql的时候会把%自动的去掉
	List<User> findByUserNameStartingWith(String userName);
	
	@Transactional
	@Modifying
	@Query("update User set userName=?1 where id=?2")
	void updateUser(String userName,int id);
	
	@Transactional
	@Modifying
	@Query("update User set userName=:#{#user.userName} where id=:#{#user.id}")
	void update(@Param("user")User user);
	
}
