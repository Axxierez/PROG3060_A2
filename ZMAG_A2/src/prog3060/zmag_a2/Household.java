package prog3060.zmag_a2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "HOUSEHOLD", schema = "APP")
public class Household {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
		
	@ManyToOne
	@JoinColumn(name="geographicArea", nullable=false)
	private GeographicArea geographicArea;
	
	@ManyToOne
	@JoinColumn(name="censusYear", nullable=false)
	private CensusYear censusYear;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GeographicArea getGeographicArea() {
		return geographicArea;
	}

	public void setGeographicArea(GeographicArea geographicArea) {
		this.geographicArea = geographicArea;
	}

	public CensusYear getCensusYear() {
		return censusYear;
	}

	public void setCensusYear(CensusYear censusYear) {
		this.censusYear = censusYear;
	}

	public HouseholdType getHouseholdType() {
		return householdType;
	}

	public void setHouseholdType(HouseholdType householdType) {
		this.householdType = householdType;
	}

	public HouseholdSize getHouseholdSize() {
		return householdSize;
	}

	public void setHouseholdSize(HouseholdSize householdSize) {
		this.householdSize = householdSize;
	}

	public HouseholdsByAgeRange getHouseholdsByAgeRange() {
		return householdsByAgeRange;
	}

	public void setHouseholdsByAgeRange(HouseholdsByAgeRange householdsByAgeRange) {
		this.householdsByAgeRange = householdsByAgeRange;
	}

	public HouseholdEarners getHouseholdEarners() {
		return householdEarners;
	}

	public void setHouseholdEarners(HouseholdEarners householdEarners) {
		this.householdEarners = householdEarners;
	}

	public TotalIncome getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(TotalIncome totalIncome) {
		this.totalIncome = totalIncome;
	}

	public int getNumberReported() {
		return numberReported;
	}

	public void setNumberReported(int numberReported) {
		this.numberReported = numberReported;
	}

	@ManyToOne
	@JoinColumn(name="householdType" , nullable= false)
	private HouseholdType householdType;
	
	@ManyToOne
	@JoinColumn(name= "householdSize",nullable = false)
	private HouseholdSize householdSize;
	
	@ManyToOne
	@JoinColumn(name="householdsByAgeRange" , nullable = false)
	private HouseholdsByAgeRange householdsByAgeRange;
	
	@ManyToOne
	@JoinColumn(name="householdEarners",nullable=false)
	private HouseholdEarners householdEarners;
	
	@ManyToOne
	@JoinColumn(name="totalIncome",nullable=false)
	private TotalIncome totalIncome;
	
	@Column(name="numberReported")
	private int numberReported;
}
