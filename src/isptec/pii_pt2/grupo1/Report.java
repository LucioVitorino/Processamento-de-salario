package isptec.pii_pt2.grupo1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author jofre
 * @author lucio
 * @author kialenguluka
 */
public class Report {
  /*  
  public static void print_collaborator(ArrayList<Collaborator> list)
    {

        
        LocalDate now = LocalDate.now();
        if(list.isEmpty())
            System.out.println("A lista de colaboradores encontra-se vazia!");
        
        else{
            try {
                try (Document pdf = new Document()) {
                    PdfWriter.getInstance(pdf, new FileOutputStream("Relatorio_de_Colaboradores.pdf"));
                    pdf.open();
                    pdf.add(new Paragraph("                                       EMPRESA XPTO S.A.  \n\n\n\n\n\n"));
                    pdf.add(new Paragraph("Departamento de Recursos Humanos"));
                    pdf.add(new Paragraph("Referência: " + now));
                    pdf.add(new Paragraph("Data: " + now));
                    pdf.add(new Paragraph(" "));
                    pdf.add(new Paragraph("Assunto: Relatório de Colaboradores"));
                    pdf.add(new Paragraph(" ")); // linha em branco
                    pdf.add(new Paragraph("Este relatório apresenta os colaboradores da empresa por ordem de admissão."));
                    pdf.add(new Paragraph(" "));
                   
                for(int i = 0; i < list.size(); i ++){
                    Collaborator item = list.get(i);
                    String linha = (i + 1) + " -  Nome: " + item.name + " | Email: " + item.email;
                    pdf.add(new Paragraph(linha));
                    
                }
                    pdf.add(new Paragraph("PDF criado com sucesso usando OpenPDF!"));
                    
                }
            System.out.println("PDF gerado!");
        } catch (DocumentException | FileNotFoundException e) {
        }
            
        }
            

    }
*/
}
