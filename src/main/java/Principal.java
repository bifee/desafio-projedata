import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = criarListaInicial();

        removerFuncionario(funcionarios, "João");

        imprimirFuncionarios(funcionarios);

        BigDecimal percentual = new BigDecimal("10");
        funcionarios.forEach(f -> aplicarAumento(f, percentual));

        Map<String, List<Funcionario>> funcionariosPorFuncao = organizarPorFuncao(funcionarios);

        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        imprimirFuncionariosPorMesDeNascimento(funcionarios, List.of(10, 12));

        imprimirFuncionarioMaisVelho(funcionarios);

        imprimirFuncionariosOrdemAlfabetica(funcionarios);

        imprimirTotalSalarios(funcionarios);

        System.out.println("\nQuantidade de salários mínimos que cada funcionário recebe:");
        funcionarios.forEach(Funcionario::imprimirQuantidadeSalariosMinimos);
    }

    private static List<Funcionario> criarListaInicial() {
        return new ArrayList<>(List.of(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));
    }

    public static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        System.out.println("\nLISTA DE FUNCIONÁRIOS:");
        System.out.printf("%-10s | %-12s | %-15s | %-15s%n",
                "NOME", "DATA NASC.", "SALÁRIO", "FUNÇÃO");
        System.out.println("------------------------------------------------------------------");

        funcionarios.forEach(System.out::println);
    }

    public static void aplicarAumento(Funcionario funcionario, BigDecimal percentual) {
        BigDecimal fator = BigDecimal.ONE.add(
                percentual.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP)
        );
        BigDecimal novoSalario = funcionario.getSalario()
                .multiply(fator)
                .setScale(2, RoundingMode.HALF_UP);
        funcionario.setSalario(novoSalario);
    }

    public static void removerFuncionario(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(f -> f.getNome().equals(nome));
    }

    public static Map<String, List<Funcionario>> organizarPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        System.out.println("\nFuncionários organizados por função:");
        funcionariosPorFuncao.forEach((funcao, listaPorFuncao) -> {
            System.out.println("\nFunção: " + funcao);
            listaPorFuncao.forEach(f -> System.out.println(" - " + f));
        });
    }

    public static void imprimirFuncionariosPorMesDeNascimento(List<Funcionario> funcionarios, List<Integer> meses) {
        System.out.println("\nFuncionários com mês de nascimento em " + meses + ":");
        funcionarios.forEach(f -> {
            int mesNascimento = f.getDataNascimento().getMonthValue();
            String dataFormatada = Funcionario.formatarData(f.getDataNascimento());
            if (meses.contains(mesNascimento)) {
                System.out.println(f.getNome() + " - " + dataFormatada);
            }
        });
    }

    public static Funcionario buscarFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        return funcionarios.stream().min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElseThrow(() -> new RuntimeException("Lista de funcionários vazia"));
    }

    public static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        Funcionario funcionarioMaisVelho = buscarFuncionarioMaisVelho(funcionarios);
        int idade = Period.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("\nFuncionário mais velho:\n" + funcionarioMaisVelho.getNome() + " - " + idade + " anos");
    }

    public static void imprimirFuncionariosOrdemAlfabetica(List<Funcionario> funcionarios) {
        System.out.println("\nFuncionários em ordem alfabética:");
        System.out.printf("%-10s | %-12s | %-15s | %-15s%n",
                "NOME", "DATA NASC.", "SALÁRIO", "FUNÇÃO");
        System.out.println("------------------------------------------------------------------");
        List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        funcionariosOrdenados.sort(Comparator.comparing(Funcionario::getNome));
        funcionariosOrdenados.forEach(System.out::println);
    }

    public static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String totalSalariosFormatado = Funcionario.bigDecimalToString(totalSalarios);
        System.out.println("\nSoma Total dos salários: R$ " + totalSalariosFormatado);
    }
}
