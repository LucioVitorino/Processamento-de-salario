package isptec.pii_pt2.grupo1;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import java.io.File;
import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Report {

    public static void report_collaborator(ArrayList<Collaborator> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("A lista de colaboradores encontra-se vazia!");
            return;
        }

        Document pdf = new Document(PageSize.A4);
        try {
            String caminho = "files/Relatorio_de_Colaboradores.pdf";
            PdfWriter.getInstance(pdf, new FileOutputStream(caminho));
            pdf.open();

            // Metadados
            pdf.addTitle("Relatório de Colaboradores");
            pdf.addAuthor("Departamento RH");
            pdf.addCreationDate();

            // Cabeçalho
            pdf.add(new Paragraph("EMPRESA LJK, Corp.", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            pdf.add(new Paragraph("Departamento de Recursos Humanos"));
            pdf.add(new Paragraph("Data: " + LocalDate.now()));
            pdf.add(Chunk.NEWLINE);
            pdf.add(new Paragraph("Assunto: Relatório de Colaboradores", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            pdf.add(Chunk.NEWLINE);
            pdf.add(new Paragraph("Este relatório apresenta os colaboradores da empresa LJK, Corp."));
            pdf.add(Chunk.NEWLINE);

            // Tabela
            PdfPTable table = new PdfPTable(5); // 5 colunas
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Cabeçalhos da tabela
            addTableHeader(table, "ID", "Nome", "Email", "Função", "Status");

            // Dados
            for (Collaborator c : list) {
                table.addCell(String.valueOf(c.Id));
                table.addCell(String.valueOf(c.name));
                table.addCell(String.valueOf(c.email));
                table.addCell(String.valueOf(c.function.name));
                table.addCell(c.is_active ? "Ativo" : "Inativo");
            }

            pdf.add(table);
            
            int total = list.size();
            long ativos = list.stream().filter(c -> c.is_active).count();
            pdf.add(new Paragraph("Resumo do Quadro de Colaboradores", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            pdf.add(new Paragraph("Total: " + total));
            pdf.add(new Paragraph("Ativos: " + ativos));
            pdf.add(new Paragraph("Inativos: " + (total - ativos)));
            
            pdf.add(Chunk.NEWLINE);
            Paragraph assinatura = new Paragraph("O DIRECTOR \n __________________");
            assinatura.setAlignment(Element.ALIGN_CENTER);
            pdf.add(Chunk.NEWLINE);
            pdf.add(Chunk.NEWLINE);
            pdf.add(assinatura);
            

            System.out.println("PDF gerado com sucesso.");
            if (Desktop.isDesktopSupported()) 
                Desktop.getDesktop().open(pdf);
            System.out.println("Acesse a página files do seu arquivo para a visualização!");
        } catch (DocumentException | IOException e) {
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
        } finally {
            pdf.close();
        }
    }

    private static void addTableHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setPadding(5);
            table.addCell(cell);
        }
    }
}
