package com.nhnacademy.shoppingmall.controller.file;

import com.google.common.collect.ArrayListMultimap;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.FileUtils;
import com.nhnacademy.shoppingmall.product.model.ImageDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/loadImage.do")
public class DisplayImageController implements BaseController {
    private static final String FILE_PATH = "/Users/isehui/develop/Java/nhnAcademy/week10/shoppingmall_project/src/main/resources/imagefile/";
    private final ImageDao imageDao = new ImageDao();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");
            String imageName = null;
            if(req.getParameter("id") != null) {
                // 이미지 파일 경로 및 ID 파라미터 확인
                String productId = req.getParameter("id");
                ArrayListMultimap<String, String> imageNameMap = imageDao.findById(productId);

                if (imageNameMap.isEmpty()) {
                    // 이미지가 존재하지 않는 경우
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return null;
                }
                List<String> imageNames = imageNameMap.get(productId);
                imageName = imageNames.get(0);
            }

            if(req.getParameter("image") != null) {
                imageName = req.getParameter("image");
            }

            String imagePath = FILE_PATH + imageName;
            // 파일 전송
            FileUtils.sendImageFile(imagePath, imageName, resp);

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
