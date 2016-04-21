/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import inet.common.jsf.request.MultipartRequest;
import inet.entities.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TUTL
 */
public class FileStorage {

    public static final String PATH_DICUSSION = "/storage/discussion/";
    public static final String PATH_AVATAR = "/storage/avatar/";
    public static final String PATH_NEWS = "/storage/news/";
    public static final String PATH_SEO = "/storage/seo/";

    private String realPath;
    private String realUri;
    private String targe;

    private MultipartRequest multipartRequest;

    public FileStorage(inet.common.jsf.request.MultipartRequest multipartRequest, String targe) {
        this.multipartRequest = multipartRequest;
        HttpServletRequest request = (HttpServletRequest) multipartRequest.getRequest();
        realPath = request.getServletContext().getRealPath("/");
        realUri = "http://" + request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + request.getContextPath();
        this.targe = targe;
    }

    public List<File> storage(String key) throws Exception {

        List<FileItem> fileItems = multipartRequest.getFileItems(key);
        List<File> files = new ArrayList<File>();
        File file;
        if (fileItems != null) {
            for (FileItem fileItem : fileItems) {
                if (fileItem == null || fileItem.getContentType().equals("application/octet-stream")) {
                    return null;
                } else {
                    file = new File();
                    int read = 0;
                    byte[] bytes = new byte[1024];

                    String fileName = targe + System.currentTimeMillis() + fileItem.getName();
                    String realFile = realPath + fileName;

                    OutputStream outputStream = new FileOutputStream(realFile);
                    InputStream inputStream = fileItem.getInputStream();

                    while ((read = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }

                    outputStream.close();
                    inputStream.close();
                    file.setUrl(realUri + fileName);
                }
                files.add(file);
            }
            return files;
        } else {
            return null;
        }
    }

}
