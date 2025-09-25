package ru.yandex.practicum.shoppingcart.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.iteractionapi.dto.BookedProductsDto;
import ru.yandex.practicum.iteractionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.iteractionapi.feign.WarehouseClient;
import ru.yandex.practicum.iteractionapi.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingcart.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.shoppingcart.exception.NotAuthorizedUserException;
import ru.yandex.practicum.shoppingcart.mapper.ShoppingCartMapper;
import ru.yandex.practicum.shoppingcart.model.ShoppingCart;
import ru.yandex.practicum.shoppingcart.repository.ShoppingCartRepository;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final WarehouseClient warehouseClient;

    @Override
    public ShoppingCartDto findCart(String username) {
        log.info("Получение корзины покупок для пользователя: {}", username);
        ShoppingCartDto shoppingCartDto = ShoppingCartMapper.INSTANCE.toShoppingCartDto(findShoppingCartByUser(username));
        log.debug("Корзина успешно найдена для пользователя: {}", username);
        return shoppingCartDto;
    }

    @Override
    @Transactional
    public ShoppingCart findShoppingCartByUser(String username) {
        log.info("Поиск активной корзины для пользователя: {}", username);
        checkUsername(username);

        Optional<ShoppingCart> opt = shoppingCartRepository.findByUsernameAndActive(username, true);
        if (opt.isPresent()) {
            ShoppingCart cart = opt.get();
            if (cart.getProducts() == null) {
                cart.setProducts(new HashMap<>());
                cart = shoppingCartRepository.save(cart);
            }
            log.debug("Найдена существующая корзина (id =: {}) для пользователя: {}", cart.getShoppingCartId(), username);
            return cart;
        }

        log.info("Активная корзина не найдена. Создаю новую для пользователя: {}", username);
        ShoppingCart newCart = ShoppingCart.builder()
                .username(username)
                .active(true)
                .products(new HashMap<>())
                .build();
        newCart = shoppingCartRepository.save(newCart);
        log.info("Новая корзина создана (id =: {}) для пользователя: {}", newCart.getShoppingCartId(), username);
        return newCart;
    }

    @Override
    public ShoppingCartDto addProductToShoppingCart(String username, Map<UUID, Long> request) {
        log.info("Добавление товаров в корзину. Пользователь: {}, Кол-во товаров: {}", username, request.size());
        checkUsername(username);
        checkProductAvailability(request);

        ShoppingCart cart = findShoppingCartByUser(username);
        if (cart.getProducts() == null) {
            cart.setProducts(new HashMap<>());
        }
        cart.getProducts().putAll(request);

        cart = shoppingCartRepository.save(cart);
        log.info("Товары добавлены. id корзины: {}, Кол-во позиций: {}", cart.getShoppingCartId(), cart.getProducts().size());
        return shoppingCartMapper.toShoppingCartDto(cart);
    }

    @Override
    public void deactivateShoppingCartByUser(String username) {
        log.info("Деактивация корзины пользователя: {}", username);
        ShoppingCart cart = findShoppingCartByUser(username);
        if (!Boolean.TRUE.equals(cart.getActive())) {
            return;
        }
        cart.setActive(false);
        shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCartDto deleteProductsFromShoppingCart(String username, List<UUID> products) {
        log.info("Удаление товаров из корзины. Пользователь: {}, Кол-во удаляемых товаров: {}", username, products.size());

        ShoppingCart cart = findShoppingCartByUser(username);
        Map<UUID, Long> map = cart.getProducts();
        if (map == null || map.isEmpty()) {
            throw new NoProductsInShoppingCartException("В корзине нет товаров для удаления");
        }

        if (!map.keySet().containsAll(products)) {
            throw new NoProductsInShoppingCartException("Некоторые товары не найдены в корзине");
        }

        products.forEach(map::remove);
        cart.setProducts(map);
        cart = shoppingCartRepository.save(cart);

        return ShoppingCartMapper.INSTANCE.toShoppingCartDto(cart);
    }

    @Override
    public ShoppingCartDto updateProductQuantity(String username, ChangeProductQuantityRequest requestDto) {
        log.info("Изменение количества товара. Пользователь: {}, Товар: {}, Новое количество: {}",
                username,
                requestDto.getProductId(),
                requestDto.getNewQuantity());

        ShoppingCart cart = findShoppingCartByUser(username);

        if (!cart.getProducts().containsKey(requestDto.getProductId())) {
            throw new NoProductsInShoppingCartException("Товар не найден в корзине");
        }

        cart.getProducts().put(requestDto.getProductId(), requestDto.getNewQuantity());

        cart = shoppingCartRepository.save(cart);

        return ShoppingCartMapper.INSTANCE.toShoppingCartDto(cart);
    }

    private void checkUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new NotAuthorizedUserException("Имя пользователя должно быть заполнено.");
        }
    }

    private void checkProductAvailability(Map<UUID, Long> products) {
        ShoppingCartDto cartDto = ShoppingCartDto.builder()
                .shoppingCartId(UUID.randomUUID())
                .products(products)
                .build();

        try {
            BookedProductsDto response = warehouseClient.checkProductQuantityForCart(cartDto);
            log.debug("Проверка наличия товаров завершена. Ответ склада: {}", response);
        } catch (FeignException e) {
            log.error("Ошибка при проверке наличия товаров: {}", e.getMessage());
            throw new IllegalStateException("Сервис склада недоступен. Попробуйте позже.");
        }
    }
}
