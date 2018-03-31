package prog3060.zmag_a2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GEOGRAPHICAREA", schema="APP")
public class GeographicArea {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="GEOGRAPHICAREAID", nullable = false)
    private int geographicAreaID;
    
    @Column(name="CODE", nullable = false)
    private int code;
    @Column(name="LEVEL", nullable = false)
    private int level;
    @Column(name="ALTERNATIVECODE", nullable = false)
    private int alternativeCode;
    @Column(name="NAME", nullable = false)
    private String name;
    
	public GeographicArea() {}
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
