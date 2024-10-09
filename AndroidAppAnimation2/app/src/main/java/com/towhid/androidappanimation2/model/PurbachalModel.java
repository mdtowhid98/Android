package com.towhid.androidappanimation2.model;

public class PurbachalModel {

    private  int id;

    private String name,clubPosition, address1,address2,  phone, cell,email, spouseName,membershipNo, memberSince, bloodGroup, DistrictPosition, memberImage, spouseImage;


    public PurbachalModel() {
    }

    public PurbachalModel(int id, String name, String clubPosition, String address1, String address2, String phone, String cell, String email, String spouseName, String membershipNo, String memberSince, String bloodGroup, String districtPosition, String memberImage, String spouseImage) {
        this.id = id;
        this.name = name;
        this.clubPosition = clubPosition;
        this.address1 = address1;
        this.address2 = address2;
        this.phone = phone;
        this.cell = cell;
        this.email = email;
        this.spouseName = spouseName;
        this.membershipNo = membershipNo;
        this.memberSince = memberSince;
        this.bloodGroup = bloodGroup;
        DistrictPosition = districtPosition;
        this.memberImage = memberImage;
        this.spouseImage = spouseImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClubPosition() {
        return clubPosition;
    }

    public void setClubPosition(String clubPosition) {
        this.clubPosition = clubPosition;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getMembershipNo() {
        return membershipNo;
    }

    public void setMembershipNo(String membershipNo) {
        this.membershipNo = membershipNo;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDistrictPosition() {
        return DistrictPosition;
    }

    public void setDistrictPosition(String districtPosition) {
        DistrictPosition = districtPosition;
    }

    public String getMemberImage() {
        return memberImage;
    }

    public void setMemberImage(String memberImage) {
        this.memberImage = memberImage;
    }

    public String getSpouseImage() {
        return spouseImage;
    }

    public void setSpouseImage(String spouseImage) {
        this.spouseImage = spouseImage;
    }
}
