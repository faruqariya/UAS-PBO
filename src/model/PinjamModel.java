package model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Faruq
 */
public class PinjamModel {
    private Database database;
    
    public PinjamModel() {
        database = Database.getInstance();
        
    }

    public boolean ajukanPeminjamanRuangan(String peminjam, String kegiatan, String gedung, String lantai, String ruangan, String tanggalString, String jam, String tanggalPengajuan) {
        String query = "INSERT INTO pinjamRuangan (peminjam, kegiatan, gedung, lantai, ruangan, tanggal, jam, tanggalPengajuan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = database.getConnection();
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Connection is closed or invalid.");
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1, peminjam);
            stmt.setString(2, kegiatan);
            stmt.setString(3, gedung);
            stmt.setString(4, lantai);
            stmt.setString(5, ruangan);
            stmt.setString(6, tanggalString);
            stmt.setString(7, jam);
            stmt.setString(8, tanggalPengajuan);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createSuratPeminjamanPDF(String peminjam, String kegiatan, String gedung, String lantai, String ruangan, String tanggal, String jam, String filePath)
            throws FileNotFoundException, DocumentException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Paragraph title = new Paragraph("Surat Peminjaman Ruangan", boldFont);
        Font italicFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ITALIC);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n\n"));

        document.add(new Paragraph("Yth. Kasubbag Tata Usaha dan Rumah Tangga", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        document.add(new Paragraph("          Politeknik Statistika STIS", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        document.add(new Paragraph("          Di tempat\n\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));

        Paragraph isiSurat = new Paragraph();
        isiSurat.add(new Chunk("          Sehubungan akan diadakannya kegiatan ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        isiSurat.add(new Chunk(kegiatan, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        isiSurat.add(new Chunk(", kami (", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        isiSurat.add(new Chunk(peminjam, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        isiSurat.add(new Chunk(") bermaksud mengajukan surat peminjaman ruangan ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        isiSurat.add(new Chunk(ruangan, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        isiSurat.add(new Chunk(" yang akan digunakan pada:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        document.add(isiSurat);
        document.add(new Paragraph("\n"));

        Paragraph tanggalJam = new Paragraph();
        tanggalJam.add(new Chunk("          Tanggal   :   ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        tanggalJam.add(new Chunk(tanggal, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        tanggalJam.add(new Chunk("\n          Waktu     :   Sesi ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        tanggalJam.add(new Chunk(jam, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        tanggalJam.add(new Chunk("\n                              keterangan: Sesi 1: 07.20 - 09.50 WIB, Sesi 2: 10.00 - 12.40 WIB,", italicFont));
        tanggalJam.add(new Chunk("\n                                                  Sesi 3: 13.30 - 16.00 WIB, Sesi 4: 16.00 - 18.00 WIB.", italicFont));
        tanggalJam.add(new Chunk("\n          Tempat   :   ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        tanggalJam.add(new Chunk(ruangan, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        document.add(tanggalJam);
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("          Demikian surat permohonan ini kami sampaikan, atas perhatian dan izin yang Bapak/Ibu berikan kami mengucapkan terima kasih.\n\n\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));

        Paragraph menyetujui = new Paragraph("Menyetujui,               \nKetua UPK               \n\n\nWahyudin, S.Si., MAP., MPP.", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
        menyetujui.setAlignment(Element.ALIGN_RIGHT);
        document.add(menyetujui);

        document.close();
    }
    
    public void populatePinjamTable(JTable pinjamTable, Date tanggal, String gedung, String lantai, String ruangan) {
        DefaultTableModel model = (DefaultTableModel) pinjamTable.getModel();
        model.setRowCount(0);

        String query = "SELECT no, tanggal, gedung, lantai, ruangan, jam, kegiatan, peminjam, tanggalPengajuan FROM pinjamRuangan WHERE 1=1";

        if (tanggal != null) {
            query += " AND tanggal = ?";
        }

        if (gedung != null) {
            query += " AND gedung = ?";
        }

        if (lantai != null) {
            query += " AND lantai = ?";
        }

        if (ruangan != null) {
            query += " AND ruangan = ?";
        }

        try (
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            int paramIndex = 1;

            if (tanggal != null) {
                stmt.setString(paramIndex++, new SimpleDateFormat("yyyy-MM-dd").format(tanggal));
            }
            if (gedung != null) {
                stmt.setString(paramIndex++, gedung);
            }
            if (lantai != null) {
                stmt.setString(paramIndex++, lantai);
            }
            if (ruangan != null) {
                stmt.setString(paramIndex++, ruangan);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int no = rs.getInt("no");
                String tanggalDb = rs.getString("tanggal");
                String gedungDb = rs.getString("gedung");
                String lantaiDb = rs.getString("lantai");
                String ruanganDb = rs.getString("ruangan");
                String jam = rs.getString("jam");
                String kegiatan = rs.getString("kegiatan");
                String peminjam = rs.getString("peminjam");
                String tanggalPengajuan = rs.getString("tanggalPengajuan");

                model.addRow(new Object[]{no, tanggalDb, gedungDb, lantaiDb, ruanganDb, jam, kegiatan, peminjam, tanggalPengajuan});
            }

            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void generatePDF(JTable pinjamTable, String filePath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            PdfPTable table = new PdfPTable(pinjamTable.getColumnCount());
            for (int i = 0; i < pinjamTable.getColumnCount(); i++) {
                table.addCell(new PdfPCell(new Phrase(pinjamTable.getColumnName(i))));
            }
            for (int row = 0; row < pinjamTable.getRowCount(); row++) {
                for (int col = 0; col < pinjamTable.getColumnCount(); col++) {
                    Object cellValue = pinjamTable.getValueAt(row, col);
                    table.addCell(new PdfPCell(new Phrase(cellValue != null ? cellValue.toString() : "")));
                }
            }
            document.add(table);
            document.close();
            JOptionPane.showMessageDialog(null, "Tabel berhasil diunduh sebagai PDF!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void generateCSV(JTable pinjamTable, String filePath) {
        try (FileWriter csvWriter = new FileWriter(filePath)) {
            for (int i = 0; i < pinjamTable.getColumnCount(); i++) {
                csvWriter.write("\"" + pinjamTable.getColumnName(i) + "\"");
                if (i < pinjamTable.getColumnCount() - 1) {
                    csvWriter.write(",");
                }
            }
            csvWriter.write("\n");

            for (int row = 0; row < pinjamTable.getRowCount(); row++) {
                for (int col = 0; col < pinjamTable.getColumnCount(); col++) {
                    Object cellValue = pinjamTable.getValueAt(row, col);
                    String cellText = (cellValue != null) ? cellValue.toString() : "";
                    csvWriter.write("\"" + cellText.replace("\"", "\"\"") + "\"");
                    if (col < pinjamTable.getColumnCount() - 1) {
                        csvWriter.write(",");
                    }
                }
                csvWriter.write("\n");
            }

            JOptionPane.showMessageDialog(null, "Tabel berhasil diunduh sebagai CSV!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}
