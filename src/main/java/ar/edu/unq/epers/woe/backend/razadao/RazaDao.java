package ar.edu.unq.epers.woe.backend.razadao;

import java.sql.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.service.data.DataService;
import ar.edu.unq.epers.woe.backend.service.raza.RazaNoExistente;

public class RazaDao implements DataService {

	private String connURL = "jdbc:mysql://localhost:3306/epers_woe?user=root&password=root&useSSL=false";

	private String getConnURL() {
		return connURL;
	}

	public void setConnURL(String connURL) {
		this.connURL = connURL;
	}

	//trae todas las razas de la db ordenadas alfabéticamente y las agrega a la lista
	public void agregarRazasOrdenadas(List<Raza> res) {
		Connection conn = this.openConnection(this.getConnURL());
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM raza order by nombre;");
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Raza raza = new Raza();
				raza.setId(resultSet.getInt("idRaza"));
				raza.setNombre(resultSet.getString("nombre"));
				raza.setAltura(resultSet.getInt("alt"));
				raza.setClases(this.stringDelimitadoAClases(resultSet.getString("clases")));
				raza.setEnergiaIncial(resultSet.getInt("energiaI"));
				raza.setPeso(resultSet.getInt("peso"));
				raza.setUrlFoto(resultSet.getString("urlFoto"));
				raza.setCantidadPersonajes(resultSet.getInt("cantP"));
				res.add(raza);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(conn);
		}
	}

	//implementación del método crearSetDatosIniciales
	public void crearSetDatosIniciales() {
		Raza raza1 = new Raza();
		Set<Clase> clases1 = new HashSet<Clase>();
		clases1.add(Clase.SACERDOTE);
		clases1.add(Clase.MAGO);

		raza1.setNombre("xRaza1");
		raza1.setAltura(55);
		raza1.setClases(clases1);
		raza1.setEnergiaIncial(10);
		raza1.setPeso(50);
		raza1.setUrlFoto("url_dest1");
		raza1.setCantidadPersonajes(0);

		Raza raza2 = new Raza();
		Set<Clase> clases2 = new HashSet<Clase>();
		clases2.add(Clase.BRUJO);

		raza2.setNombre("yRaza2");
		raza2.setAltura(182);
		raza2.setClases(clases1);
		raza2.setEnergiaIncial(158);
		raza2.setPeso(90);
		raza2.setUrlFoto("url_dest2");
		raza2.setCantidadPersonajes(2);

		raza1.crearRaza(raza1);
		raza2.crearRaza(raza2);

	}

	//recupera de la db los atributos de la raza con el id recibido como parámetro y los setea a la raza recibida como parámetro
	public void recuperar_raza(Integer id, Raza raza) {
		Connection conn = this.openConnection(this.getConnURL());
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM raza where idRaza = ?;");
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet .next()) {

				raza.setId(id);
				raza.setNombre(resultSet.getString("nombre"));
				raza.setAltura(resultSet.getInt("alt"));
				raza.setClases(this.stringDelimitadoAClases(resultSet.getString("clases")));
				raza.setEnergiaIncial(resultSet.getInt("energiaI"));
				raza.setPeso(resultSet.getInt("peso"));
				raza.setUrlFoto(resultSet.getString("urlFoto"));
				raza.setCantidadPersonajes(resultSet.getInt("cantP"));
				
			}
			if( !(resultSet.first()) ) {
				throw new RazaNoExistente(id);
			}
			ps.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(conn);
		}
	}

	//recibe un string con nombres de clases separados por ',' y retorna un conjunto de Clase con un miembro por cada una
	public Set<Clase> stringDelimitadoAClases(String clases) {
		String[] rec = clases.split(",");
		Set<Clase> res = new java.util.HashSet<Clase>();
		for (String clase : rec) {
			res.add(Clase.valueOf(clase));
		}
		return res;
	}

	//registra el driver que se va a usar al instanciar la clase
	public RazaDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("No se puede encontrar la clase del driver", e);
		}
	}

	//implementación del método deleteAll
	@Override
	public void eliminarDatos() {
		this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement("TRUNCATE raza;");
			ps.execute();
			ps.close();
			return null;
		});
	}

	//retorna un String con la lista de nombres de clases separados por ","
	public String clasesAStringDelimitado(Set<Clase> clases) {
		String res = "";
		if (!clases.isEmpty()) {
			for (Clase clase : clases) {
				res = res + "," + clase.name();
			}
			res = res.substring(1);
		}
		return res;
	}

	//inserta los datos de una raza en la db
	public void guardar(Raza raza) {
		this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO raza (nombre, clases, peso, alt, energiaI, urlFoto, cantP ) VALUES (?,?,?,?,?,?,?)");
			//ps.setInt(1, raza.getId());
			ps.setString(1, raza.getNombre());
			ps.setString(2, clasesAStringDelimitado(raza.getClases()));
			ps.setInt(3, raza.getPeso());
			ps.setInt(4, raza.getAltura());
			ps.setInt(5, raza.getEnergiaInicial());
			ps.setString(6, raza.getUrlFoto());
			ps.setInt(7, raza.getCantidadPersonajes());

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
		Connection connection = this.openConnection(this.getConnURL());
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
			return DriverManager.getConnection(url);

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

	//incrementa en 1 el valor de la columna cantP de la raza con el id recibido como parámetro
	public void incrementarPjs(Integer razaId) {
		Raza raza = new Raza();
		this.recuperar_raza(razaId, raza);
		Integer cantActual = raza.getCantidadPersonajes() + 1;
		this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement("UPDATE raza set cantP = ? where idRaza = ?;");
			ps.setInt(1, cantActual);
			ps.setInt(2, razaId);
			ps.execute();
			ps.close();
			return null;
		});
	}


}
