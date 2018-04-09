package ar.edu.unq.epers.woe.backend.razadao;

import java.sql.*;
import java.util.Set;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;

public class RazaDao {

	//registra el driver que se va a usar al instanciar la clase
	public RazaDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("No se puede encontrar la clase del driver", e);
		}
	}

	//implementación del método deleteAll
	public void deleteAll() {
		this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement("TRUNCATE raza;");
			ps.execute();
			ps.close();
			return null;
		});
	}

	//implementación del método crearRaza
	public void crearRaza(Raza raza) {
		raza.setId(this.nextId());
		this.guardar(raza);
	}

	//retorna un String con la lista de nombres de clases separados por ","
	public String clasesAStringDelimitado(Set<Clase> clases) {
		String res = "";
		for (Clase clase : clases) {
			res = res + "," + clase.name();
		}
		return res.substring(1);
	}

	//retorna el siguiente Id disponible para una raza
	public Integer nextId() {
		Connection conn = this.openConnection("jdbc:mysql://localhost:3306/epers_woe?user=root&password=root&useSSL=false");
		Integer n = null;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(idRaza) FROM raza;");
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				n = resultSet.getInt("MAX(idRAza)");
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n + 1;
	}

	//inserta los datos de una raza en la db
	public void guardar(Raza raza) {
		this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO raza (idRaza, nombre, clases, peso, alt, energiaI, urlFoto, cantP ) VALUES (?,?,?,?,?,?,?,?)");
			ps.setInt(1, raza.getId());
			ps.setString(2, raza.getNombre());
			ps.setString(3, clasesAStringDelimitado(raza.getClases()));
			ps.setInt(4, new Integer(raza.getPeso()));
			ps.setInt(5, new Integer(raza.getAltura()));
			ps.setInt(6, new Integer(raza.getEnergiaInicial()));
			ps.setString(7, raza.getUrlFoto());
			ps.setInt(8, new Integer(raza.getCantidadPersonajes()));

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
