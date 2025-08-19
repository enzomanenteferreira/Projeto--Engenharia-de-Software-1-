package es1backendservicos;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet Filter implementation class CorsFilter
 */

// Aplica o filtro a todos os endpoints (/*)
@WebFilter("/*")
public class CorsFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

    public CorsFilter() {
        super();
    }

    @Override
    public void destroy() {
        // Código de limpeza, se necessário
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;

        // Adicione os cabeçalhos CORS adequados
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        // Passa a requisição adiante na cadeia de filtros
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        // Código de inicialização, se necessário
    }
}
