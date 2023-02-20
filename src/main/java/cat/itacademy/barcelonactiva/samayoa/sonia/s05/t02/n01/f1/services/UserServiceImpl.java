package cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.Game;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.User;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.repository.GameRepository;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.repository.UserRepository;


public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	GameRepository gameRepo;

	@Override
	public User addUser(User user) {
		
		return userRepo.save(user);
	}

	@Override
	public String updateUser(User userUpdate) {
		
		 int num = (int) userUpdate.getId();
		
		if(userRepo.findById(num).isPresent()) {
			User us = new User();
			us=userRepo.findById(num).get();
			us.setUserName(userUpdate.getUserName());
			userRepo.save(us);
			return "Jugador modificado!";
		}else { 
		return "Jugador con Id:  no encontrado!";
		}
	}

	@Override
	public Game addGame(Game game) {

		int d1 = (int) (Math.floor(Math.random() * 6) + 1);
		int d2 = (int) (Math.floor(Math.random() * 6) + 1);
		int total = d1 + d2;
		game.setDie1(d1);
		game.setDie2(d2);
		game.setTotalTurn(total);
		if (total == 7) {
			game.setWin(true);
		} else {
			game.setWin(false);
		}
		Optional<User> userOptional = userRepo.findById(1);
		if(userOptional.isPresent()) {
		game.setUser(userOptional.get());
		}

		return gameRepo.save(game);
	}
	


	@Override
	public String deleteGame(int idUser) {
		
		List<Game> gameListUser = gameRepo.findAllByIdUser(idUser);
		
		if(gameListUser.isEmpty()) {
			return "No existen partidas del usuario: " + idUser;
		}else {
			
			for (int i = 1; i <= gameListUser.size(); i++) {
				gameListUser.remove(i);				
			}
			return "partidas de usuario con id: " + idUser + " eliminadas!";
		}
	}

	@Override
	public List<User> getUsersAndSuccess() {
		
		return userRepo.findAll();
	}

	@Override
	public List<Game> getGamesOfAUser(int id) {
		
		return gameRepo.findAllByIdUser(id);
	}

	@Override
	public List<User> getUsersByScoreOrder() {
		
		return userRepo.findAll(Sort.by("averageWin"));
	}

	@Override
	public List<User> getUserLowScore() {
		
		return userRepo.findFirst1ByOrderByAverageWinAsc();
	}

	@Override
	public List<User> getUsersHighScore() {
		// TODO Auto-generated method stub
		return userRepo.findFirst1ByOrderByAverageWinDesc();
	}
	
	@Override
	public boolean findByName(String userName) {
		return userRepo.existsByUserName(userName);
	}

	


}
