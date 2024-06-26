package controller;

import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.PinjamModel;
import view.Pinjam;

/**
 *
 * @author Faruq
 */
public class PinjamController {
    private Pinjam view;
    private PinjamModel model;

    public PinjamController(Pinjam view) {
        this.view = view;
        this.model = new PinjamModel();
    }
    
    public void processPeminjaman(String peminjam, String kegiatan, String gedung, String lantai, String ruangan, String tanggalString, String jam) {
        String tanggalPengajuan = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        view.getUnduhButton().setEnabled(true);
        view.getUnduhButton().setActionCommand(peminjam + ";" + kegiatan + ";" + gedung + ";" + lantai + ";" + ruangan + ";" + tanggalString + ";" + jam);

        if (model.ajukanPeminjamanRuangan(peminjam, kegiatan, gedung, lantai, ruangan, tanggalString, jam, tanggalPengajuan)) {
            JOptionPane.showMessageDialog(view, "Pinjam berhasil! Silakan unduh fail surat permohonan peminjaman ruangan anda.");
        } else {
            JOptionPane.showMessageDialog(view, "Pinjam gagal! Silakan coba lagi.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void unduhSuratPeminjaman() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int result = fileChooser.showSaveDialog(view);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            String[] data = view.getUnduhButton().getActionCommand().split(";");

            String peminjam = data[0];
            String kegiatan = data[1];
            String gedung = data[2];
            String lantai = data[3];
            String ruangan = data[4];
            String tanggalString = data[5];
            String jam = data[6];

            try {
                model.createSuratPeminjamanPDF(peminjam, kegiatan, gedung, lantai, ruangan, tanggalString, jam, filePath);
                JOptionPane.showMessageDialog(view, "Surat Peminjaman Ruangan berhasil dibuat.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException | DocumentException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Terjadi kesalahan saat membuat surat PDF.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void updateLantaiOptions() {
        view.getLantaiComboBox().removeAllItems();
        view.getRuanganComboBox().removeAllItems();

        String selectedGedung = (String) view.getGedungComboBox().getSelectedItem();
        if (selectedGedung != null) {
            switch (selectedGedung) {
                case "2":
                    view.getLantaiComboBox().addItem("1");
                    view.getLantaiComboBox().addItem("5");
                    view.getLantaiComboBox().addItem("6");
                    break;
                case "3":
                    view.getLantaiComboBox().addItem("2");
                    view.getLantaiComboBox().addItem("3");
                    view.getLantaiComboBox().addItem("4");
                    break;
            }
        }
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
}
