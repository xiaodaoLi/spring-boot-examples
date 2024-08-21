package com.cxf.hgb.endpoint;

import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService
@Component
public class UserSsoServiceImpl implements UserSsoService {
    @Override
    public String getSsoBySeesionToken(String seesionToken) {
        return "12345678";
    }

}
