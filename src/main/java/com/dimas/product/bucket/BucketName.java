package com.dimas.product.bucket;

public enum BucketName {

    BUCKET("a.dimasblog.my.id");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName(){
        return bucketName;
    }
}
