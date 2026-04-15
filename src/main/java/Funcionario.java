import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate data_nascimento, BigDecimal salario, String funcao) {
        super(nome, data_nascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }


    public String toString() {
        String dataFormatada = formatarData(this.getDataNascimento());
        String salarioFormatado = "R$ " + bigDecimalToString(this.getSalario());
        return String.format("%-10s | %-12s | %-15s | %-15s",
                this.getNome(), dataFormatada, salarioFormatado, this.getFuncao());
    }

    public BigDecimal getQuantidadeSalariosMinimos(){
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        return this.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
    }

    public void imprimirQuantidadeSalariosMinimos(){
        BigDecimal quantidade = getQuantidadeSalariosMinimos();
        String quantidadeFormatada = bigDecimalToString(quantidade);
        System.out.println(this.getNome() + " recebe " + quantidadeFormatada + " salários mínimos.");
    }

    public static String bigDecimalToString(BigDecimal salario) {
        Locale ptBr = Locale.of("pt", "BR");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(ptBr);
        symbols.setGroupingSeparator('.');
        symbols.setMonetaryDecimalSeparator(',');

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        return df.format(salario);
    }

    public static String formatarData(LocalDate data) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatador);
    }
}
