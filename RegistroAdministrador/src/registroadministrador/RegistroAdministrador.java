/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package registroadministrador;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class RegistroAdministrador {
    private static final String URL = "jdbc:mysql://localhost:3306/votaSoft";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        SwingUtilities.invokeLater(() -> crearYMostrarVentana());
    }

    private static void crearYMostrarVentana() {
        JFrame frame = new JFrame("Registro de Administrador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField nombreField = new JTextField(20);
        JTextField apellidoField = new JTextField(20);
        JTextField numeroIdentificacionField = new JTextField(20);
        JComboBox<String> tipoIdentificacionComboBox = new JComboBox<>(new String[]{"Tarjeta de Identidad", "Cédula de Ciudadanía", "Cédula de Extranjería", "Pasaporte"});
        JTextField correoElectronicoField = new JTextField(20);
        JTextField rolField = new JTextField(20);
        JTextField hashContraseñaField = new JTextField(20);

        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Apellido:"));
        panel.add(apellidoField);
        panel.add(new JLabel("Número de Identificación:"));
        panel.add(numeroIdentificacionField);
        panel.add(new JLabel("Tipo de Identificación:"));
        panel.add(tipoIdentificacionComboBox);
        panel.add(new JLabel("Correo Electrónico:"));
        panel.add(correoElectronicoField);
        panel.add(new JLabel("Rol:"));
        panel.add(rolField);
        panel.add(new JLabel("Hash Contraseña:"));
        panel.add(hashContraseñaField);

        JButton addButton = new JButton("Agregar Administrador");
        addButton.addActionListener(e -> {
            try {
                agregarAdministrador(nombreField.getText(), apellidoField.getText(), numeroIdentificacionField.getText(),
                        (String) tipoIdentificacionComboBox.getSelectedItem(), correoElectronicoField.getText(), rolField.getText(),
                        hashContraseñaField.getText());
                JOptionPane.showMessageDialog(frame, "Administrador agregado correctamente.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error al agregar administrador: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(addButton);

        frame.pack();
        frame.setVisible(true);
    }

    private static void agregarAdministrador(String nombre, String apellido, String numeroIdentificacion, String tipoIdentificacion,
                                              String correoElectronico, String rol, String hashContraseña) throws SQLException {
        String sql = "INSERT INTO administrador (nombre, apellido, numeroIdentificacion, tipoIdentificacion, correoElectronico, rol, hashContraseña) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, numeroIdentificacion);
            preparedStatement.setString(4, tipoIdentificacion);
            preparedStatement.setString(5, correoElectronico);
            preparedStatement.setString(6, rol);
            preparedStatement.setString(7, hashContraseña);

            preparedStatement.executeUpdate();
        }
    }
}
