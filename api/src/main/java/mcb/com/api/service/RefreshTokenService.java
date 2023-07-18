package mcb.com.api.service;

import lombok.RequiredArgsConstructor;
import mcb.com.api.utils.MessageUtil;
import mcb.com.domain.dto.response.exception.BadRequestException;
import mcb.com.domain.dto.response.exception.UserNotFoundException;
import mcb.com.domain.entity.RefreshToken;
import mcb.com.domain.entity.Users;
import mcb.com.persistence.RefreshTokenRepo;
import mcb.com.persistence.UsersRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;


public interface RefreshTokenService {


     Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(String userId);

     RefreshToken verifyExpiration(RefreshToken token);


}
