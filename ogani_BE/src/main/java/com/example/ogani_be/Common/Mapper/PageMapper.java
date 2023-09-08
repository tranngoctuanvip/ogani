package com.example.ogani_be.Common.Mapper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {
    public Pageable pageable(int page, int size, String sortType, String sortBy){
        Sort sort;
        if (sortType.equals("desc")){
            sort = Sort.by(Sort.Direction.DESC,sortBy);
        }
        else {
            sort = Sort.by(Sort.Direction.ASC,sortBy);
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        return  pageable;
    }
}
