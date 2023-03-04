package com.green.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.green.entity.Account;
import com.green.entity.Authority;

@Repository
public interface AuthorityDAO extends JpaRepository<Authority, Integer>{
	//lấy ra các quyền được cấp mà những account nằm trong nhóm tài khoản admin
	@Query("Select Distinct a From Authority a where a.account IN ?1")
	List<Authority> authoritiesOf(List<Account> accounts);

//	@Query("Select a From Authority a where a.account.username like ?1")
//	List<Authority> getOneByRole(String username);
//
//	@Transactional
//	@Modifying
//	@Query("Delete from Authority where Username = ?1")
//	void deleteByUserName(String username);

}
