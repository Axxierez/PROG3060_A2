// ----------------------------------------------------
// Zack Meadows & Alex Galka
// PROG3060 Assignment 2
// 2018-03-27
// ----------------------------------------------------

package prog3060.zmag_a2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import prog3060.zmag_a2.CensusYear;
import prog3060.zmag_a2.GeographicArea;
import prog3060.zmag_a2.HouseholdEarners;
import prog3060.zmag_a2.HouseholdSize;
import prog3060.zmag_a2.HouseholdType;
import prog3060.zmag_a2.HouseholdsByAgeRange;
import prog3060.zmag_a2.TotalIncome;
import prog3060.zmag_a2.Household;

public class ConnectionBean {
	static final int EXIT_FAILURE_COMMAND_LINE_ARGS = 1;
	static final int EXIT_UNHANDLED_ERROR = 2;

	static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/CanadaCensusDB";
	static final String CONNECTION_USER = "user";
	static final String CONNECTION_PASSWORD = "zmag";

	static final String BASE_QUERY = "SELECT g.*, a.combined, a.male, a.female FROM GEOGRAPHICAREA g JOIN AGE a ON a.geographicArea = g.geographicAreaID WHERE ageGroup = 1 AND censusYear = 1";

	List<GeographicArea> geographicAreas;
	String testOutput = "Hey bby";
	String testArray[] = { "1", "2", "3", "4" };

	public String[] getTestOutput() {
		return testArray;
	}

