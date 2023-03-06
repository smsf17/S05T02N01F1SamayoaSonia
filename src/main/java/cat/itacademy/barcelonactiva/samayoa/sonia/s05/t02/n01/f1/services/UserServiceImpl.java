package cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.Game;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.User;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.UserDTO;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.repository.GameRepository;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.repository.UserRepository;


public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	GameRepository gameRepo;
	
	public UserServiceImpl(UserRepository userRepo, GameRepository gameRepo) {
		this.userRepo = userRepo;
		this.gameRepo = gameRepo;
	}

	@Override
	public User addUser(User userInput) {
		
		User user = new User();
		user.setUserName(userInput.getUserName());
		user.setRegDate(LocalDate.now());
		
		return userRepo.save(user);
	}

	@Override
	public String updateUser(User userUpdate) {
		
		 int num = (int) userUpdate.getId();
		
		if(userRepo.findById(num).isPresent()) {
			User us = new User();
			us.setId(num);
			us.setUserName(userUpdate.getUserName());
			us.setRegDate(LocalDate.now());
			userRepo.save(us);
			return "Jugador modificado!";
		}else { 
		return "Jugador con id " + num + " no encontrado!";
		}
	}

	@Override
	public Game addGameRoll(User user) {
		
		Game game = new Game();

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
		game.setUser(user);

		return gameRepo.save(game);
	}
	


	@Override
	public String deleteGame(User user) {
		
		int num = (int) user.getId();
		
		if(userRepo.findById(num).isPresent()) {
			gameRepo.deleteByUser(user);
			return "partidas de usuario con id: " + num + " eliminadas!";
			
		}
		
		return "Usuario " + num + " no encontrado";
	
	}

	@Override
	public List<UserDTO> getUsersAndSuccess() {
		
		List<User> users = userRepo.findAll();
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		
		users.forEach(x->{
			float average = calculateAverageWin(x);
			UserDTO userDTO = new UserDTO(x, average);
			usersDTO.add(userDTO);
		});
		
		return usersDTO;
	}

	@Override
	public List<Game> getGamesOfAUser(User user) {
		
		int num = (int) user.getId();
		
		if(userRepo.findById(num).isPresent()) {
		
		return gameRepo.findByUser(user);
		}
		ArrayList<Game> gameList = new ArrayList<>();
		return gameList;
	}

	@Override
	public List<UserDTO> getUsersByScoreOrder() {
				
		List<User> users = userRepo.findAll();
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		
		users.forEach(x->{
			float average = calculateAverageWin(x);
			UserDTO userDTO = new UserDTO(x, average);
			usersDTO.add(userDTO);
		});
		
		return usersDTO;
	}

	@Override
	public UserDTO getUserLowScore() {
		
		List<UserDTO> usersList = getUsersAndSuccess();
				
		return usersList.stream().min(Comparator.comparing(UserDTO::getAverageWin)).get();
	}

	@Override
	public UserDTO getUsersHighScore() {
		
		List<UserDTO> usersList = getUsersAndSuccess();
		
		return usersList.stream().max(Comparator.comparing(UserDTO::getAverageWin)).get();
	}
	
	@Override
	public boolean findByName(String userName) {
		return userRepo.existsByUserName(userName);
	}

	public float calculateAverageWin (User user) {

		List<Game> gameList = gameRepo.findByUser(user);
		
		
		int numRollWin = 0;
		int numRolls = gameList.size();

		for(Game g:gameList) {
			if (g.getWin() == true) {
				numRollWin++;				
			}
		}
		return numRollWin/numRolls*100;
		
	}



}
