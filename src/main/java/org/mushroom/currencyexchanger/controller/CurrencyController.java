package org.mushroom.currencyexchanger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mushroom.currencyexchanger.dto.CurrencyDto;
import org.mushroom.currencyexchanger.service.CurrencyService;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/currencies")
public class CurrencyController extends HttpServlet {
    private final CurrencyService currencyService = new CurrencyService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        List<CurrencyDto> result = currencyService.getAllCurrencies();
        objectMapper.writeValue(res.getWriter(), result);
    }
}
