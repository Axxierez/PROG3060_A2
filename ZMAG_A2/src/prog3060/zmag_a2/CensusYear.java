package prog3060.zmag_a2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CENSUSYEAR", schema="APP")
public class CensusYear {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="CENSUSYEARID", nullable = false)
    private int censusYearID;
    @Column(name="CENSUSYEAR", nullable = false)
    private int censusYear;
    
	public CensusYear() {}

	public int getCensusYearID() {
		return censusYearID;
	}

	public void setCensusYearID(int censusYearID) {
		this.censusYearID = censusYearID;
	}

	public int getCensusYear() {
		return censusYear;
	}

	public void setCensusYear(int censusYear) {
		this.censusYear = censusYear;
	}
}
