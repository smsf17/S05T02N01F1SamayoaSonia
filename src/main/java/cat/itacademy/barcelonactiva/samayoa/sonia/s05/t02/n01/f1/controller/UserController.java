package cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.Game;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.User;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.services.UserService;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User user) {

		if (!userService.findByName(user.getUserName()))
			return new ResponseEntity(new Message("Ya existe usuario con ese nombre"), HttpStatus.BAD_REQUEST);

		User userData = userService.addUser(new User(user.getUserName()));
		return new ResponseEntity(new Message("Usuario creada"), HttpStatus.OK);

	}

	@PutMapping("/update")
	public String updateUser(@RequestBody User userUpdate) {

		return userService.updateUser(userUpdate);

	}

	@PostMapping("/addgame/{idUser}")
	public ResponseEntity<Game> addGame(@PathVariable("idUser") Integer idUser, @RequestBody Game game) {
		userService.addGame(game);
		return ResponseEntity(new Message("Partida creada correctamente"), HttpStatus.OK);

	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> userList = userService.getUsersAndSuccess();

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
			List<Game> gameList = userService.getGamesOfAUser(id);

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
			List<User> userList = userService.getUsersByScoreOrder();

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
			List<User> findFirstByOrderByAverageWinAsc = userService.getUserLowScore();
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
			List<User> findFirstByOrderByAverageWinDesc = userService.getUsersHighScore();
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
