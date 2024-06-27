package com.amatta.findog.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        String filename = UUID.randomUUID().toString();

        try {
            amazonS3.putObject(bucket, getFolderName() +"/"+ filename, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException("S3에 파일을 저장하지 못했습니다.");
        }

        return amazonS3.getUrl(bucket, getFolderName() +"/"+filename).toString();
    }

    public void deleteFile(String fileUrl) {
        String key = extractStoredFilePathFromUrl(fileUrl);
        try {
            amazonS3.deleteObject(bucket, key);
        } catch (AmazonServiceException e) {
            throw new RuntimeException("파일을 찾지 못했습니다.");
        }
    }

    private String getFolderName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", "/");
    }

    /*
    S3 파일 URL로 부터 경로 추출
    */
    public String extractStoredFilePathFromUrl(String fileUrl) {
        String splitStr = ".com/";
        String path = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());
        System.out.println((path).replace(File.separatorChar, '/'));
        return (path).replace(File.separatorChar, '/');
    }
}
