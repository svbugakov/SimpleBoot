package main;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Cacheable(cacheNames="myCache",key="#id")
    public MyCache getEmployeeByID(String id)
    {
        System.out.println("get from db " + id);

        return new MyCache(id,"Suman Das");
    }
}
