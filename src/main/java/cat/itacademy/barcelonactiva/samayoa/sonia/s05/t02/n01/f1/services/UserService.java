package cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.Game;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.User;
import cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain.UserDTO;

@Service
public interface UserService {
	
	// POST: /players: crea un jugador/a.
	User addUser(User user);

	// PUT /players: modifica el nom del jugador/a.
	String updateUser(User userUpdate);

	// POST /players/{id}/games/ : un jugador/a específic realitza una tirada dels
	// daus.
	Game addGameRoll(User user);

	// DELETE /players/{id}/games: elimina les tirades del jugador/a.
	String deleteGame(User user);

	// GET /players/: retorna el llistat de tots els jugadors/es del sistema amb el
	// seu percentatge mitjà d’èxits.
	List<UserDTO> getUsersAndSuccess();

	// GET /players/{id}/games: retorna el llistat de jugades per un jugador/a.
	List<Game> getGamesOfAUser(User user);

	// GET /players/ranking: retorna el ranking mig de tots els jugadors/es del
	// sistema. És a dir, el percentatge mitjà d’èxits.
	List<UserDTO> getUsersByScoreOrder();

	// GET /players/ranking/loser: retorna el jugador/a amb pitjor percentatge
	// d’èxit.
	UserDTO getUserLowScore();

	// GET /players/ranking/winner: retorna el jugador amb pitjor percentatge
	// d’èxit.
	UserDTO getUsersHighScore();
	
	public boolean findByName(String userName);

}
