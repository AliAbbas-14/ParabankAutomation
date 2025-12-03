package com.parabank.utilities;

import redis.clients.jedis.Jedis;
import java.util.*;

public class RedisDataReader {
    
    private static Jedis jedis;
    
    public static void connectToRedis() {
        try {
            jedis = new Jedis("localhost", 6379);
            System.out.println("✅ Connected to Redis successfully");
            System.out.println("Redis Server: " + jedis.ping());
        } catch (Exception e) {
            System.out.println("❌ Redis connection failed: " + e.getMessage());
        }
    }
    
    public static Map<String, String> getTestData(String key) {
        Map<String, String> testData = new HashMap<>();
        
        try {
            testData = jedis.hgetAll(key);
            System.out.println("✅ Retrieved test data from Redis key: " + key);
        } catch (Exception e) {
            System.out.println("❌ Error reading from Redis: " + e.getMessage());
        }
        
        return testData;
    }
    
    public static List<Map<String, String>> getAllTestData(String pattern) {
        List<Map<String, String>> allData = new ArrayList<>();
        
        try {
            Set<String> keys = jedis.keys(pattern + "*");
            for (String key : keys) {
                Map<String, String> data = jedis.hgetAll(key);
                data.put("redisKey", key);
                allData.add(data);
            }
        } catch (Exception e) {
            System.out.println("❌ Error reading multiple keys: " + e.getMessage());
        }
        
        return allData;
    }
    
    public static void closeConnection() {
        if (jedis != null) {
            jedis.close();
            System.out.println("✅ Redis connection closed");
        }
    }
}