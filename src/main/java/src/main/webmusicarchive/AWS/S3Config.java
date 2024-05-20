package src.main.webmusicarchive.AWS;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class S3Config {
    @Value("${access.key.id}")
    private String accessKeyId;

    @Value("${access.key.secret}")
    private String accessKeySecret;
    @Value("${session.token}")
    private String sessionToken;

    @Value("${s3.region.name}")
    private String s3RegionName;



    @Bean
    public AmazonS3 getAmazonS3Client() {
        BasicSessionCredentials credentials = new BasicSessionCredentials(
                accessKeyId,
                accessKeySecret,
                sessionToken
        );
        // Get Amazon S3 client and return the S3 client object
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(s3RegionName)
                .build();
    }
}
