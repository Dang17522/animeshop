package com.anime.jpa.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.User;
import com.anime.jpa.entity.UserReport;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	List<User> findByIsActive(Boolean isActive);

	Page<User> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE User u SET u.isActive = ?1 WHERE u.id = ?2")
	void deleteLogical(Boolean isActive, Long id);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE users SET email =?1,fullname=?2, photo = ?3, phone = ?4 WHERE username= ?5", nativeQuery = true)

	void create(String email, String fullname, String photo, String phone, String username);

	@Query("select u from User u where u.username = ?1")
	User findByUsername(String username);

	@Query(value = "select u from User u where u.email = ?1")
	User findByEmail(String email);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE User u SET u.password = ?1 WHERE u.id = ?2")
	void updatePassword(String password, Long id);

	@Query("FROM User u WHERE u.username=?1 OR email=?1")
	User findByUsernameOrEmail(String username);

	@Query("select new UserReport(p.user.username,p.user.fullname,p.user.email,count(p.user.username)) from Order p Group By p.user.username,p.user.fullname,p.user.email  Order By count(p.user.username) DESC")
	public List<UserReport> load();

	@Query(value = "SELECT u FROM User u JOIN UserRole ur ON u.id = ur.user.id JOIN Role r ON ur.role.id = r.id "
			+ "WHERE u.isActive = ?1 AND r.name in ('CUSTOMER')")
	Page<User> listUserCustomer(Boolean isActive, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.isActive = ?1 AND CONCAT(u.username, u.fullname) LIKE %?2%")
	Page<User> findByKeyword(Boolean isActive, String keyword, Pageable pageable);
	
	@Query("FROM User u where u.email = ?1")
	public User checkEmail(String email);
}
