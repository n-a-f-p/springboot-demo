package com.example.springbootdemo.util;

import com.example.springbootdemo.pojo.Room;
import com.example.springbootdemo.pojo.Video;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;



public class MyFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前扫描的类的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取类上注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前类的资源（类路径等）
        Resource resource = metadataReader.getResource();
        return false;
    }
}
