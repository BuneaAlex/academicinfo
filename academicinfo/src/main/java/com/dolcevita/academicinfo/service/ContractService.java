package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.StudentDto;
import com.dolcevita.academicinfo.dto.SubjectDto;
import com.dolcevita.academicinfo.exceptions.NotConfirmedException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final StudentsSubjectService studentsSubjectService;
    private final AuthenticationService authenticationService;

    public byte[] generatePdfContract(String jwt, int semester) throws NotConfirmedException, DocumentException {
        Set<SubjectDto> subjects = studentsSubjectService.getSubjectsForAStudent(jwt)
                .stream()
                .filter(subjectDto -> subjectDto.semester() == semester)
                .collect(java.util.stream.Collectors.toSet());
        Optional<StudentDto> optionalStudentDto = authenticationService.confirmStudentByToken(jwt);
        if (optionalStudentDto.isEmpty()) {
            throw new NotConfirmedException("Identity could not be confirmed!");
        }
        StudentDto studentDto = optionalStudentDto.get();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        String title = "Contract " + studentDto.getFirstName() + " " + studentDto.getSurname() + " Semestrul " + semester;
        document.addSubject(title);

        Paragraph titleParagraph = new Paragraph();
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        Font font = FontFactory.getFont(FontFactory.COURIER, 18, Font.BOLD, BaseColor.BLUE);
        Chunk chunk = new Chunk(title, font);
        titleParagraph.add(chunk);
        document.add(titleParagraph);

        document.add(Chunk.NEWLINE);

        Paragraph descriptionParagraph = new Paragraph();
        descriptionParagraph.setAlignment(Element.ALIGN_CENTER);
        Font font2 = FontFactory.getFont(FontFactory.COURIER, 12, Font.NORMAL, BaseColor.BLACK);
        Chunk chunk2 = new Chunk("Contractul studentului " + studentDto.getFirstName() + " " + studentDto.getSurname() +
                ", inmatriculat in anul " + studentDto.getYearOfStudy() + ", la specializarea " + studentDto.getSpecialization() +
                ", limba de studiu " + studentDto.getLanguage() + ", forma de finantare " + studentDto.getFunding() +
                ", in grupa " + studentDto.getGroupNumber() +
                ", pentru semestrul " + semester + ": ", font2);
        descriptionParagraph.add(chunk2);
        document.add(descriptionParagraph);

        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        table.addCell("Name");
        table.addCell("Credits");
        table.addCell("Subject type");
        table.addCell("Faculty");

        int totalCredits = 0;

        for (SubjectDto subject : subjects) {
            table.addCell(subject.name());
            table.addCell(String.valueOf(subject.credits()));
            table.addCell(subject.subjectType().toString());
            table.addCell(subject.faculty().toString());
            totalCredits += subject.credits();
        }

        document.add(table);

        document.add(Chunk.NEWLINE);

        Paragraph totalCreditsParagraph = new Paragraph();
        totalCreditsParagraph.setAlignment(Element.ALIGN_CENTER);
        Font font3 = FontFactory.getFont(FontFactory.COURIER, 12, Font.NORMAL, BaseColor.BLACK);
        Chunk chunk3 = new Chunk("Total credits: " + totalCredits, font3);
        totalCreditsParagraph.add(chunk3);
        document.add(totalCreditsParagraph);

        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
