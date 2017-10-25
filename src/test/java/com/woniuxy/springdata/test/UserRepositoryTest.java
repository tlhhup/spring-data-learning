package com.woniuxy.springdata.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.woniuxy.springdata.App;
import com.woniuxy.springdata.entity.User;
import com.woniuxy.springdata.repository.UserRepository;
import com.woniuxy.springdata.repository.specification.UserSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
public class UserRepositoryTest {

	@Resource
	private UserRepository userRepository;

	@Test
	public void save() {
		User user = new User();
		user.setUserName("amdin");
		user.setAddress("成都");
		user.setPassword("admin");
		userRepository.save(user);
	}

	@Test
	public void saveBatch() {
		User user = null;
		List<User> users = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			user = new User();
			user.setUserName("amdin" + i);
			user.setAddress("成都");
			user.setPassword("admin");
			users.add(user);
			user = null;
		}
		this.userRepository.save(users);
	}

	@Test
	public void update() {
		User user = new User();
		user.setAddress("北京");
		user.setId(2);
		this.userRepository.save(user);
	}

	@Test
	public void delete() {
		this.userRepository.delete(2);
	}

	@Test
	public void page() {
		Page<User> page = this.userRepository.findAll(new PageRequest(1, 5));

		System.out.println(page.getTotalPages());
	}

	@Test
	public void query() {
		List<User> users = this.userRepository.findByUserNameStartingWith("amdin");
		System.out.println(users.size());
	}

	@Test
	public void update1() {
		User user=new User();
		user.setId(3);
		user.setUserName("lis");
		this.userRepository.update(user);
	}
	
	@Test
	public void findSpecification(){
		List<User> users = this.userRepository.findAll(UserSpecification.likeUserName("am"));
		System.out.println(users.size());
	}
	
	@Test
	public void findByExample(){
		User user=new User();
		user.setUserName("am");
//		Example<User> example=Example.of(user);//该生成是等值查询，同时也存在基本数据类型的问题
		ExampleMatcher matcher=ExampleMatcher.matching().withMatcher("userName", match->match.startsWith());
		
		Example<User> example=Example.of(user,matcher);
		
		List<User> users = this.userRepository.findAll(example);
		
		System.out.println(users.size());
	}
}
