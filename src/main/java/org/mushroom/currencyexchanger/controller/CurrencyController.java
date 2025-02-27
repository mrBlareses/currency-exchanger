package org.mushroom.currencyexchanger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mushroom.currencyexchanger.dto.CurrencyDto;
import org.mushroom.currencyexchanger.dto.NewCurrencyPayload;
import org.mushroom.currencyexchanger.exception.SqlQueryException;
import org.mushroom.currencyexchanger.service.CurrencyService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/currencies")
public class CurrencyController extends HttpServlet {
    private static final String CONTENT_TYPE_JSON = "application/json";
    private final CurrencyService currencyService = new CurrencyService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType(CONTENT_TYPE_JSON);
        res.setCharacterEncoding("UTF-8");

        List<CurrencyDto> result;
        try {
            result = currencyService.getAllCurrencies();
        } catch (SqlQueryException e) {
            try {
                throw new SqlQueryException("Ошибка при получении списка валют");
            } catch (SqlQueryException ex) {
                throw new RuntimeException(ex);
            }
        }
        objectMapper.writeValue(res.getWriter(), result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        NewCurrencyPayload newCurrencyPayload;

        if (CONTENT_TYPE_JSON.equalsIgnoreCase(req.getContentType())) {
            newCurrencyPayload = objectMapper.readValue(req.getReader(), NewCurrencyPayload.class);
        } else {
            String code = req.getParameter("code");
            String name = req.getParameter("name");
            String sign = req.getParameter("sign");
            newCurrencyPayload = new NewCurrencyPayload(code, name, sign);
        }

        CurrencyDto newCurrency = null;
        try {
            newCurrency = currencyService.createCurrency(newCurrencyPayload);
        } catch (SqlQueryException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(res.getWriter(),
                    Map.of("error", "Ошибка при сохранении валюты", "details", e.getMessage()));
        }
        res.setContentType(CONTENT_TYPE_JSON);
        res.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(res.getWriter(), newCurrency);
    }
}
