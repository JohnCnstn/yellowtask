package com.johncnstn.view;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.johncnstn.data.entity.User;
import com.johncnstn.report.CurrencyRate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PdfView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"all-users.pdf\"");

        List<CurrencyRate> currencyRateList = (List<CurrencyRate>) model.get("todayCurrencyRates");
        document.add(new Paragraph("Generated Users " + LocalDate.now()));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100.0f);
        table.setSpacingBefore(10);

        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(BaseColor.WHITE);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Username", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("First Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);

        for(CurrencyRate currencyRate : currencyRateList){
            table.addCell(currencyRate.getWeek());
            table.addCell(String.valueOf(currencyRate.getTotalDistance()));
            table.addCell(currencyRate.getAvgTime());
            table.addCell(currencyRate.getAvgSpeed());
        }

        document.add(table);
    }
}
