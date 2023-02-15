package cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.Game;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.User;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.services.UserServiceImpl;

@RestController
@RequestMapping("players")
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;

	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User user) {

		if (!userServiceImpl.findName(user.getUserName()))
			return new ResponseEntity(new Message("Ya existe usuario con ese nombre"), HttpStatus.BAD_REQUEST);

		User userData = userServiceImpl.addUser(new User(user.getUserName()));
		return new ResponseEntity(new Message("Usuario creada"), HttpStatus.OK);

	}

	@PutMapping("/update")
	public String updateUser(@RequestBody User userUpdate) {

		return userServiceImpl.updateUser(userUpdate);

	}

	@PostMapping("/addgame/{idUser}")
	public ResponseEntity<Game> addGame(@PathVariable("idUser") Integer idUser, @RequestBody Game game) {
		userServiceImpl.addGame(idUser, game);
		return ResponseEntity(new Message("Partida creada correctamente"), HttpStatus.OK);

	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> userList = userServiceImpl.getUsersAndSuccess();

			if (userList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(userList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/{id}/games")
	public ResponseEntity<List<Game>> getGamesOfAUser(int id) {
		try {
			List<Game> gameList = userServiceImpl.getGamesOfAUser(id);

			if (gameList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(gameList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/ranking")
	public ResponseEntity<List<User>> getUsersByScoreOrder() {
		try {
			List<User> userList = userServiceImpl.getUsersByScoreOrder();

			if (userList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(userList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/ranking/loser")
	public ResponseEntity<List<User>> getUserLowScore() {
		try {
			List<User> findFirstByOrderByAverageWinAsc = userServiceImpl.getUserLowScore();
			if (findFirstByOrderByAverageWinAsc.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			System.out.println("Usuario con menos porcetaje de acierto: " + findFirstByOrderByAverageWinAsc);
			return new ResponseEntity<>(findFirstByOrderByAverageWinAsc, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/ranking/winner")
	public ResponseEntity<List<User>> getUsersHighScore() {
		try {
			List<User> findFirstByOrderByAverageWinDesc = userServiceImpl.getUsersHighScore();
			if (findFirstByOrderByAverageWinDesc.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			System.out.println("El ganador es: " + findFirstByOrderByAverageWinDesc);
			return new ResponseEntity<>(findFirstByOrderByAverageWinDesc, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ResponseEntity<Game> ResponseEntity(Message message, HttpStatus ok) {
		// TODO Auto-generated method stub
		return null;
	}


}
