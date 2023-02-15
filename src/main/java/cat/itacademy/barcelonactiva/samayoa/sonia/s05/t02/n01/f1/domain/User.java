package cat.itacademy.barcelonactiva.samayoa.sonia.s05.t02.n01.f1.domain;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "UserName")
	private String userName;

	@Column(name = "Date")
	private LocalDate regDate;

	@Column(name = "AverageWin")
	private float averageWin;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Game> gameList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDate getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDate regDate) {
		this.regDate = regDate;
	}

	@PrePersist
	public void defineDate() {
		regDate = LocalDate.now();
	}

	public float getAverageWin() {
		
		List<Game> gameList = this.getGameList();
		float averageWin = 0;
		int aux=0;
		
		for(Game g:gameList) {
			if (g.getWin() == true) {
				aux = aux + 1;				
			}
			averageWin = aux/gameList.size();
		}
		return averageWin;
	}

	public void setAverageWin(float averageWin) {
		this.averageWin = averageWin;
	}

	public List<Game> getGameList() {
		return gameList;
	}

	public void setGameList(List<Game> gameList) {
		this.gameList = gameList;
	}

	public User(String userName) {
		this.userName = userName;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}


}



