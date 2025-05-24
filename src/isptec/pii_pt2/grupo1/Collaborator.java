package isptec.pii_pt2.grupo1;
//--------------------- GERADOR DE PDF -----------------
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
//--------------------- GERADOR DE PDF -----------------
import static isptec.pii_pt2.grupo1.Address.create_new_address;
import static isptec.pii_pt2.grupo1.Function.select_function;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import static isptec.pii_pt2.grupo1.Utils.add_name;
import static isptec.pii_pt2.grupo1.Utils.input;
import static isptec.pii_pt2.grupo1.Utils.validate_email;
import java.io.FileNotFoundException;

/**
 *
 * @author lucio
 * @author jofre
 */
public class Collaborator {
    int number;
    StringBuilder name = new StringBuilder();
    LocalDate birthday;
    Address household = new Address();
    Function function = new Function();
    StringBuilder email = new StringBuilder();
    LocalDate start_data;
    boolean is_active = true;

    public  void register_collaborator(ArrayList<Collaborator> list)
    {
        Collaborator novo = new Collaborator();
        System.out.println("------------Dados Pessoais-----------");
        System.out.print("Digite o nome: ");
        novo.name.append(add_name());
        novo.birthday = create_birthday();
        StringBuilder email_ = new StringBuilder();
        do{
        System.out.print("Digite o seu email: ");
        email_.append(input.next());
        }while(!validate_email(email_, list));
        
        novo.email.append(email_);
        
        System.out.println();
        novo.function = select_function();
        
        System.out.println();
        novo.household = create_new_address();
        novo.start_data = LocalDate.now();
        list.add(novo);
    }
    
    public static LocalDate create_birthday() 
    {
         DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         String data = new String();
         LocalDate date = LocalDate.now();
         
        try{
            
        System.out.print("Data de nascimento (dd/MM/yyyy) : ");
        data = input.next();
        date = LocalDate.parse(data, formato);
        
        }catch(DateTimeParseException e)
        {
            System.out.print("Erro de fomatação de data !");
        }
        return (date);
    }
    
    public static void disable_collaborator(String nome, ArrayList<Collaborator> list)
    {
        for(Collaborator item : list)
        {
            if(nome.equals(item.name.toString())){
                item.is_active = false;
                return ;
            }
        }
    }
    
    public static void print_collaborator(ArrayList<Collaborator> list)
    {
        int j = 0;
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
                    j++;
                }
            System.out.println("PDF gerado!");
        } catch (DocumentException | FileNotFoundException e) {
        }
        /*
        System.out.println("Nome\t\t\tEmail");
        for(Collaborator item : list){
            System.out.print(item.name);
            System.out.print("\t\t\t");
            System.out.println(item.email);
        } */
        }
            

    }
}   
