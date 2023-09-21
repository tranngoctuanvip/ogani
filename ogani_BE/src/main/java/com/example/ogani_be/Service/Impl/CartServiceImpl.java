package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Common.Constant.Constant;
import com.example.ogani_be.Common.Mapper.Mapper;
import com.example.ogani_be.DTO.CartDto;
import com.example.ogani_be.Entity.Cart;
import com.example.ogani_be.Entity.Product;
import com.example.ogani_be.Repository.CartRepository;
import com.example.ogani_be.Repository.ProductRepository;
import com.example.ogani_be.Service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    private final Mapper mapper;
    @Override
    public Cart create(Long productId) {
        Cart cart = new Cart();
        cart.setCreateAt(LocalDateTime.now());
        cart.setProductId(productId);
        cart.setQuality(1);
        cart.setCreateBy(mapper.getUserId());
        cart.setDeleted(Constant.NOTDELETE);
        cart.setStatus(Constant.UNPAID);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public List<Map<String, Object>> getUnpaidCart() {
        var getUnpaidCart = cartRepository.getUnpaidCart();
        return getUnpaidCart;
    }

    @Override
    public void delete(Long id) {
        Optional<Cart> cartOptional = cartRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        Cart cart1 = cartOptional.get();
        cart1.setDeleteAt(LocalDateTime.now());
        cart1.setDeleted(Constant.DELETE);
        cartRepository.save(cart1);
    }

    @Override
    public Cart update(CartDto cartDto, Long id) {
        Optional<Cart> cartOptional = cartRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        Cart cart = cartOptional.get();
        cart.setQuality(cartDto.getQuantity());
        cart.setUpdateBy(mapper.getUserId());
        cart.setUpdateAt(LocalDateTime.now());
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart updateStatus(Integer status, Long id) {
        Optional<Cart> cartOptional = cartRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        if (cartOptional.isEmpty()){
            throw new RuntimeException("Giỏ hàng không tồn tại");
        }
        Cart cart = cartOptional.get();
        return null;
    }

    @Override
    public List<Map<String, Object>> unpaid() {
        var unpaid = cartRepository.unpaid();
        return unpaid;
    }
}
