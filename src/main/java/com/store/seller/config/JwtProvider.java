package com.store.seller.config;

import com.store.seller.enums.TIRE_CODE;
import com.store.seller.enums.USER_ROLE;
import com.store.seller.error.BadRequestException;
import com.store.seller.model.User;
import com.store.seller.repo.UserRepository;
import com.store.seller.utils.EncryptionUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class JwtProvider {

	private UserRepository userRepository;
	private final SecretKey key=Keys.hmacShaKeyFor(KeywordsAndConstants.SECRET_KEY.getBytes());
	
	public String generateToken(String id, String email, USER_ROLE role) {

		String encryptedEmail = null;
		try {
			encryptedEmail = EncryptionUtils.encrypt(email);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TIRE_CODE tire = TIRE_CODE.TIRE4;

		if(Objects.equals(role.toString(), USER_ROLE.ROLE_MASTER.toString())) tire = TIRE_CODE.TIRE0;
		if(Objects.equals(role.toString(), USER_ROLE.ROLE_ADMIN.toString())) tire = TIRE_CODE.TIRE1;
		if(Objects.equals(role.toString(), USER_ROLE.ROLE_SELLER.toString())) tire = TIRE_CODE.TIRE2;
		if(Objects.equals(role.toString(), USER_ROLE.ROLE_CUSTOMER_CARE.toString())) tire = TIRE_CODE.TIRE3;
		if(Objects.equals(role.toString(), USER_ROLE.ROLE_DELIVERY_BOY.toString())) tire = TIRE_CODE.TIRE3;
		if(Objects.equals(role.toString(), USER_ROLE.ROLE_CUSTOMER.toString())) tire = TIRE_CODE.TIRE4;

		String jwt=Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+86400000))
				.claim("email", encryptedEmail)
				.claim("id", id)
				.claim("role", role)
				.claim("tire",tire)
				.signWith(key)
				.compact();
		return jwt;
		
	}

	private String fetchUserIdByEmail(String email) {
		Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
		if (userOptional.isPresent()) {
			return String.valueOf(userOptional.get().getId());
		}
		throw new UsernameNotFoundException("User not found with email: " + email);
	}
	
	public String getEmailFromJwtToken(String jwt) {
		jwt=jwt.substring(7);
		Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String encryptedMail = String.valueOf(claims.get("email"));
		try {
			return EncryptionUtils.decrypt(encryptedMail);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getIdFromJwtToken(String jwt) {
		jwt=jwt.substring(7);
		Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String id = String.valueOf(claims.get("id"));
		BadRequestException badRequestException = new BadRequestException();
		badRequestException.setErrorMessage("Error fetching Id From Token");
		if(id==null) throw badRequestException;
		else return id;
	}

	public String getRoleFromJwtToken(String jwt) {
		jwt=jwt.substring(7);
		Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		return String.valueOf(claims.get("role"));
	}

}
