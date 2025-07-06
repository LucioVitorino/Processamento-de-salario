
package isptec.pii_pt2.grupo1;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Chunk;
import com.lowagie.text.Phrase;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;

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

        Document pdf = new Document(PageSize.A4, 40, 40, 50, 50); // Margens elegantes
        try {
            String caminho = "files/Relatorio_de_Colaboradores.pdf";
            PdfWriter.getInstance(pdf, new FileOutputStream(caminho));
            pdf.open();

            // Metadados aprimorados
            pdf.addTitle("Relatório Executivo de Colaboradores - LJK Corp");
            pdf.addAuthor("Departamento de Recursos Humanos");
            pdf.addSubject("Relatório Mensal de Colaboradores");
            pdf.addKeywords("RH, Colaboradores, Relatório, LJK");
            pdf.addCreationDate();

            // Cabeçalho elegante com gradiente visual
            createHeader(pdf);
            
            // Linha decorativa
            pdf.add(createSeparatorLine());
            pdf.add(Chunk.NEWLINE);


            // Estatísticas resumidas antes da tabela
            createSummarySection(pdf, list);
            pdf.add(Chunk.NEWLINE);
            
            // Tabela principal otimizada (8 colunas)
            Font tableFont = FontFactory.getFont(FontFactory.HELVETICA, 10f);
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setSpacingBefore(15f);
            table.setSpacingAfter(15f);
            
            // Definir larguras das colunas para melhor layout
            float[] columnWidths = {8f, 20f, 12f, 15f, 15f, 10f, 8f, 12f};
            table.setWidths(columnWidths);

            // Cabeçalho elegante da tabela
            addElegantTableHeader(table, "ID", "Nome", "Nascimento", "Função", "Email", "Início", "Status", "Salário");

            // Dados com design aprimorado
            int rowCount = 0;
            for (Collaborator c : list) {
                Color bg = (rowCount % 2 == 0) ? new Color(248, 249, 250) : Color.WHITE;
                
                table.addCell(createStyledCell(c.Id, bg, tableFont, Element.ALIGN_CENTER));
                table.addCell(createStyledCell(truncateText(c.name.toString(), 18), bg, tableFont, Element.ALIGN_LEFT));
                table.addCell(createStyledCell(formatDate(c.birthday), bg, tableFont, Element.ALIGN_CENTER));
                table.addCell(createStyledCell(c.function != null ? truncateText(c.function.name.toString(), 12) : "N/A", bg, tableFont, Element.ALIGN_LEFT));
                table.addCell(createStyledCell(truncateText(c.email.toString(), 15), bg, tableFont, Element.ALIGN_LEFT));
                table.addCell(createStyledCell(formatDate(c.start_data), bg, tableFont, Element.ALIGN_CENTER));
                
                // Status com cor especial
                Color statusBg = c.is_active ? new Color(220, 255, 220) : new Color(255, 220, 220);
                Color statusText = c.is_active ? new Color(0, 100, 0) : new Color(150, 0, 0);
                table.addCell(createStatusCell(c.is_active ? "Ativo" : "Inativo", statusBg, statusText));
                
                table.addCell(createStyledCell(String.format("%.0f Kzs", c.net_salary), bg, tableFont, Element.ALIGN_RIGHT));
                rowCount++;
            }

            pdf.add(table);
            
            // Seção de análise detalhada
            createAnalysisSection(pdf, list);
            
            // Rodapé elegante
            createFooter(pdf);

            System.out.println(isptec.pii_pt2.grupo1.Utils.ANSI_GREEN + "✓ Relatório PDF gerado com sucesso!" + isptec.pii_pt2.grupo1.Utils.ANSI_RESET);
            System.out.println("📄 Arquivo salvo em: " + caminho);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(caminho));
                System.out.println("🚀 Abrindo relatório automaticamente...");
            } else {
                System.out.println("📁 Acesse a pasta 'files' para visualizar o relatório.");
            }

        } catch (DocumentException | IOException e) {
            System.err.println(isptec.pii_pt2.grupo1.Utils.ANSI_RED + "Erro ao gerar PDF: " + e.getMessage() + isptec.pii_pt2.grupo1.Utils.ANSI_RESET);
        } finally {
            pdf.close();
        }
    }
    // Métodos auxiliares para design elegante
    private static void createHeader(Document pdf) throws DocumentException {
        // Título principal
        Paragraph titulo = new Paragraph("EMPRESA LJK, Corp.", 
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, new Color(41, 128, 185)));
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(5f);
        pdf.add(titulo);
        
        // Subtítulo
        Paragraph subtitulo = new Paragraph("Departamento de Recursos Humanos", 
            FontFactory.getFont(FontFactory.HELVETICA, 14, new Color(127, 140, 141)));
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setSpacingAfter(10f);
        pdf.add(subtitulo);
        
        // Data e assunto
        Paragraph dataAssunto = new Paragraph(
            "Relatório de Colaboradores - " + formatCurrentDate(),
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new Color(52, 73, 94)));
        dataAssunto.setAlignment(Element.ALIGN_CENTER);
        dataAssunto.setSpacingAfter(15f);
        pdf.add(dataAssunto);
    }
    
    private static Paragraph createSeparatorLine() {
        Paragraph line = new Paragraph("_".repeat(80), 
            FontFactory.getFont(FontFactory.HELVETICA, 8, new Color(189, 195, 199)));
        line.setAlignment(Element.ALIGN_CENTER);
        return line;
    }
    
    private static void createSummarySection(Document pdf, ArrayList<Collaborator> list) throws DocumentException {
        int total = list.size();
        long ativos = list.stream().filter(c -> c.is_active).count();
        double totalSalarios = list.stream().filter(c -> c.is_active).mapToDouble(c -> c.net_salary).sum();
        
        // Tabela de resumo
        PdfPTable summaryTable = new PdfPTable(4);
        summaryTable.setWidthPercentage(80);
        summaryTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        summaryTable.setSpacingBefore(10f);
        
        // Cabeçalho do resumo
        Font summaryHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE);
        PdfPCell headerCell = new PdfPCell(new Phrase("RESUMO EXECUTIVO", summaryHeaderFont));
        headerCell.setColspan(4);
        headerCell.setBackgroundColor(new Color(52, 73, 94));
        headerCell.setPadding(8f);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        summaryTable.addCell(headerCell);
        
        // Dados do resumo
        Font summaryFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        summaryTable.addCell(createSummaryCell("Total", String.valueOf(total), new Color(52, 152, 219)));
        summaryTable.addCell(createSummaryCell("Ativos", String.valueOf(ativos), new Color(46, 204, 113)));
        summaryTable.addCell(createSummaryCell("Inativos", String.valueOf(total - ativos), new Color(231, 76, 60)));
        summaryTable.addCell(createSummaryCell("Folha Salarial", String.format("%.0f Kzs", totalSalarios), new Color(155, 89, 182)));
        
        pdf.add(summaryTable);
    }
    
    private static PdfPCell createSummaryCell(String label, String value, Color color) {
        Paragraph content = new Paragraph();
        content.add(new Phrase(label + "\n", FontFactory.getFont(FontFactory.HELVETICA, 9, Color.GRAY)));
        content.add(new Phrase(value, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, color)));
        
        PdfPCell cell = new PdfPCell(content);
        cell.setPadding(8f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new Color(248, 249, 250));
        cell.setBorderColor(new Color(220, 221, 225));
        return cell;
    }
    
    private static void addElegantTableHeader(PdfPTable table, String... headers) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE);
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(new Color(52, 73, 94));
            cell.setPadding(8f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(new Color(44, 62, 80));
            cell.setBorderWidth(1f);
            table.addCell(cell);
        }
    }
    
    private static PdfPCell createStyledCell(String text, Color bg, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bg);
        cell.setPadding(6f);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(new Color(220, 221, 225));
        cell.setBorderWidth(0.5f);
        return cell;
    }
    
    private static PdfPCell createStatusCell(String status, Color bg, Color textColor) {
        Font statusFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, textColor);
        PdfPCell cell = new PdfPCell(new Phrase(status, statusFont));
        cell.setBackgroundColor(bg);
        cell.setPadding(6f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderColor(new Color(220, 221, 225));
        cell.setBorderWidth(0.5f);
        return cell;
    }
    
    private static void createAnalysisSection(Document pdf, ArrayList<Collaborator> list) throws DocumentException {
        pdf.add(Chunk.NEWLINE);
        
        // Título da análise
        Paragraph analysisTitle = new Paragraph("ANÁLISE DETALHADA", 
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(52, 73, 94)));
        analysisTitle.setAlignment(Element.ALIGN_CENTER);
        analysisTitle.setSpacingAfter(10f);
        pdf.add(analysisTitle);
        
        // Análise por função
        if (!isptec.pii_pt2.grupo1.Function.functions_list.isEmpty()) {
            pdf.add(new Paragraph("Distribuição por Função:", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(52, 73, 94))));
            
            for (isptec.pii_pt2.grupo1.Function f : isptec.pii_pt2.grupo1.Function.functions_list) {
                long count = list.stream()
                    .filter(c -> c.function != null && c.function.id == f.id && c.is_active)
                    .count();
                if (count > 0) {
                    pdf.add(new Paragraph("• " + f.name + ": " + count + " colaborador(es)", 
                        FontFactory.getFont(FontFactory.HELVETICA, 11)));
                }
            }
        }
    }
    
    private static void createFooter(Document pdf) throws DocumentException {
        pdf.add(Chunk.NEWLINE);
        pdf.add(Chunk.NEWLINE);
        
        // Linha decorativa
        pdf.add(createSeparatorLine());
        pdf.add(Chunk.NEWLINE);
        
        // Assinatura elegante
        Paragraph assinatura = new Paragraph(
            "APROVADO POR:\n\n" +
            "_________________________________\n" +
            "Director de Recursos Humanos\n" +
            "LJK, Corp.",
            FontFactory.getFont(FontFactory.HELVETICA, 11, new Color(127, 140, 141)));
        assinatura.setAlignment(Element.ALIGN_CENTER);
        pdf.add(assinatura);
        
        pdf.add(Chunk.NEWLINE);
        
        // Rodapé com data de geração
        Paragraph rodape = new Paragraph(
            "Documento gerado automaticamente em " + formatCurrentDate(),
            FontFactory.getFont(FontFactory.HELVETICA, 8, Color.GRAY));
        rodape.setAlignment(Element.ALIGN_CENTER);
        pdf.add(rodape);
    }
    
    // Métodos utilitários
    private static String formatDate(LocalDate date) {
        if (date == null) return "N/A";
        return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
    }
    
    private static String formatCurrentDate() {
        LocalDate now = LocalDate.now();
        return now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear();
    }
    
    private static String truncateText(String text, int maxLength) {
        if (text == null) return "N/A";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}
