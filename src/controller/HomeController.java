package controller;

import javax.swing.JOptionPane;
import java.util.Date;
import javax.swing.JFileChooser;
import model.PinjamModel;
import view.Home;

/**
 *
 * @author Faruq
 */
public class HomeController {
    private final Home view;
    private final PinjamModel pinjamModel;

    public HomeController(Home view) {
        this.view = view;
        this.pinjamModel = view.getPinjamModel();
    }

    public void updateLantaiOptions() {
        view.getLantaiComboBox().removeAllItems();
        view.getRuanganComboBox().removeAllItems();

        String selectedGedung = (String) view.getGedungComboBox().getSelectedItem();
        if (selectedGedung != null) {
            switch (selectedGedung) {
                case "2":
                    view.getLantaiComboBox().addItem(null);
                    view.getLantaiComboBox().addItem("1");
                    view.getLantaiComboBox().addItem("5");
                    view.getLantaiComboBox().addItem("6");
                    break;
                case "3":
                    view.getLantaiComboBox().addItem(null);
                    view.getLantaiComboBox().addItem("2");
                    view.getLantaiComboBox().addItem("3");
                    view.getLantaiComboBox().addItem("4");
                    break;
                default:
                    view.getLantaiComboBox().setSelectedItem(null);
                    break;
            }
        }

        updateRuanganOptions();
    }

    public void updateRuanganOptions() {
        view.getRuanganComboBox().removeAllItems();

        String selectedGedung = (String) view.getGedungComboBox().getSelectedItem();
        String selectedLantai = (String) view.getLantaiComboBox().getSelectedItem();

        if (selectedGedung != null && selectedLantai != null) {
            if (selectedGedung.equals("2")) {
                switch (selectedLantai) {
                    case "1":
                        view.getRuanganComboBox().addItem("Auditorium");
                        break;
                    case "5":
                        view.getRuanganComboBox().addItem(null);
                        view.getRuanganComboBox().addItem("251");
                        view.getRuanganComboBox().addItem("252");
                        view.getRuanganComboBox().addItem("253");
                        view.getRuanganComboBox().addItem("254");
                        view.getRuanganComboBox().addItem("255");
                        view.getRuanganComboBox().addItem("256");
                        view.getRuanganComboBox().addItem("257");
                        view.getRuanganComboBox().addItem("258");
                        break;
                    case "6":
                        view.getRuanganComboBox().addItem(null);
                        view.getRuanganComboBox().addItem("261");
                        view.getRuanganComboBox().addItem("262");
                        view.getRuanganComboBox().addItem("263");
                        view.getRuanganComboBox().addItem("264");
                        view.getRuanganComboBox().addItem("265");
                        view.getRuanganComboBox().addItem("266");
                        view.getRuanganComboBox().addItem("267");
                        view.getRuanganComboBox().addItem("268");
                        break;
                }
            } else if (selectedGedung.equals("3")) {
                switch (selectedLantai) {
                    case "2":
                        view.getRuanganComboBox().addItem(null);
                        view.getRuanganComboBox().addItem("321");
                        view.getRuanganComboBox().addItem("322");
                        view.getRuanganComboBox().addItem("323");
                        view.getRuanganComboBox().addItem("324");
                        view.getRuanganComboBox().addItem("325");
                        view.getRuanganComboBox().addItem("326");
                        view.getRuanganComboBox().addItem("327");
                        view.getRuanganComboBox().addItem("328");
                        break;
                    case "3":
                        view.getRuanganComboBox().addItem(null);
                        view.getRuanganComboBox().addItem("331");
                        view.getRuanganComboBox().addItem("332");
                        view.getRuanganComboBox().addItem("333");
                        view.getRuanganComboBox().addItem("334");
                        view.getRuanganComboBox().addItem("335");
                        view.getRuanganComboBox().addItem("336");
                        view.getRuanganComboBox().addItem("337");
                        view.getRuanganComboBox().addItem("338");
                        break;
                    case "4":
                        view.getRuanganComboBox().addItem(null);
                        view.getRuanganComboBox().addItem("341");
                        view.getRuanganComboBox().addItem("342");
                        view.getRuanganComboBox().addItem("343");
                        view.getRuanganComboBox().addItem("344");
                        view.getRuanganComboBox().addItem("345");
                        view.getRuanganComboBox().addItem("346");
                        view.getRuanganComboBox().addItem("347");
                        view.getRuanganComboBox().addItem("348");
                        break;
                }
            }
        }
    }

    public void populatePinjamTable() {
        Date tanggal = view.getTanggalDateChooser().getDate();
        String gedung = (String) view.getGedungComboBox().getSelectedItem();
        String lantai = (String) view.getLantaiComboBox().getSelectedItem();
        String ruangan = (String) view.getRuanganComboBox().getSelectedItem();

        pinjamModel.populatePinjamTable(view.getPinjamTable(), tanggal, gedung, lantai, ruangan);
    }

    public void filterTable() {
        populatePinjamTable();
    }

    public void unduhTabelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Object[] options = {"PDF", "CSV"};
        int choice = JOptionPane.showOptionDialog(view,
                "Pilih format unduhan:",
                "Unduh Tabel",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        if (choice == 0) {
            unduhSebagaiPDF();
        } else if (choice == 1) {
            unduhSebagaiCSV();
        }
    }

    public void unduhSebagaiPDF() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".pdf";
            pinjamModel.generatePDF(view.getPinjamTable(), filePath);
        }
    }

    private void unduhSebagaiCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".csv";
            pinjamModel.generateCSV(view.getPinjamTable(), filePath);
        }
    }

    public void tampilButtonActionPerformed(java.awt.event.ActionEvent evt) {
        view.getTanggalDateChooser().setDate(null);
        view.getGedungComboBox().setSelectedIndex(-1);
        view.getLantaiComboBox().setSelectedIndex(-1);
        view.getRuanganComboBox().setSelectedIndex(-1);

        filterTable();
    }

    public void cariButtonActionPerformed(java.awt.event.ActionEvent evt) {
        filterTable();
    }
}
