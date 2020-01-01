package com.sdcommunity.user.service;

import com.sdcommunity.user.pojo.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Leon
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void testAdd(){
        Admin admin = new Admin();
        admin.setPassword("123");
        adminService.add(admin);
    }

}
