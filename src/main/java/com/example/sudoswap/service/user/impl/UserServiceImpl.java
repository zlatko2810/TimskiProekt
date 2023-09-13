package com.example.sudoswap.service.user.impl;

import com.example.sudoswap.entity.user.UserEntity;
import com.example.sudoswap.entity.user.UserRole;
import com.example.sudoswap.entity.wallet.WalletEntity;
import com.example.sudoswap.entity.wallet.WalletType;
import com.example.sudoswap.repository.UserRepository;
import com.example.sudoswap.repository.WalletRepository;
import com.example.sudoswap.service.common.CommonService;
import com.example.sudoswap.service.user.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final CommonService commonService;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, WalletRepository walletRepository, CommonService commonService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.commonService = commonService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerNewUser(String firstName, String lastName, String username, String password) throws Exception {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception("Username already exists");
        }
        UserEntity user = new UserEntity(firstName, lastName, username, passwordEncoder.encode(password), UserRole.USER);
        WalletEntity walletBTC = new WalletEntity(generateRandomCryptoAddress(), WalletType.BTC, 0.0F);
        WalletEntity walletETH = new WalletEntity(generateRandomCryptoAddress(), WalletType.ETH, 0.0F);
        walletRepository.save(walletBTC);
        walletRepository.save(walletETH);
        user.getWallets().add(walletBTC);
        user.getWallets().add(walletETH);
        userRepository.save(user);
    }

    @Override
    public List<WalletEntity> getUserWallets() throws Exception {
        return userRepository.findById(commonService.getLoggedInUser().getId()).orElseThrow(Exception::new).getWallets();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(user.get().getRole().toString()));

        return new User(user.get().getUsername(), user.get().getPassword(), authorities);
    }


    public static String generateRandomCryptoAddress() {
        return RandomStringUtils.randomAlphanumeric(42);
    }


}
