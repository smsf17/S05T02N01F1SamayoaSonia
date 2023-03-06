package cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.Game;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.User;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

	List<Game> findAllByIdGame(int idGame);
	List<Game> findByUser(User user);
	void deleteByUser(User user);
}
