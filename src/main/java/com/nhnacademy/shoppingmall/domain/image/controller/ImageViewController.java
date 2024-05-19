package com.nhnacademy.shoppingmall.domain.image.controller;

import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.FileUtils;
import com.nhnacademy.shoppingmall.global.common.util.FormValidator;
import com.nhnacademy.shoppingmall.domain.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.image.service.ImageService;
import com.nhnacademy.shoppingmall.domain.image.service.ImageServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/loadImage.do")
public class ImageViewController implements BaseController {
    private final ImageService service = new ImageServiceImpl(new ImageRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");

            String imageName = getImageName(req);

            if(imageName != null) {
                log.debug("imageName, {} ", imageName);
                sendImageFile(imageName, resp);
            }

        } catch (UnsupportedEncodingException e) {
            handleInternalServerError(resp, "Unsupported encoding", e);
        } catch (IOException e) {
            handleInternalServerError(resp, "IO error", e);
        }

        return null;
    }

    private String getImageName(HttpServletRequest req) {
        if(req.getParameter("productId") != null) {
            Integer productId = FormValidator.stringToInteger(req.getParameter("productId"));
            return service.findOneById(productId);
        }

        return req.getParameter("imageName");
    }

    private void sendImageFile(String imageName, HttpServletResponse resp) throws IOException {
        FileUtils.sendImageFile(imageName, resp);
    }

    private void handleInternalServerError(HttpServletResponse resp, String message, Exception e) {
        log.error(message, e);
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
