package com.example.chatapp;

public class Users {
    public Object imageuri;
    String profilepic,mail,userName,password,conpassword,userId,lastMessage,status;

    public Users()
    {

    }

    public Users(String uid, String name, String email, String password, String finalImageUri, String status){

    }
    public Users(String id, String name, String email, String password, String conpassword, String imageuri, String status){
        this.userId=id;
        this.userName=name;
        this.mail=email;
        this.password=password;
        this.conpassword=conpassword;
        this.profilepic=imageuri;
        this.status=status;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
