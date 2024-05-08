package com.nhnacademy.shoppingmall.controller.image;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.FileUtils;
import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.entity.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.image.service.ImageService;
import com.nhnacademy.shoppingmall.entity.image.service.ImageServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/loadImage.do")
public class ImageViewController implements BaseController {
    private ImageService service = new ImageServiceImpl(new ImageRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");

            String imageName = null;

            if(req.getParameter("id") != null) {
                // 이미지 파일 경로 및 ID 파라미터 확인
                Integer productId = FormValidator.stringToInteger(req.getParameter("id"));
                imageName = service.findOneById(productId);
            }

            if(req.getParameter("image") != null) {
                imageName = req.getParameter("image");
            }

            if(imageName == null) return null;

            log.debug("imageName, {} ", imageName);

            // 파일 전송
            FileUtils.sendImageFile(imageName, resp);

        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported encoding", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.error("IO error", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return null;
    }
}
