package com.yww.dao;

import com.yww.entity.Selection;

import java.util.List;
import java.util.Map;

public interface SelectionDao {
    void insert(List<Selection> seles);
    void delete(int sid, int cid);
    List<Map<String,Object>> selectByStudent(int sid);
    List<Map<String,Object>> selectByCourse(int cid);
}
