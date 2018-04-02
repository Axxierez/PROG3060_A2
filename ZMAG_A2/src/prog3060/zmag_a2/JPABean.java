// ----------------------------------------------------
// Zack Meadows & Alex Galka
// PROG3060 Assignment 2
// 2018-03-27
// ----------------------------------------------------

package prog3060.zmag_a2;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JPABean {
	
    static final String PERSISTENCE_UNIT_NAME = "ZMAG_A2";
    
    static EntityManagerFactory entityMF = null;
    static EntityManager entityManager = null;
    boolean validConn = false;

	public boolean isValid() {
		return validConn;
	}
	public int parseStringToInt(String input) {
		int output = 0;
		try
		{
		    output = Integer.parseInt(input);
		}
		catch (NumberFormatException e)
		{
			output = 0;
		}
		return output;
	}
	public boolean openConn(String user, String pass) {
        try
        {
	    	validConn = true;
	    	Map<String, String> properties = new HashMap<String, String>();
	    	properties.put("hibernate.connection.username", user);
	    	properties.put("hibernate.connection.password", pass);
	    	
	    	entityMF = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
	    	entityManager = entityMF.createEntityManager();
	    	return true;
        }
        catch (Exception e)
        {
	    	validConn = false;
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
	}
	
	public void closeConn() {
        entityManager.getTransaction().rollback();
        if (entityManager != null)
        	entityManager.close();
        if (entityMF != null)
        	entityMF.close();
    	validConn = false;
	}
	
	public List<Object[]> getHouseholdsByArea(int id) {
    	String jpqlQuery = "SELECT h FROM Household h"
    			+ " WHERE h.geographicArea.geographicAreaID = :id AND h.censusYear.censusYear = 2016"
    			+ " AND h.householdEarners.description LIKE '1 earner or more'"
    			+ " AND h.householdsByAgeRange.description LIKE 'Total - Households by number of persons aged 0 to 17 years'"
    			+ " AND h.householdSize.description LIKE '2 or more persons'"
    			+ " AND h.householdType.description LIKE 'One couple census family without other persons in the household'"
    			+ " AND h.totalIncome.description LIKE '$80,000 to $89,999'";
    		
	    Query query = entityManager.createQuery(jpqlQuery);
	    query.setParameter("id", id);

		return doQuery(query);
	}
	
	public List<Object[]> getAllGeographicAreas() {
    	String jpqlQuery = "SELECT a, ga FROM Age a"
    			+ " JOIN a.geographicArea ga "
    			+ " WHERE a.ageGroup = 1 AND a.censusYear = 1"
    			+ " ORDER BY ga.level, ga.name";
    	
    	Query query = entityManager.createQuery(jpqlQuery);
		return doQuery(query);
	}
	
	public List<Object[]> getGeographicAreasByID(int id) {
    	String jpqlQuery = "SELECT a, ga FROM Age a"
    			+ " JOIN a.geographicArea ga "
    			+ " WHERE a.ageGroup = 1 AND a.censusYear = 1";
    	String order = " ORDER BY ga.level, ga.name";
    	
    	Query query = null;
    	if(id != 0) {
			jpqlQuery +=" AND ga.geographicAreaID = :id"
					+ order;
			
	    	query = entityManager.createQuery(jpqlQuery);
	    	query.setParameter("id", id);
    	}
    	else {
    		jpqlQuery += order;
        	query = entityManager.createQuery(jpqlQuery);
    	}
    	
		return doQuery(query);
	}

	public List<Age> getCensusYearPopulation(int id,int year) {
    	String jpqlQuery = "SELECT a FROM Age a"
    			+ " WHERE a.ageGroup = 1 AND a.censusYear.censusYear = :censusYear";
    	String order = " ORDER BY a.geographicArea.level, a.geographicArea.name";
    	
    	Query query = null;
    	if(year != 0) {
			jpqlQuery +=" AND a.geographicArea.geographicAreaID = :id"
					+ order;
			
	    	query = entityManager.createQuery(jpqlQuery);
	    	query.setParameter("id", id);
	    	query.setParameter("censusYear", year);
    	}
    	else {
    		jpqlQuery += order;
        	query = entityManager.createQuery(jpqlQuery);
    	}
    	
    	try {
        	entityManager.getTransaction().begin();
        	
        	List <Age> resultSet = query.getResultList();
			return resultSet;
		}
        catch (Exception e)
        {
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
        }
		return null;
	}
	
	public List<Object[]> getGeographicAreasByParent(int code, int level) {
    	String jpqlQuery = "SELECT a, ga FROM Age a"
    			+ " JOIN a.geographicArea ga "
    			+ " WHERE a.ageGroup = 1 AND a.censusYear = 1";
    	String order = " ORDER BY ga.alternativeCode DESC";
    	
    	
    	Query query = null;
    	if(code != 0) {
			jpqlQuery += " AND ga.level > :level"
					+ order;
			
	    	query = entityManager.createQuery(jpqlQuery);
	    	query.setParameter("level", level);
	    	
	    	try {
	        	entityManager.getTransaction().begin();
	        	
	        	List<Object[]> tempResultSet = new LinkedList<Object[]>();
	        	List <Object[]> resultSet = query.getResultList();        	
	        	
	        	for(int i = 0; i<resultSet.size();i++) {
	        		GeographicArea tempGa= (GeographicArea) resultSet.get(i)[1];
	        		if (Integer.toString(tempGa.getAlternativeCode()).startsWith(Integer.toString(code))) {
						tempResultSet.add(resultSet.get(i));
					}
	        	}
				return tempResultSet;
			}
	        catch (Exception e)
	        {
	            if (entityManager != null)
	            	entityManager.getTransaction().rollback();
	            e.printStackTrace();
	        }
	        finally
	        {
	            if (entityManager != null)
	            	entityManager.getTransaction().rollback();
	        }
	    	
    	}
    	else {
    		jpqlQuery += order;
        	query = entityManager.createQuery(jpqlQuery);
    	}
    	
		return doQuery(query);
	}

	public List<Object[]> getGeographicAreasByLevel(int level) {
    	String jpqlQuery = "SELECT a, ga FROM Age a"
    			+ " JOIN a.geographicArea ga "
    			+ " WHERE a.ageGroup = 1 AND a.censusYear = 1";
    	String order = " ORDER BY ga.level, ga.name";
    	
    	Query query = null;
    	if(level != 0) {
			jpqlQuery += " AND ga.level = :level"
					+ order;
			
	    	query = entityManager.createQuery(jpqlQuery);
	    	query.setParameter("level", level);
    	}
    	else {
    		jpqlQuery += order;
        	query = entityManager.createQuery(jpqlQuery);
    	}
		return doQuery(query);
	}

	private List <Object[]> doQuery(Query query) {
		try {
        	entityManager.getTransaction().begin();
        	
        	List <Object[]> resultSet = query.getResultList();
			return resultSet;
		}
        catch (Exception e)
        {
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
        }
		return null;
	}

	public List<Object[]> getAgeGroupPopulation(int censusYear) {
    	String jpqlQuery = "SELECT a, ag FROM Age a"
    			+ " JOIN a.ageGroup ag "
    			+ " WHERE ag.ageGroupID IN (3, 9, 15, 22, 28, 34, 40, 46, 52, 58, 64, 70, 76, 83, 89, 95, 101, 108, 114, 120, 126)"
    			+ " AND a.geographicArea.geographicAreaID = 1"
    			+ " AND a.censusYear.censusYear = :censusYear"
    			+ "	ORDER BY ag.description";
    	Query query = entityManager.createQuery(jpqlQuery);
    	query.setParameter("censusYear", censusYear);
    	
		try {
        	entityManager.getTransaction().begin();
        	
        	List <Object[]> resultSet = query.getResultList();
			return resultSet;
		}
        catch (Exception e)
        {
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
        }
		return null;}
	
	public List<Household> getMedianHouseholdIncome() {
    	String jpqlQuery = "select h FROM Household h"
    			+ " WHERE h.censusYear.censusYear=2016"
    			+ " AND h.householdSize.description like '2 or more persons'"
    			+ " AND h.householdType.description like 'One couple census family without other persons in the household'"
    			+ " AND h.householdEarners.description like '1 earner or more'"
    			+ " AND h.householdsByAgeRange.id = 9"
    			+ " AND h.geographicArea.level=1"
    			+ " AND h.totalIncome=22 order by h.numberReported desc"
    		;
    	
    	Query query = entityManager.createQuery(jpqlQuery);
    	
		try {
        	entityManager.getTransaction().begin();
        	
        	List <Household> resultSet = query.getResultList();
			return resultSet;
		}
        catch (Exception e)
        {
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
        }
		return null;}
	
}
