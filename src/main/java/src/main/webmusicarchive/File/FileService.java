package src.main.webmusicarchive.File;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import src.main.webmusicarchive.Application.ApplicationException;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class FileService {
    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);

    private final ResourceLoader resourceLoader;
    private final AmazonS3 amazonS3;

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    @Value("${s3.region.name}")
    private String s3RegionName;

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try  {
            final FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(multipartFile.getBytes());
            outputStream.close();
        } catch (IOException e) {
            LOG.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
        }
        return file;
    }

    @Async
    public S3ObjectInputStream findByName(String fileName) {
        LOG.info("Downloading file with name {}", fileName);
        return amazonS3.getObject(s3BucketName, fileName).getObjectContent();
    }

    @Async
    public String save(final MultipartFile multipartFile, String desiredFileName) {
        final File file = convertMultiPartFileToFile(multipartFile);
        String extension = FilenameUtils.getExtension(file.getAbsolutePath());
        final String fileName = desiredFileName+'.'+extension;
        try {


            LOG.info("Uploading file with name {}", fileName);
            final PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, fileName, file);
            PutObjectResult result = amazonS3.putObject(putObjectRequest);


            Files.delete(file.toPath());

        } catch (AmazonServiceException e) {
            LOG.error("Error {} occurred while uploading file", e.getMessage());
            throw new ApplicationException("file/0001","An error occurred during file upload", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOG.error("Error {} occurred while deleting file", e.getMessage());
        }
        return fileName;
    }

    public String convertFileNameToBucketURI(String fileName){
        return String.format("https://s3.%s.amazonaws.com/%s/%s", s3RegionName  , s3BucketName, fileName);
    }


    @Async
    public void remove(String fileName){
        LOG.debug("File name {}",fileName);
        try{
            amazonS3.deleteObject(s3BucketName,fileName);
        }catch (AmazonServiceException e){
            LOG.error("Error {} occurred while deleting file", e.getMessage());
        }
    }

}
