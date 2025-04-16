package com.edu.boot.entity.Vo;

import com.edu.boot.entity.Recruitment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class RecruitmentChildrenVo {
    public RecruitmentChildrenVo(Integer id, List<Recruitment> children) {
        this.id = id;
        this.children = children;
    }

    Integer id;
    List<Recruitment> children;


}
