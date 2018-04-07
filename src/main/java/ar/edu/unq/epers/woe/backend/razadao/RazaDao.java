package ar.edu.unq.epers.woe.backend.razadao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ar.edu.unq.epers.woe.backend.model.raza.Raza;

public class RazaDao {
	
	//inserta los datos de una raza en la db
	public void guardar(Raza raza) {
		this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO raza (idRaza, nombre, clases, peso, alt, energiaI, urlFoto, cantP ) VALUES (?,?,?,?,?,?,?,?)");
			ps.setString(1, raza.getId().toString());
			ps.setString(1, raza.getNombre());
			ps.setString(1, raza.getClases().toString());
			ps.setString(1, new Integer(raza.getPeso()).toString() );
			ps.setString(1, new Integer(raza.getAltura()).toString() );
			ps.setString(1, new Integer(raza.getEnergiaInicial()).toString() );
			ps.setString(1, raza.getUrlFoto());
			ps.setString(1, new Integer(raza.getCantidadPersonajes()).toString() );

			ps.execute();

			if (ps.getUpdateCount() != 1) {
				throw new RuntimeException("No se inserto la raza");
			}
			ps.close();

			return null;
		}
		);
	}
	
	
	/**
	 * Ejecuta un bloque de codigo contra una conexion.
	 */
	private <T> T executeWithConnection(ConnectionBlock<T> bloque) {
		Connection connection = this.openConnection("jdbc:mysql://localhost:3306/epers_woe?user=root&password=root");
		try {
			return bloque.executeWith(connection);
		} catch (SQLException e) {
			throw new RuntimeException("Error no esperado", e);
		} finally {
			this.closeConnection(connection);
		}
	}

	/**
	 * Establece una conexion a la url especificada
	 * @param url - la url de conexion a la base de datos
	 * @return la conexion establecida
	 */
	private Connection openConnection(String url) {
		try {
			//La url de conexion no deberia estar harcodeada aca
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/epers_woe?user=root&password=root&useSSL=false");

		} catch (SQLException e) {
			throw new RuntimeException("No se puede establecer una conexion", e);
		}
	}

	/**
	 * Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
	 * @param connection - la conexion a cerrar.
	 */
	private void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException("Error al cerrar la conexion", e);
		}
	}

}
