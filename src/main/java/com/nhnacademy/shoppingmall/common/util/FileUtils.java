package com.nhnacademy.shoppingmall.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

@Slf4j
public final class FileUtils {

    public static final String FILE_PATH = "/Users/isehui/develop/Java/nhnAcademy/week10/shoppingmall_project/src/main/resources/imagefile/";
    private static final String CONTENT_TYPE = "image/jpeg"; // 이미지 파일인 경우 JPEG로 가정

    public static Map<String, String> fileSave(HttpServletRequest req) {
        try {
            Map<String, String> param = new HashMap<>();
            List<String> fileNames = new ArrayList<>();

            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Set factory constraints
            factory.setSizeThreshold(400000); // Set the maximum allowed size (in bytes) before a FileUploadException will be thrown.
            factory.setRepository(new File(System.getProperty("java.io.tmpdir"))); // Set the repository location where files will be stored temporarily.

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request
            List<FileItem> items = upload.parseRequest(req);

            // Process the uploaded items
            for (FileItem item : items) {
                if (item.isFormField()) { // Process regular form fields
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8"); // Ensure UTF-8 encoding
                    log.info("name = {} value ={}", name, value);
                    param.put(name, value);
                } else { // Process uploaded files
                    String fileName = item.getName();
                    String type = item.getContentType();
                    File file = new File(FILE_PATH, fileName);

                    try (InputStream fileContent = item.getInputStream();
                         OutputStream outputStream = new FileOutputStream(file)) {
                        IOUtils.copy(fileContent, outputStream); // Apache Commons IOUtils is used for stream copying
                    }

                    param.put("fileName", fileName);
                    fileNames.add(fileName);

                    log.info("실제 파일 이름 : {} ", fileName);
                    log.info("저장 파일 이름 : {}", file.getName());
                    log.info("파일 콘텐츠 유형 : {}", type);
                    log.info("파일 크기 : {}", file.length());
                }
            }
            req.setAttribute("fileNames", fileNames);
            return param;
        } catch (FileUploadException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendImageFile(String fileName, HttpServletResponse response) throws IOException {
        File file = new File(FILE_PATH + fileName);

        if (!file.exists() || !file.isFile()) {
            log.error("Not found file");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // 파일 전송 준비
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Content-Disposition", "inline; filename=" + fileName);
        response.setContentType(CONTENT_TYPE);

        // 파일 전송
        try (InputStream in = new FileInputStream(file); ServletOutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024 * 8];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            log.error("error {}", e.getMessage());
        }
    }

    public static boolean doesFileExist(String fileName) {
        // 파일이 존재하는지 확인할 디렉토리 경로와 파일 이름을 인자로 받습니다.
        File directory = new File(FILE_PATH);

        // 해당 경로가 디렉토리인지 확인합니다.
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Provided directory path is not valid.");
        }

        // 디렉토리 내에 파일 목록을 가져옵니다.
        File[] files = directory.listFiles();

        // 파일 목록을 순회하면서 주어진 파일 이름과 일치하는 파일이 있는지 확인합니다.
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(fileName)) {
                    return true; // 파일이 존재합니다.
                }
            }
        }

        return false; // 파일이 존재하지 않습니다.
    }
}
