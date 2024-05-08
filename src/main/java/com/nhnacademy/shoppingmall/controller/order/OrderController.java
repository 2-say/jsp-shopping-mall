package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.SessionConst;
import com.nhnacademy.shoppingmall.entity.cart.domain.Cart;
import com.nhnacademy.shoppingmall.entity.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.cart.service.CartService;
import com.nhnacademy.shoppingmall.entity.cart.service.CartServiceImpl;
import com.nhnacademy.shoppingmall.entity.product.entity.Product;
import com.nhnacademy.shoppingmall.entity.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.service.ProductService;
import com.nhnacademy.shoppingmall.entity.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.entity.user.domain.User;
import com.nhnacademy.shoppingmall.entity.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.user.service.UserService;
import com.nhnacademy.shoppingmall.entity.user.service.impl.UserServiceImpl;
import com.nhnacademy.shoppingmall.entity.userscart.repository.UserCartRepositoryImpl;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/order.do")
public class OrderController implements BaseController {
    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private CartService cartService = new CartServiceImpl(new CartRepositoryImpl(), new UserCartRepositoryImpl());
    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        String userId = (String) session.getAttribute(SessionConst.LOGIN_USER_ID);

        if (userId == null) {
            req.setAttribute("error", "로그인 후 다시 시도해주세요");
            return "redirect:/index.do";
        }

        List<Cart> cartList = cartService.findByUserId(userId);

        int totalPrice = 0;
        int cartId = -1;
        for (Cart cart : cartList) {
            Product product = productService.findById(cart.getProductId());
            log.info("상품 재고 감소 {} -> {} ", product.getProductField(), product.getProductField() - 1);
            productService.deleteOneQuantity(cart.getProductId());
            totalPrice += product.getPrice() * cart.getCartProductQuantity();
            cartId = cart.getCartId();
        }

        log.info("user Id = {} 에서 totalPrice = {} 를 차감합니다", userId, totalPrice);
        User user = userService.getUser(userId);
        userService.decreasePoint(userId, totalPrice);

        //장바구니 삭제
        cartService.deleteCart(cartId);

        //결제완료 포인트 추가
        RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute("requestChannel");

        ChannelRequest request = new PointChannelRequest(user, totalPrice);
        try {
            //포인트 충전 쓰레드 시작
            requestChannel.addRequest(request);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "shop/order/order_complete";
    }
}
