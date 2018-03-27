package prog3060.zmag_a2;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GEOGRAPHICAREA", schema = "APP")
public class GeographicArea {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="geographicAreaID")
	private int geographicAreaID;
	
	public int getGeographicAreaID() {
		return geographicAreaID;
	}

	public void setGeographicAreaID(int geographicAreaID) {
		this.geographicAreaID = geographicAreaID;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}




	public int getAlternativeCode() {
		return alternativeCode;
	}

	public void setAlternativeCode(int alternativeCode) {
		this.alternativeCode = alternativeCode;
	}




	@Column(name="code")
	private int code;
	
	@Column(name="level")
	private int level;
	
	@Column(name="name")
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="alternativeCode")
	private int alternativeCode;
	
	
	@OneToMany(mappedBy="geographicArea")
	private Set<Household> household = new HashSet<Household>();

	public Set<Household> getHousehold() {
		return household;
	}

	public void setHousehold(Set<Household> household) {
		this.household = household;
	}

	public int getTotalPopulation() {
		return totalPopulation;
	}

	public void setTotalPopulation(int totalPopulation) {
		this.totalPopulation = totalPopulation;
	}

	public int getMalePopulation() {
		return malePopulation;
	}

	public void setMalePopulation(int malePopulation) {
		this.malePopulation = malePopulation;
	}




	public int getFemalePopulation() {
		return femalePopulation;
	}

	public void setFemalePopulation(int femalePopulation) {
		this.femalePopulation = femalePopulation;
	}




	private int totalPopulation;
	private int malePopulation;

	private int femalePopulation;



	
}
