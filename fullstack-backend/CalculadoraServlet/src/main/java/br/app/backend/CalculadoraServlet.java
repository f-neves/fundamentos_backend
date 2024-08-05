package br.app.backend;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calculadora")
public class CalculadoraServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        String num1 = request.getParameter("num1");
        String num2 = request.getParameter("num2");
        String operacao = request.getParameter("operacao");

        try {
            double numero1 = Double.parseDouble(num1);
            double numero2 = Double.parseDouble(num2);

            String mensagem = "Operação informada é inválida: " + operacao;

            double resultado;

            switch (operacao) {
                case "soma":
                    resultado = numero1 + numero2;
                    mensagem = "A soma desses dois números é: " + resultado;
                    break;

                case "subtracao":
                    resultado = numero1 - numero2;
                    mensagem = "A subtração desses dois números é: " + resultado;
                    break;

                case "divisao":
                    resultado = numero1 / numero2;
                    mensagem = "A divisão desses dois números é: " + resultado;
                    break;

                case "multiplicacao":
                    resultado = numero1 * numero2;
                    mensagem = "A multiplicação desses dois números é: " + resultado;
                    break;

                default:
                    resultado = 0;
                    mensagem = "Operação informada é inválida: " + operacao;
                    break;
            }
            resp.getWriter().println(mensagem);
        } catch (NumberFormatException | NullPointerException e) {
            resp.getWriter().println("Os valores informados não são válidos.");
        }
    }

    private String formatarRetorno(double resultado) {
        return "Resultado da Operação: " + resultado;
    }
}
