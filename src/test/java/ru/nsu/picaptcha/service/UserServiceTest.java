package ru.nsu.picaptcha.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.nsu.picaptcha.dao.UserRepository;
import ru.nsu.picaptcha.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.mockito.Matchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void init() {
        userService = new UserService(userRepository);

        User user = new User();
        user.setId(1);
        user.setLogin("user");
        user.setPassword("pass");
        user.setIsBanned(false);

        User friend = new User();
        friend.setId(2);
        friend.setLogin("friend");
        friend.setPassword("TheBestFriendsForever");
        friend.setIsBanned(false);

        User gena = new User();
        gena.setId(3);
        gena.setLogin("Gena_Crocodile");
        gena.setPassword("JustAnotherGoodFriend");
        gena.setIsBanned(false);

        Set<User> friends = new HashSet<>();
        friends.add(friend);
        user.setFriends(friends);

        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user, friend, gena));
        Mockito.when(userRepository.save(any(User.class))).then(AdditionalAnswers.returnsFirstArg());
    }

    @Test
    public void find_Success() {
        Assert.assertEquals(userService.find("user").get().getId(), 1);
        Assert.assertEquals(userService.find("friend").get().getId(), 2);
    }

    @Test(expected = NoSuchElementException.class)
    public void find_NotFind() {
        userService.find("Hacker_Anatolij").get().getId();
    }

    @Test
    public void setBanned_Success() {
        Assert.assertEquals(userService.setBanned("user", true), true);
        Assert.assertEquals(userService.setBanned("user", false), true);
    }

    @Test
    public void setBanned_UserNoExist() {
        Assert.assertEquals(userService.setBanned("Hacker_Anatolij", true), false);
    }

    @Test
    public void addFriend_Success() {
        Assert.assertEquals(userService.addFriend("user", "Gena_Crocodile"), true);
    }

    @Test
    public void addFriend_AlreadyFriends() {
        Assert.assertEquals(userService.addFriend("user", "friend"), false);
    }

    @Test
    public void addFriend_UserNoExist() {
        Assert.assertEquals(userService.addFriend("user", "Hacker_Anatolij"), false);
    }

    @Test
    public void removeFriend_Success() {
        Assert.assertEquals(userService.removeFriend("user", "friend"), true);
    }

    @Test
    public void removeFriend_AlreadyFriends() {
        Assert.assertEquals(userService.removeFriend("user", "Gena_Crocodile"), false);
    }

    @Test
    public void removeFriend_UserNoExist() {
        Assert.assertEquals(userService.removeFriend("user", "Hacker_Anatolij"), false);
    }

    @Test
    public void save() {
        User user = new User();
        user.setId(4);
        user.setLogin("raccoon");
        user.setPassword("SaveMePleaseFromThisLab");
        user.setIsBanned(false);

        Assert.assertEquals(userService.save(user).getLogin(), "raccoon");
    }
}