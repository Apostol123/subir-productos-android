package com.example.subirproductosamitienda.Logica.Session;

public interface Session {
    boolean isLoggedIn();

    void saveToken(String token);

    String getToken();

    void saveEmail(String email);

    String getEmail();

    void savePassword(String password);

    String getPassword();

    void invalidate();


}
