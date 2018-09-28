package xyz.wongs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.wongs.config.Provider;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProviderApplication.class)
public class RabbitmqApplicationTests {

    @Autowired
    private Provider provider;

    @Test
    public void contextLoads() throws Exception{
        provider.sendToQueue();
    }
}
