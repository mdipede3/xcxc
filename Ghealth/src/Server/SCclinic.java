/**
 * TODO This is the class description
 */


package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import models.*;


/**
 * @author G5 lab group
 * The Class SCclinic.
 */
public class SCclinic {


	PreparedStatement stmt = null; 
	ResultSet result = null;

	/**
	 * Gets the our clinic list.
	 *
	 * @return the envelope
	 */
	public static Envelope GetOurClinicList()
	{
		Envelope en = new Envelope();
		String querystr;
		PreparedStatement stmt = null; 
		ResultSet result = null;
		
		querystr="SELECT * "
				+ "FROM clinic";
		
		try 
		{
			stmt = mysqlConnection.autoConn.getDbConnession().prepareStatement(querystr);
			
			result = getSql(querystr);
			while (result.next())
            {
				en.addobjList(new Clinic(result.getInt(1),result.getString(2),result.getString(3)));
				
            }   
		}
		catch (SQLException ex) 
   	    {/* handle any errors*/
          
         
        }
		return en;
		
	}
	
	/**
	 * Assegna i valori passati come parametri allo statements.
	 * 
	 * @param statement
	 * @param fields
	 * @throws SQLException
	 */
	private static void assignValues(PreparedStatement statement, Object... fields) throws SQLException {
		int index = 0;
		for (Object obj : fields) {
			statement.setObject(++index, obj);
		}
	}
	

	/**
	 * Getting SQL query results back from DB
	 * 
	 * @param query
	 */
	public static ResultSet getSql(String query) {
		PreparedStatement stmt = null; 
		ResultSet result = null;
		try {
			stmt = mysqlConnection.autoConn.getDbConnession().prepareStatement(query);
			result = stmt.executeQuery();
			// mysqlConnection.autoConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return result;
	}

	/**
	 * Getting SQL query results back from DB with params
	 * 
	 * @param query
	 */
	public static ResultSet getSql(String query, Object... fields) {
		PreparedStatement stmt = null; 
		ResultSet result = null;
		try {
			stmt = mysqlConnection.autoConn.getDbConnession().prepareStatement(query);
			assignValues(stmt, fields);
			result = stmt.executeQuery();
			// mysqlConnection.autoConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return result;
	}

	/**
	 * Sending SQL query.
	 *
	 * @param query
	 *            the new SQL query
	 */
	public static void setSql(String query) {
		PreparedStatement stmt = null; 
		ResultSet result = null;
		try {
			stmt = mysqlConnection.autoConn.getDbConnession().prepareStatement(query);
			stmt.executeUpdate();
			// mysqlConnection.autoConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	/**
	 * Sending SQL query with params.
	 *
	 * @param query
	 *            the new SQL query
	 */
	public static void setSql(String query, Object...fields) {
		PreparedStatement stmt = null; 
		ResultSet result = null;
		try {
			stmt = mysqlConnection.autoConn.getDbConnession().prepareStatement(query);
			assignValues(stmt, fields);
			stmt.executeUpdate();
			// mysqlConnection.autoConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	
}
