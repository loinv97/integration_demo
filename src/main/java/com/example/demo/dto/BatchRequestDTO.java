package com.example.demo.dto;

import java.util.List;
import java.util.Map;

public class BatchRequestDTO {
    private List<Map<String, Object>> create; // For Create operations
    private List<Map<String, Object>> update; // For Update operations
    private List<Long> delete; // For Delete operations
    private List<Map<String, Object>> patch; // For Patch operations

    public List<Map<String, Object>> getCreate() {
        return create;
    }

    public void setCreate(List<Map<String, Object>> create) {
        this.create = create;
    }

    public List<Map<String, Object>> getUpdate() {
        return update;
    }

    public void setUpdate(List<Map<String, Object>> update) {
        this.update = update;
    }

    public List<Long> getDelete() {
        return delete;
    }

    public void setDelete(List<Long> delete) {
        this.delete = delete;
    }

    public List<Map<String, Object>> getPatch() {
        return patch;
    }

    public void setPatch(List<Map<String, Object>> patch) {
        this.patch = patch;
    }

    @Override
    public String toString() {
        return "BatchRequest{" +
                "create=" + create +
                ", update=" + update +
                ", delete=" + delete +
                ", patch=" + patch +
                '}';
    }
}

