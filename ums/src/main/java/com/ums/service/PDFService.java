package com.ums.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ums.entity.Booking;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class PDFService {

    private static final Font tableHeaderFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font tableBodyFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

    public String generateBookingDetailsPdf(Booking booking) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("D://airbnb-booking//booking-confirmation" + booking.getId() + ".pdf"));

            document.open();

            // Add title
            Paragraph title = new Paragraph("Booking Details", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add empty line
            document.add(new Paragraph(" "));

            // Create table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Add table headers
            addTableHeader(table, "AirBNB", "Guest_Details");

            // Add booking details to the table
            addRow(table, "Guest Name", booking.getGuestName());
            addRow(table, "Total Nights", String.valueOf(booking.getTotalNights()));
            addRow(table, "Total Price", String.valueOf(booking.getTotalPrice()));
            addRow(table, "Booking Date", formatDate(booking.getBookingDate()));
            addRow(table, "Check-in Time", formatCheckInTime(booking.getCheckInTime()));




            // Add table to document
            document.add(table);

            Paragraph title1 = new Paragraph("\nThis is an automated confirmation generated from our online booking platform. " +
                    "Please contact us if you have any questions or concerns.\n Please Connect our HelpLine Number/Email \n" +
                    "+919131304154 Or sk15885628@gmail.com", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
         return "D://airbnb-booking//booking-confirmation" + booking.getId() + ".pdf";
    }

    private void addTableHeader(PdfPTable table, String header1, String header2) {
        PdfPCell cell1 = new PdfPCell(new Phrase(header1, tableHeaderFont));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase(header2, tableHeaderFont));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell2);
    }

    private void addRow(PdfPTable table, String attribute, String value) {
        PdfPCell cell1 = new PdfPCell(new Phrase(attribute, tableBodyFont));
        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase(value, tableBodyFont));
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell2);
    }

    private String formatDate(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private String formatCheckInTime(int checkInTime) {
        // Format check-in time as needed (e.g., convert from 24-hour format to AM/PM format)
        return String.valueOf(checkInTime);
    }
}
