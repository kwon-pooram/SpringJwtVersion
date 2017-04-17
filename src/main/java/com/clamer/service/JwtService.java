package com.clamer.service;

import com.clamer.domain.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtService implements Serializable {

    /**
     * www.jsonwebtoken.io
     * !! 너무 많은 내용 담으면 오버헤드 발생 !!
     * <p>
     * - 라이브러리 기본 제공 클레임
     * String ISSUER = "iss"; 토큰 발급자 (Clamer)
     * String SUBJECT = "sub"; 토큰 소유 유저 네임
     * String AUDIENCE = "aud"; 접속 기기 종류
     * String EXPIRATION = "exp"; 토큰 만료 날짜 ( + 30분 )
     * String ISSUED_AT = "iat";  토큰 발급 날짜
     * String ID = "jti"; 토큰 고유 아이디 (랜덤 UUID)
     **/

    //    토큰 발급자
    private static final String CLAIM_KEY_ISSUER = "clamer";
    //    만료 날짜에 더할 시간
    private static final int TOKEN_VALIDITY_PERIOD_IN_MINUTES = 1;


    //    접속 기기 종류
    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";


    /**
     * 토큰 디코딩에 필요한 Signature
     * !! 비공개 정보 !!
     * 추후 Java Key Tool 이용한 RSA 방식 (Public key, Private key) 변경 계획
     **/
    private static final String SECRET = "clamer";


    /**
     * GETTER
     **/
    //    토큰에 저장되어 있는 모든 정보(클레임) 반환
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    //    ID 반환
    public String getIdFromToken(String token) {
        String id;
        try {
            final Claims claims = getClaimsFromToken(token);
            id = claims.getId();
        } catch (Exception e) {
            id = null;
        }
        return id;
    }

    //    발급자 반환
    public String getIssuerFromToken(String token) {
        String issuer;
        try {
            final Claims claims = getClaimsFromToken(token);
            issuer = claims.getIssuer();
        } catch (Exception e) {
            issuer = null;
        }
        return issuer;
    }

    //    유저 네임 반환
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    //    토큰 생성 날짜 반환
    public Date getIssuedDateFromToken(String token) {
        Date issuedDate;
        try {
            final Claims claims = getClaimsFromToken(token);
            issuedDate = claims.getIssuedAt();
        } catch (Exception e) {
            issuedDate = null;
        }
        return issuedDate;
    }

    //    토큰 만료 날짜 반환
    public Date getExpirationDateFromToken(String token) {
        Date expirationDate;
        try {
            final Claims claims = getClaimsFromToken(token);
            expirationDate = claims.getExpiration();
        } catch (Exception e) {
            expirationDate = null;
        }
        return expirationDate;
    }

    //    접속 기기 반환
    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    /**
     * GETTER ENDS
     **/


    //    만료 날짜 생성, 발급 날짜에서 변수 시간 더해서 반환
    private Date generateExpirationDate(Date issuedDate) {
        Date expirationDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issuedDate);
        calendar.add(Calendar.MINUTE, TOKEN_VALIDITY_PERIOD_IN_MINUTES);
        expirationDate = calendar.getTime();
        return expirationDate;
    }

    //    토큰 만료 여부 확인
    private Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    //    토큰 생성 시간이 비밀번호 변경 시간보다 전인지 확인, 비밀번호 변경시 기존 토큰 사용 불가능
    private Boolean isCreatedBeforePasswordUpdated(Date created, Date passwordUpdatedAt) {
        return (passwordUpdatedAt != null && created.before(passwordUpdatedAt));
    }

    //    접속 기기 체크 (확인 불가, 웹, 테블릿, 모바일)
    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    //    모바일, 테블릿의 경우 토큰 만료 없음
    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    //    토큰 생성 메소드
    public String generateToken(UserDetails userDetails, Device device) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setId(UUID.randomUUID().toString())
                .setIssuer(CLAIM_KEY_ISSUER)
                .setIssuedAt(new Date())
                .setAudience(generateAudience(device))
                .setExpiration(generateExpirationDate(new Date()))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    //    토큰 업데이트 가능 여부 판단
    //    1) 비밀번호 변경 이후에 만들어졌고  2) 토큰이 아직 만료되지 않았거나 만료 기간이 없는 경우
    //    !! 이미 만료 기간이 지난 토큰은 사용 불가능, 새로 발급받아야 함 !!
    public Boolean canTokenBeRefreshed(String token, Date passwordUpdatedAt) {
        final Date createdDate = getIssuedDateFromToken(token);
        return !isCreatedBeforePasswordUpdated(createdDate, passwordUpdatedAt)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    //    토큰 업데이트, 기존 발급된 토큰에서 클레임 가져오는 메소드
    public String refreshToken(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return refreshToken(claims);
        } catch (Exception e) {
            return null;
        }
    }

    //    실제 토큰 업데이트 메소드, 기존 클레임에서 정보 가져오고 생성 날짜, 만료 날짜만 현재 시간 기준으로 업데이트해서 재발급
    private String refreshToken(Claims claims) {
        return Jwts.builder()
                .setSubject(claims.getSubject())
                .setId(claims.getId())
                .setIssuer(claims.getIssuer())
                .setIssuedAt(new Date())
                .setAudience(claims.getAudience())
                .setExpiration(generateExpirationDate(new Date()))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }


    //    토큰 인증 메소드
    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String usernameFromToken = getUsernameFromToken(token);
        final Date issuedDateFromToken = getIssuedDateFromToken(token);
        return (
                usernameFromToken
                        .equals(
                                user.getUsername()) // 토큰과 접속 유저의 이름이 일치하는가
                        && !isTokenExpired(token) // 토큰 만료 기간이 지나지 않았는가
                        && !isCreatedBeforePasswordUpdated( // 비밀 번호 변경 이후에 생성된 토큰인가
                                issuedDateFromToken, user.getPasswordUpdatedAt()
                )
        );
    }
}
