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
            System.out.println(isptec.pii_pt2.grupo1.Utils.ANSI_RED + "A lista de colaboradores encontra-se vazia!" + isptec.pii_pt2.grupo1.Utils.ANSI_RESET);
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

            // Cabeçalho visual
            Paragraph titulo = new Paragraph("EMPRESA LJK, Corp.", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLUE));
            titulo.setAlignment(Element.ALIGN_CENTER);
            pdf.add(titulo);
            Paragraph subtitulo = new Paragraph("Departamento de Recursos Humanos", FontFactory.getFont(FontFactory.HELVETICA, 13, Color.DARK_GRAY));
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            pdf.add(subtitulo);
            pdf.add(new Paragraph("Data: " + LocalDate.now()));
            pdf.add(Chunk.NEWLINE);
            Paragraph assunto = new Paragraph("Assunto: Relatório de Colaboradores", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            assunto.setAlignment(Element.ALIGN_CENTER);
            pdf.add(assunto);
            pdf.add(Chunk.NEWLINE);
            pdf.add(new Paragraph("Este relatório apresenta os colaboradores da empresa LJK, Corp."));
            pdf.add(Chunk.NEWLINE);

            // Tabela aprimorada

            PdfPTable table = new PdfPTable(10); // 10 colunas
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Cabeçalhos da tabela
            addTableHeader(table, "ID", "Nome", "Nascimento", "Email", "Função", "Morada", "Início", "Status", "Horas/Faltas", "Salário Mensal");

            // Dados com linhas alternadas e cores para status
            boolean alternate = false;
            for (Collaborator c : list) {
                Color bg = alternate ? new Color(240,240,255) : Color.WHITE;
                table.addCell(makeCell(String.valueOf(c.Id), bg));
                table.addCell(makeCell(String.valueOf(c.name), bg));
                table.addCell(makeCell(c.birthday != null ? c.birthday.toString() : "", bg));
                table.addCell(makeCell(String.valueOf(c.email), bg));
                table.addCell(makeCell(c.function != null ? String.valueOf(c.function.name) : "", bg));
                table.addCell(makeCell(String.valueOf(c.household), bg));
                table.addCell(makeCell(c.start_data != null ? c.start_data.toString() : "", bg));
                // Status colorido
                Color statusColor = c.is_active ? new Color(0,128,0) : Color.RED;
                table.addCell(makeCell(c.is_active ? "Ativo" : "Inativo", statusColor, bg));
                table.addCell(makeCell(c.worked_hours + "/" + c.fouls, bg));
                table.addCell(makeCell(String.format("%.2f", c.net_salary), bg));
                alternate = !alternate;
            }

            pdf.add(table);

            int total = list.size();
            long ativos = list.stream().filter(c -> c.is_active).count();
            pdf.add(new Paragraph("Resumo do Quadro de Colaboradores", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13)));
            pdf.add(new Paragraph("Total: " + total, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            pdf.add(new Paragraph("Ativos: " + ativos, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.GREEN)));
            pdf.add(new Paragraph("Inativos: " + (total - ativos), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.RED)));

            pdf.add(Chunk.NEWLINE);
            Paragraph assinatura = new Paragraph("O DIRECTOR \n __________________");
            assinatura.setAlignment(Element.ALIGN_CENTER);
            pdf.add(Chunk.NEWLINE);
            pdf.add(Chunk.NEWLINE);
            pdf.add(assinatura);

            System.out.println(isptec.pii_pt2.grupo1.Utils.ANSI_GREEN + "PDF gerado com sucesso." + isptec.pii_pt2.grupo1.Utils.ANSI_RESET);
            if (Desktop.isDesktopSupported()) 
                Desktop.getDesktop().open(new File(caminho));
            System.out.println("Acesse a página files do seu arquivo para a visualização!");

        } catch (DocumentException | IOException e) {
            System.err.println(isptec.pii_pt2.grupo1.Utils.ANSI_RED + "Erro ao gerar PDF: " + e.getMessage() + isptec.pii_pt2.grupo1.Utils.ANSI_RESET);
        } finally {
            pdf.close();
        }
    }
    // Cria célula com cor de fundo e opcionalmente cor de texto
    private static PdfPCell makeCell(String text, Color bg) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBackgroundColor(bg);
        cell.setPadding(5);
        return cell;
    }
    private static PdfPCell makeCell(String text, Color fg, Color bg) {
        Phrase phrase = new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, fg));
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBackgroundColor(bg);
        cell.setPadding(5);
        return cell;
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
