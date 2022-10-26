package com.dimas.product.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.finspacedata.model.AwsCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Value("${dimas.app.accessKey}")
    String accessKey;

    @Value("${dimas.app.secretKey}")
    String secretKey;
    //String region = "ap-southeast-1"; khusus aws s3

    AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("https://72ca95a06799d7355825a63dce46008b.r2.cloudflarestorage.com","auto");

    @Bean
    public AmazonS3 s3(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
            accessKey,secretKey
        );

        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpoint) //pakai Cloudflare R2
                //.withRegion(region) khusus aws s3
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

}
