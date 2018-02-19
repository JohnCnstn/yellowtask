package com.johncnstn.view;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.johncnstn.report.RunReport;
import com.johncnstn.report.RunReportList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Map;

public class PdfView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"report.pdf\"");

        RunReportList runReportList = (RunReportList) model.get("runReportList");
        document.add(new Paragraph("Generated Report " + LocalDate.now()));

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
        cell.setPhrase(new Phrase("Week", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total Distance", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Average Time", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Average Speed", font));
        table.addCell(cell);

        for(RunReport runReport : runReportList.getRunReportList()){
            table.addCell(runReport.getWeek());
            table.addCell(String.valueOf(runReport.getTotalDistance()));
            table.addCell(runReport.getAvgTime());
            table.addCell(runReport.getAvgSpeed());
        }

        document.add(table);
    }
}
