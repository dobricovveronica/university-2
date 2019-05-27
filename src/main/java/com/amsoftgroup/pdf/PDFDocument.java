package com.amsoftgroup.pdf;

import com.amsoftgroup.model.Average;
import com.amsoftgroup.model.Discipline;
import com.amsoftgroup.model.Phone;
import com.amsoftgroup.model.Student;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import javafx.scene.transform.Rotate;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.RuleBasedCollator;
import java.util.*;
import java.util.List;

import static java.awt.Color.BLUE;
import static java.awt.Color.LIGHT_GRAY;

public class PDFDocument extends AbstractPdfView {

    @Override protected void buildPdfDocument(
            Map<String, Object> model,
            Document document,
            PdfWriter writer,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Font titleFont = FontFactory.getFont("Algerian", 20);
        Font subtitleFont = FontFactory.getFont("Times Roman", 10);

//        Rectangle rectangle = PageSize.getRectangle("LETTER");
//        rectangle.rotate();
//        document.setPageSize(rectangle);
//        document.setPageSize(PageSize.A4.rotate());

        HeaderFooter header = new HeaderFooter(new Phrase("List of students"), false);
        HeaderFooter footer = new HeaderFooter(new Phrase(String.format("Page  ", writer.getPageNumber())), new Phrase(" " ));

        document.setHeader(header);
        document.setFooter(footer);
        document.addTitle("List of students");

        Paragraph title = new Paragraph("Generate PDF report\n \n  List of students ", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);

        for (int i = 0; i < 10; i++) {
            document.add(new Paragraph("  ", titleFont));
        }
        document.add(title);
        document.newPage();

        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setTotalWidth(new float[]{2, 2, 3, 1, 3, 3, 3, 2, 3});

        PdfPCell header0 = new PdfPCell(new Phrase("Image"));
        PdfPCell header1 = new PdfPCell(new Phrase("Name"));
        PdfPCell header2 = new PdfPCell(new Phrase("Birth Day"));
        PdfPCell header3 = new PdfPCell(new Phrase("G"));
        PdfPCell header4 = new PdfPCell(new Phrase("Address"));
        PdfPCell header5 = new PdfPCell(new Phrase("Mail"));
        PdfPCell header6 = new PdfPCell(new Phrase("Phone"));
        PdfPCell header7 = new PdfPCell(new Phrase("Group"));
        PdfPCell header8 = new PdfPCell(new Phrase("Discipline"));
        header0.setHorizontalAlignment(Element.ALIGN_CENTER);
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        header3.setHorizontalAlignment(Element.ALIGN_CENTER);
        header4.setHorizontalAlignment(Element.ALIGN_CENTER);
        header5.setHorizontalAlignment(Element.ALIGN_CENTER);
        header6.setHorizontalAlignment(Element.ALIGN_CENTER);
        header7.setHorizontalAlignment(Element.ALIGN_CENTER);
        header8.setHorizontalAlignment(Element.ALIGN_CENTER);
        header0.setBackgroundColor(LIGHT_GRAY);
        header1.setBackgroundColor(LIGHT_GRAY);
        header2.setBackgroundColor(LIGHT_GRAY);
        header3.setBackgroundColor(LIGHT_GRAY);
        header4.setBackgroundColor(LIGHT_GRAY);
        header5.setBackgroundColor(LIGHT_GRAY);
        header6.setBackgroundColor(LIGHT_GRAY);
        header7.setBackgroundColor(LIGHT_GRAY);
        header8.setBackgroundColor(LIGHT_GRAY);

        table.addCell(header0);
        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
        table.addCell(header5);
        table.addCell(header6);
        table.addCell(header7);
        table.addCell(header8);

        //Get data from model
        List<Student> students = (List<Student>) model.get("modelObject");
        for (Student student : students) {

            Image img = Image.getInstance(student.getPicture());
            PdfPCell cellStImage = new PdfPCell();
            cellStImage.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellStImage.addElement(img);
            table.addCell(cellStImage);

            Paragraph stName = new Paragraph(student.getFirstName()+ " " +student.getLastName(), subtitleFont);
            PdfPCell cellStName = new PdfPCell();
            cellStName.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellStName.addElement(stName);
            table.addCell(cellStName);

            Paragraph stBirthDay = new Paragraph(student.getDateOfBirth().toString(), subtitleFont);
            PdfPCell cellStBirthDay = new PdfPCell();
            cellStBirthDay.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellStBirthDay.addElement(stBirthDay);
            table.addCell(cellStBirthDay);

            Paragraph stGender = new Paragraph(student.getGender().toString(), subtitleFont);
            PdfPCell cellStGender = new PdfPCell();

            cellStGender.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellStGender.addElement(stGender);
            table.addCell(cellStGender);

            Paragraph stAddress = new Paragraph(student.getAddress().getCountry()+", \n"+ student.getAddress().getCity()+", "+student.getAddress().getAddress(), subtitleFont);
            PdfPCell cellStAddress = new PdfPCell();
            cellStAddress.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellStAddress.addElement(stAddress);
            table.addCell(cellStAddress);

            Paragraph stMail = new Paragraph(student.getMail(), subtitleFont);
            PdfPCell cellStMail = new PdfPCell();
            cellStMail.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellStMail.addElement(stMail);
            table.addCell(cellStMail);

            Set<Phone> stPhones = new HashSet<>(student.getPhones());
            String phonesOfStudent = "";
            for (Phone phone : stPhones){
                phonesOfStudent += phone.getPhoneType().getName() + ": " + phone.getValue() + "\n";
            }
            Paragraph stPhonesList = new Paragraph(phonesOfStudent, subtitleFont);
            PdfPCell cellStPhonesList = new PdfPCell();
            cellStPhonesList.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellStPhonesList.addElement(stPhonesList);
            table.addCell(cellStPhonesList );

            Paragraph stGroup = new Paragraph(student.getGroup().getName(), subtitleFont);
            PdfPCell cellStGroup = new PdfPCell();
            cellStGroup.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellStGroup.addElement(stGroup);
            table.addCell(cellStGroup);

            Set<Average> stDisciplinesAverages = new HashSet<>(student.getAverages());
            String disciplinesOfStudent = "";
            for (Average average : stDisciplinesAverages){
                disciplinesOfStudent += average.getDiscipline().getTitle() + ": " + average.getValue() + "\n";
            }
            Paragraph stDisciplines = new Paragraph(disciplinesOfStudent, subtitleFont);
            PdfPCell cellStDisciplines = new PdfPCell();
            cellStDisciplines.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellStDisciplines.addElement(stDisciplines);
            table.addCell(cellStDisciplines);
        }


        document.add(table);

    }
}