	private static Connection OpenConnection() throws SQLException, ClassNotFoundException {
		Properties connProperties = new Properties();
		connProperties.put("user", CONNECTION_USER);
		connProperties.put("password", CONNECTION_PASSWORD);

		Class.forName("org.apache.derby.jdbc.ClientDriver");
		Connection conn = DriverManager.getConnection(CONNECTION_STRING, connProperties);
		conn.setAutoCommit(false);
		conn.createStatement().executeUpdate("SET SCHEMA APP");

		return conn;
	}

	
	public int getHouseholdsMatchingAreaSQL(String area, Connection dbConn) {

		int householdCount=0;
		String query ="SELECT COUNT(*) from Household h inner join CensusYear y on h.censusYear=y.censusYearId "
				+ "inner join GeographicArea a on h.geographicArea = a.geographicAreaId "
				+ "inner join householdEarners e on h.householdEarners=e.id "
				+ "inner join householdSize s on h.householdSize=s.id "
				+ "inner join totalIncome i on h.totalIncome=i.id where (TRIM(CAST(CAST(a.alternativeCode AS CHAR(30)) AS VARCHAR(30))) LIKE '"+area+ "%')"
						+ " and y.censusYear=2016 and e.description='1 earner or more'"
						+ " and s.description='2 or more persons' and i.description='$80,000 to $89,999'";

		
		try (Statement statement = dbConn.createStatement(); ResultSet result = statement.executeQuery(query)) {
			
			while (result.next()) {
				householdCount=Integer.parseInt(result.getString("1"));
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			System.exit(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(EXIT_UNHANDLED_ERROR);
		}
		return householdCount;
	}
	
	public int getHouseholdsMatchingArea(String level, Connection dbConn) {

		 EntityManagerFactory tempEntityManagerFactory = null;
	        EntityManager tempEntityManager = null;
	        int householdCount = 0;

	        try
	        {

	            tempEntityManagerFactory = Persistence.createEntityManagerFactory("ZMAG_A2");
	            tempEntityManager = tempEntityManagerFactory.createEntityManager();
	            tempEntityManager.getTransaction().begin();
	            
	            
	            String queryString = "FROM Household "
	                    + "WHERE geographicArea.level = :level and censusYear.censusYear= :censusYear and householdEarners.description= :householdEarners"
	                    + " and householdSize.description= :householdSize and totalIncome.description= :totalIncome";

	            Query tempQuery = tempEntityManager.createQuery(queryString)
	            		.setParameter("level", level).setParameter("censusYear", 2016).setParameter("householdEarners", "1 earner or more").
	            		setParameter("householdSize", "2 or more persons").setParameter("totalIncome", "$80,000 to $89,999");
	            
	            householdCount=  tempQuery.getResultList().size();
	            
	            
	        } catch (Exception e)
	        {
	            if (tempEntityManager != null)
	            {
	                tempEntityManager.getTransaction().rollback();
	            }

	            e.printStackTrace();
	        }
	        finally
	        {
	            if (tempEntityManager != null)
	            {
	                tempEntityManager.close();
	            }

	            if (tempEntityManagerFactory != null)
	            {
	                tempEntityManagerFactory.close();
	            }
	        }

		return householdCount;

	}

	public List<GeographicArea> getGeographicAreasByID(String id, Connection dbConn) {
		String query = BASE_QUERY;
		if (id != null) {
			query = BASE_QUERY + " AND geographicAreaID = " + id;
		}
		query += " ORDER BY level, name";
		return getGeographicAreas(query, dbConn);
	}

	public List<GeographicArea> getGeographicAreasByParent(String id, String code, String level, Connection dbConn) {
		String query = BASE_QUERY;
		if (code != null) {
			// Look at all these hoops I have to jump through since derby can't cast an int
			// as a varchar
			query = BASE_QUERY + " AND (TRIM(CAST(CAST(alternativeCode AS CHAR(30)) AS VARCHAR(30))) LIKE '" + code
					+ "%' OR geographicAreaId = " + id + ") AND level > " + level;

		}
		query += " ORDER BY level, name";
		return getGeographicAreas(query, dbConn);
	}

	public List<GeographicArea> getGeographicAreasByLevel(String level, Connection dbConn) {
		String query = BASE_QUERY;
		if (level != null) {
			query = BASE_QUERY + " AND level = " + level;
		}
		query += " ORDER BY level, name";
		return getGeographicAreas(query, dbConn);
	}

	public List<GeographicArea> getGeographicAreas(String query, Connection dbConn) {
		try (Statement statement = dbConn.createStatement(); ResultSet result = statement.executeQuery(query)) {
			List<GeographicArea> resultSet = new ArrayList<GeographicArea>();

			while (result.next()) {
				GeographicArea tempGeoArea = new GeographicArea();
				tempGeoArea.setGeographicAreaID(Integer.parseInt(result.getString("geographicAreaID")));
				tempGeoArea.setCode(Integer.parseInt(result.getString("code")));
				tempGeoArea.setAlternativeCode(Integer.parseInt(result.getString("alternativeCode")));
				tempGeoArea.setLevel(Integer.parseInt(result.getString("level")));
				tempGeoArea.setName(result.getString("name"));
				tempGeoArea.setTotalPopulation(Integer.parseInt(result.getString("combined")));
				tempGeoArea.setMalePopulation(Integer.parseInt(result.getString("male")));
				tempGeoArea.setFemalePopulation(Integer.parseInt(result.getString("female")));

				resultSet.add(tempGeoArea);
			}
			dbConn.rollback();
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(EXIT_UNHANDLED_ERROR);
		}
		return null;
	}

	public List<AgeGroup> getAgeGroupPopulation(Connection dbConn) {
		String query = "SELECT a.censusYear, ag.description, a.male, a.female FROM AGE a JOIN AGEGROUP ag ON ag.agegroupid = a.agegroup"
				+ " WHERE ag.agegroupid in(3, 9, 15, 22, 28, 34, 40, 46, 52, 58, 64, 70, 76, 83, 89, 95, 101, 108, 114, 120, 126)"
				+ " AND geographicArea = 1 ORDER BY ag.description";
		try (Statement statement = dbConn.createStatement(); ResultSet result = statement.executeQuery(query)) {
			List<AgeGroup> resultSet = new ArrayList<AgeGroup>();
			String current_ageGroup = "";
			String last_ageGroup = "";
			AgeGroup tempAgeGroup = new AgeGroup();
			while (result.next()) {
				current_ageGroup = result.getString("description");

				// tempAgeGroup.setAgegroup(current_ageGroup);
				if (result.getString("censusyear").equals("1")) {
					// tempAgeGroup.setMalePopulation2016(Integer.parseInt(result.getString("male")));
					// tempAgeGroup.setFemalePopulation2016(Integer.parseInt(result.getString("female")));
				} else {
					// tempAgeGroup.setMalePopulation2011(Integer.parseInt(result.getString("male")));
					// tempAgeGroup.setFemalePopulation2011(Integer.parseInt(result.getString("female")));
				}
				if (current_ageGroup.equals(last_ageGroup)) {
					resultSet.add(tempAgeGroup);
					tempAgeGroup = new AgeGroup();
				} else {
					last_ageGroup = current_ageGroup;
				}
			}
			dbConn.rollback();
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(EXIT_UNHANDLED_ERROR);
		}
		return null;
	}
}
