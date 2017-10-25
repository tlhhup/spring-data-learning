package com.woniuxy.springdata.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.woniuxy.springdata.entity.User;

public class UserSpecification {

	//用于构建where子句
	public static Specification<User> likeUserName(String userName){
		return new Specification<User>() {
			
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("userName"), userName+"%");
			}
		};
	}
	
}
