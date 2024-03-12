package com.green.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.green.entity.Account;

@Repository
public interface AccountDAO extends JpaRepository<Account, String>{
	//lấy ra những account có 1 trong 2 vai trò 'DIRE','STAF'
	@Query("Select Distinct ar.account From Authority ar where ar.role.id IN ('DIRE','STAF')")
	List<Account> getAdministrators();

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//thi
	@Query("SELECT o FROM Account o WHERE email=:mail")
	Account GetByMail(@Param("mail") String mail);
	
	//sơn
	@Query(value="Select a.Username, a.Fullname, a.email, a.photo, "
			+ "sum(odt.price * odt.quantity) as totalPayment "
			+ "From Accounts a inner join orders o on a.Username = o.Username "
			+ "inner join OrderDetails odt on o.Id = odt.OrderId "
			+ "Group by a.Username, a.Fullname, a.email, a.photo "
			+ "order by totalPayment desc",nativeQuery = true)
	List<Object[]> top10Customer();
	
	@Query(value="delete from Accounts where username = :username ",nativeQuery = true)	
	void deleteAccount(@Param("username") String username);
}
