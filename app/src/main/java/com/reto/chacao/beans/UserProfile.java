package com.reto.chacao.beans;

/**
 * Created by Eduardo Luttinger on 20/05/2015.
 */
public class UserProfile extends AppBean {

    private int userId;
    private String email;
    private String password;
    private String familyName;
    private String fbId;
    private String firstName;
    private String middleName;
    private String username;
    private boolean fbPostPermissions = Boolean.TRUE;
    private String fbToken;
    private String confirmationToken;
    private Integer numericConfirmationToken;
    private String apiToken;
    private String salt;

    private String zipCode;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setUsername(this.email);
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFbPostPermissions() {
        return fbPostPermissions;
    }

    public void setFbPostPermissions(boolean fbPostPermissions) {
        this.fbPostPermissions = fbPostPermissions;
    }

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getNumericConfirmationToken() {
        if (numericConfirmationToken == null){
            numericConfirmationToken = 0;
        }
        return numericConfirmationToken;
    }

    public void setNumericConfirmationToken(Integer numericConfirmationToken) {
        this.numericConfirmationToken = numericConfirmationToken;
    }
}
