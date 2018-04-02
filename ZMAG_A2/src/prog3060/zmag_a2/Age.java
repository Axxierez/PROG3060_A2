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
@Table(name="AGE", schema="APP")
public class Age {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="AGEID", nullable = false)
    private int ageID;
    
    @ManyToOne
	@JoinColumn(name="AGEGROUP")
    private AgeGroup ageGroup;
    @ManyToOne
	@JoinColumn(name="CENSUSYEAR")
    private CensusYear censusYear;
    @ManyToOne
	@JoinColumn(name="GEOGRAPHICAREA")
    private GeographicArea geographicArea;

    @Column(name="COMBINED", nullable = false)
    private int combined;
    @Column(name="MALE", nullable = false)
    private int male;
    @Column(name="FEMALE", nullable = false)
    private int female;
    
	public Age() {}

	public int getAgeID() {
		return ageID;
	}

	public AgeGroup getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(AgeGroup ageGroup) {
		this.ageGroup = ageGroup;
	}

	public CensusYear getCensusYear() {
		return censusYear;
	}

	public void setCensusYear(CensusYear censusYear) {
		this.censusYear = censusYear;
	}

	public GeographicArea getGeographicArea() {
		return geographicArea;
	}

	public void setGeographicArea(GeographicArea geographicArea) {
		this.geographicArea = geographicArea;
	}

	public int getCombined() {
		return combined;
	}

	public void setCombined(int combined) {
		this.combined = combined;
	}

	public int getMale() {
		return male;
	}

	public void setMale(int male) {
		this.male = male;
	}

	public int getFemale() {
		return female;
	}

	public void setFemale(int female) {
		this.female = female;
	}

	public void setAgeID(int ageID) {
		this.ageID = ageID;
	}

	
}
