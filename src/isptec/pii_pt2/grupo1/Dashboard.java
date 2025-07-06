package isptec.pii_pt2.grupo1;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static isptec.pii_pt2.grupo1.Utils.*;
import static isptec.pii_pt2.grupo1.Function.functions_list;

/**
 * Dashboard com estatísticas e métricas do sistema
 * @author jofre
 */
public class Dashboard {
    
    public static void showDashboard(ArrayList<Collaborator> collaborators) {
        clearScreen();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    DASHBOARD EXECUTIVO                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Estatísticas gerais
        showGeneralStats(collaborators);
        System.out.println();
        
        // Estatísticas por função
        showFunctionStats(collaborators);
        System.out.println();
        
        // Alertas e notificações
        showAlerts(collaborators);
        System.out.println();
        
        // Gráfico ASCII simples
        showSalaryDistribution(collaborators);
        
        pause();
    }
    
    private static void showGeneralStats(ArrayList<Collaborator> collaborators) {
        int total = collaborators.size();
        long ativos = collaborators.stream().filter(c -> c.is_active).count();
        long inativos = total - ativos;
        
        double totalSalarios = collaborators.stream()
            .filter(c -> c.is_active)
            .mapToDouble(c -> c.net_salary)
            .sum();
        
        double mediaSalarial = ativos > 0 ? totalSalarios / ativos : 0;
        
        System.out.println("┌─────────────────── ESTATÍSTICAS GERAIS ───────────────────┐");
        System.out.printf("│ Total de Colaboradores: %s%-10d%s                     │%n", 
                         ANSI_GREEN, total, ANSI_RESET);
        System.out.printf("│ Ativos: %s%-5d%s | Inativos: %s%-5d%s                      │%n", 
                         ANSI_GREEN, ativos, ANSI_RESET, ANSI_RED, inativos, ANSI_RESET);
        System.out.printf("│ Folha Salarial Total: %s%.2f Kzs%s                    │%n", 
                         ANSI_GREEN, totalSalarios, ANSI_RESET);
        System.out.printf("│ Salário Médio: %s%.2f Kzs%s                           │%n", 
                         ANSI_GREEN, mediaSalarial, ANSI_RESET);
        System.out.println("└────────────────────────────────────────────────────────────┘");
    }
    
    private static void showFunctionStats(ArrayList<Collaborator> collaborators) {
        System.out.println("┌─────────────────── POR FUNÇÃO ───────────────────┐");
        
        if (functions_list.isEmpty()) {
            System.out.println("│ Nenhuma função cadastrada                         │");
        } else {
            for (Function f : functions_list) {
                long count = collaborators.stream()
                    .filter(c -> c.function != null && c.function.id == f.id && c.is_active)
                    .count();
                
                System.out.printf("│ %-20s: %s%2d%s colaboradores        │%n", 
                                f.name.toString().substring(0, Math.min(20, f.name.length())), 
                                ANSI_GREEN, count, ANSI_RESET);
            }
        }
        System.out.println("└───────────────────────────────────────────────────┘");
    }
    
    private static void showAlerts(ArrayList<Collaborator> collaborators) {
        System.out.println("┌─────────────────── ALERTAS ───────────────────┐");
        
        // Aniversariantes do mês
        LocalDate hoje = LocalDate.now();
        long aniversariantes = collaborators.stream()
            .filter(c -> c.birthday != null && 
                        c.birthday.getMonth() == hoje.getMonth() && 
                        c.is_active)
            .count();
        
        // Colaboradores com muitas faltas
        long muitasFaltas = collaborators.stream()
            .filter(c -> c.fouls > 5 && c.is_active)
            .count();
        
        if (aniversariantes > 0) {
            System.out.printf("│ [ANIV] %d aniversariante(s) este mês        │%n", aniversariantes);
        }
        
        if (muitasFaltas > 0) {
            System.out.printf("│ [ALERT] %d colaborador(es) com +5 faltas    │%n", muitasFaltas);
        }
        
        if (aniversariantes == 0 && muitasFaltas == 0) {
            System.out.println("│ [OK] Nenhum alerta no momento              │");
        }
        
        System.out.println("└───────────────────────────────────────────────┘");
    }
    
    private static void showSalaryDistribution(ArrayList<Collaborator> collaborators) {
        System.out.println("┌─────────────── DISTRIBUIÇÃO SALARIAL ───────────────┐");
        
        // Faixas salariais
        long baixo = collaborators.stream().filter(c -> c.net_salary < 50000 && c.is_active).count();
        long medio = collaborators.stream().filter(c -> c.net_salary >= 50000 && c.net_salary < 100000 && c.is_active).count();
        long alto = collaborators.stream().filter(c -> c.net_salary >= 100000 && c.is_active).count();
        
        long total = baixo + medio + alto;
        
        if (total > 0) {
            System.out.println("│ < 50K Kzs  │ 50K-100K │ > 100K Kzs              │");
            System.out.println("├────────────┼──────────┼─────────────────────────┤");
            
            // Gráfico ASCII simples
            String graficoBaixo = createBar((int)(baixo * 20 / total));
            String graficoMedio = createBar((int)(medio * 20 / total));
            String graficoAlto = createBar((int)(alto * 20 / total));
            
            System.out.printf("│ %-10s │ %-8s │ %-23s │%n", graficoBaixo, graficoMedio, graficoAlto);
            System.out.printf("│ %s%2d%s pessoas │ %s%2d%s pessoas│ %s%2d%s pessoas              │%n", 
                            ANSI_RED, baixo, ANSI_RESET,
                            ANSI_GREEN, medio, ANSI_RESET,
                            ANSI_GREEN, alto, ANSI_RESET);
        } else {
            System.out.println("│ Nenhum colaborador ativo para análise            │");
        }
        
        System.out.println("└───────────────────────────────────────────────────┘");
    }
    
    private static String createBar(int length) {
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < length; i++) {
            bar.append("█");
        }
        return bar.toString();
    }
}