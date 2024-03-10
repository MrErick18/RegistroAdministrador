
package registroadministrador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private Connection connection;

    public AdminDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar un nuevo administrador
    public void insertarAdmin(Admin admin) throws SQLException {
        String sql = "INSERT INTO Usuarios (nombre, apellido, numeroIdentificacion, tipoIdentificacion, correoElectronico, rol, hashContraseña) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, admin.getNombre());
            preparedStatement.setString(2, admin.getApellido());
            preparedStatement.setString(3, admin.getNumeroIdentificacion());
            preparedStatement.setString(4, admin.getTipoIdentificacion());
            preparedStatement.setString(5, admin.getCorreoElectronico());
            preparedStatement.setString(6, admin.getRol());
            preparedStatement.setString(7, admin.getHashContraseña());

            preparedStatement.executeUpdate();
        }
    }

    // Método para obtener todos los administradores
    public List<Admin> obtenerTodosAdmins() throws SQLException {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios WHERE rol = 'administrador'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setId(resultSet.getInt("id"));
                admin.setNombre(resultSet.getString("nombre"));
                admin.setApellido(resultSet.getString("apellido"));
                admin.setNumeroIdentificacion(resultSet.getString("numeroIdentificacion"));
                admin.setTipoIdentificacion(resultSet.getString("tipoIdentificacion"));
                admin.setCorreoElectronico(resultSet.getString("correoElectronico"));
                admin.setRol(resultSet.getString("rol"));
                admin.setHashContraseña(resultSet.getString("hashContraseña"));

                admins.add(admin);
            }
        }
        return admins;
    }

    // Otros métodos para actualizar y eliminar administradores
}
