package com.example.iac.filter;

import com.example.iac.service.IacService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that validates incoming JSON for POST/PUT to /api/iacs and computes IAC when possible.
 * The computed IAC is attached as a request attribute 'computedIac' so controllers/services can use it.
 */
@Component
public class IacFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.startsWith("/api/iacs") || (!"POST".equalsIgnoreCase(request.getMethod()) && !"PUT".equalsIgnoreCase(request.getMethod()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Read body safely
        var wrapped = new org.springframework.web.util.ContentCachingRequestWrapper(request);
        String body = new String(wrapped.getInputStream().readAllBytes(), request.getCharacterEncoding() == null ? "UTF-8" : request.getCharacterEncoding());

        if (body == null || body.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is required for this endpoint");
            return;
        }

        try {
            JsonNode node = mapper.readTree(body);
            JsonNode hNode = node.get("heightMeters");
            JsonNode hipNode = node.get("hipCm");

            if (hNode == null || hipNode == null || hNode.isNull() || hipNode.isNull()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "heightMeters and hipCm are required");
                return;
            }
            double height = hNode.asDouble();
            double hip = hipNode.asDouble();

            if (height <= 0 || hip <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "heightMeters and hipCm must be positive numbers");
                return;
            }

            double computed = IacService.calculateIac(height, hip);
            // Attach computed IAC for controller to use
            wrapped.setAttribute("computedIac", computed);

            // Proceed with wrapped request so downstream can read body as usual
            filterChain.doFilter(wrapped, response);

        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON: " + ex.getMessage());
        }
    }
}
