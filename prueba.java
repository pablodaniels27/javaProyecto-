import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class prueba extends JFrame implements ActionListener {
    private JTextField doctorField, pacienteField, citaField;
    private JButton confirmarButton, limpiarButton;

    public prueba() {
        setTitle("VitaSalud - Información de Cita");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        doctorField = new JTextField();
        pacienteField = new JTextField();
        citaField = new JTextField();

        confirmarButton = new JButton("Confirmar Cita");
        confirmarButton.addActionListener(this);

        limpiarButton = new JButton("Limpiar Formulario");
        limpiarButton.addActionListener(this);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Doctor:"));
        panel.add(doctorField);
        panel.add(new JLabel("Paciente:"));
        panel.add(pacienteField);
        panel.add(new JLabel("Fecha de cita:"));
        panel.add(citaField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmarButton);
        buttonPanel.add(limpiarButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmarButton) {
            String doctor = doctorField.getText().trim();
            String paciente = pacienteField.getText().trim();
            String fechaCita = citaField.getText().trim();

            // Validar doctor y paciente
            if (!isValidText(doctor) || !isValidText(paciente)) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese solo texto para el Doctor y el Paciente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (fechaCita.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos antes de confirmar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Validar la fecha
                if (isValidDate(fechaCita)) {
                    // Mostrar confirmación
                    int confirmacion = JOptionPane.showConfirmDialog(this, "¿Son correctos los datos?\nDoctor: " + doctor + "\nPaciente: " + paciente + "\nFecha de cita: " + fechaCita, "Confirmar Cita", JOptionPane.OK_CANCEL_OPTION);

                    if (confirmacion == JOptionPane.OK_OPTION) {
                        // Guardar datos en un archivo
                        guardarEnArchivo(doctor, paciente, fechaCita);

                        JOptionPane.showMessageDialog(this, "¡Cita confirmada! ¡Esperamos verte pronto!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada no es válida. Utilice el formato dd de MMMM de yyyy (por ejemplo, 28 de octubre de 2023).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == limpiarButton) {
            limpiarFormulario();
        }
    }

    private void limpiarFormulario() {
        doctorField.setText("");
        pacienteField.setText("");
        citaField.setText("");
    }

    private boolean isValidText(String text) {
        // Validar que solo contiene caracteres alfabéticos
        return text.matches("^[a-zA-Z]+$");
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
        dateFormat.setLenient(false);

        try {
            // Intentar parsear la fecha
            Date parsedDate = dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void guardarEnArchivo(String doctor, String paciente, String fechaCita) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cita.txt", true))) {
            // Escribir los datos en el archivo
            writer.write("Doctor: " + doctor);
            writer.newLine();
            writer.write("Paciente: " + paciente);
            writer.newLine();
            writer.write("Fecha de cita: " + fechaCita);
            writer.newLine();
            writer.write("---------------------------");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new prueba();
    }
}
