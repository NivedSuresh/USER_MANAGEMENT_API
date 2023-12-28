package com.module.library.SERVICES;

import org.springframework.security.core.Authentication;

public interface JwtService {
    String JWT_SECRET = "bedjdcbeiuemxlsxieugncdkjcbuejsxnkchgcvdcmdcjchdcbldckdcjdbcoodbcuygfi";
    String JWT_HEADER = "Authorization";

    String generateJwt(Authentication authentication);
    String[] getDataFromJwt(String jwt);

    boolean validateJwt(String jwt);
}
