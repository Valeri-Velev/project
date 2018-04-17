package com.example.demo.BindingModels;

import com.sun.istack.internal.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.JoinTable;
import javax.validation.constraints.NotNull;

public class UserBindingModel
{
    
    @Nullable
    private Double height;
    @Nullable
    private Double weight;
    @Nullable
    private String sex;
    @Nullable
    private Integer years;
    @NotNull
    private String email;
    @NotNull
    private String fullName;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
    private MultipartFile profilePicture;
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    public MultipartFile getProfilePicture()
    {
        return profilePicture;
    }
    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }
}
