package cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	List<User> findByUserName(String userName);
	boolean findUserName( String userName);
	List<User> findByUserNameOrderByAverageWin(float averageWin);
	User findFirstByOrderByAverageWinDesc();
	User findFirstByOrderByAverageWinAsc();
	List<User> findFirst1ByOrderByAverageWinDesc();
	List<User> findFirst1ByOrderByAverageWinAsc();

}
