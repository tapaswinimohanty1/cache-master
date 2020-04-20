/**
 * 
 */
package com.sc.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.Assert;


/**
 * @author tapaswini
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CacheTest {
	@Autowired
	private LRUCache lruCache;

	@Test
	public void putTest() {
		if(lruCache == null) {
			lruCache = new LRUCache();
		}

		lruCache.add("test123", "Hi.firstVal", 100);
		String value = (String)lruCache.get("test123");
		Assert.assertEquals("Hi.firstVal",value);
	}
	
	@Test
	public void getTest() {
		if(lruCache == null) {
			lruCache = new LRUCache();
		}

		lruCache.add("test123", "Hi.firstVal", 100);
		String value = (String)lruCache.get("test123");
		Assert.assertEquals("Hi.firstVal",value);
	}
	
	@Test
	public void removeTest() {
		if(lruCache == null) {
			lruCache = new LRUCache();
		}

		lruCache.add("test123", "Hi.firstVal", 100);
		lruCache.remove("test123");
		Object value = lruCache.get("test123");
		Assert.assertNull(null,value);
	}
}
