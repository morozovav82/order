package ru.morozov.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.morozov.order.dto.CartDto;

@Service
@Slf4j
public class CartService {

    @Value("${cart.url}")
    private String cartUrl;

    public CartDto getCart(Long userId) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = cartUrl + "/" + userId;
            log.debug("Sent request to " + url);
            ResponseEntity<CartDto> result = restTemplate.getForEntity(url, CartDto.class);
            log.info("Order found. Result: {}", result.getBody());
            return result.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Cart not found by userId: " + userId);
        } catch (Throwable e) {
            throw new RuntimeException("Failed to get cart by userId " + userId, e);
        }
    }
}
