package com.ttstudios.pi;

import com.ttstudios.pi.transform.DtoToEntityMapper;
import com.ttstudios.pi.transform.MergeMapper;
import org.mapstruct.factory.Mappers;
import org.neo4j.cypher.internal.compiler.v2_1.ast.Merge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public BookRepository booksRepo() {
        return new BookRepository();
    }

//    @Bean
//    public DtoToEntityMapper dtoToEntityMapper() {
//        return Mappers.getMapper(DtoToEntityMapper.class);
//    }
//
//    @Bean
//    public MergeMapper mergeMapper(){
//        return Mappers.getMapper(MergeMapper.class);
//    }
}
