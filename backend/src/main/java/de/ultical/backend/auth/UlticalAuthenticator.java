package de.ultical.backend.auth;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import com.google.common.base.Optional;

import de.ultical.backend.app.MyBatisManager;
import de.ultical.backend.data.mapper.UserMapper;
import de.ultical.backend.model.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class UlticalAuthenticator implements Authenticator<BasicCredentials, User> {
    private final MyBatisManager mbm;

    public UlticalAuthenticator(MyBatisManager mbm) {
        this.mbm = mbm;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        final String providedUserName = credentials.getUsername();
        final String providedPassword = credentials.getPassword();
        SqlSession sqlSession = this.mbm.provide();
        User user = null;
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            user = userMapper.getByEmail(providedUserName);
        } catch (PersistenceException pe) {
            throw new AuthenticationException("Accessing the database failed", pe);
        } finally {
            sqlSession.close();
        }
        Optional<User> result = Optional.absent();
        if (user != null && user.getPassword().equals(providedPassword)) {
            result = Optional.of(user);
        }
        return result;
    }
}