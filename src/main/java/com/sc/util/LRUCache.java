package com.sc.util;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class LRUCache implements Cache {

	@Value("${cleanupPeriodInsec: #{60}}")
	private long cleanupPeriodInsec;

	@Value("${lruSize}")
	private long lruSize;

	private final  Map<Object, Entry> hashmap = new ConcurrentHashMap<>();;
	Entry start, end;

	public LRUCache() {

		Thread cleanerThread = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(cleanupPeriodInsec * 1000);

					hashmap.entrySet().removeIf(entry -> Optional.ofNullable(entry.getValue()).map(Entry::isExpired).orElse(false));
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});
		cleanerThread.setDaemon(true);
		cleanerThread.start();
	}

	@Override
	public Object get(String key) {
		if (hashmap.containsKey(key)) // Key Already Exist, just update the
		{
			Entry entry = hashmap.get(key);
			remove(key);
			addAtTop(entry);
			return entry.value;
		}
		return null;
	}
	@Override
	public void add(String key, Object value,long periodInMillis) {
		if (key == null) {
			return;
		}
		if (value == null) {
			remove(key);
		}
		long expiryTime = System.currentTimeMillis() + periodInMillis;

		if (hashmap.containsKey(key)) // Key Already Exist, just update the value and move it to top
		{
			Entry entry = hashmap.get(key);
			entry.value = value;
			remove(key);
			entry.timeTolive = expiryTime;
			addAtTop(entry);
		} else {
			Entry newnode = new Entry();
			newnode.left = null;
			newnode.right = null;
			newnode.value = value;
			newnode.key = key;
			newnode.timeTolive = periodInMillis;
			if (hashmap.size() > lruSize) // We have reached maxium size so need to make room for new element.
			{
				hashmap.remove(end.key);
				remove(key);				
				addAtTop(newnode);

			} else {
				addAtTop(newnode);
			}

			hashmap.put(key, new Entry());

			hashmap.put(key, newnode);
		}
	}
	public void addAtTop(Entry node) {
		node.right = start;
		node.left = null;
		if (start != null)
			start.left = node;
		start = node;
		if (end == null)
			end = start;
	}
	@Override
	public void remove(String key) {
		Entry node = null; 

		if (hashmap.containsKey(key)) // Key Already Exist, just update the
		{
			node = hashmap.get(key);
			hashmap.remove(key);

		}
		if(node != null) {
			if (node.left != null) {
				node.left.right = node.right;
			} else {
				start = node.right;
			}

			if (node.right != null) {
				node.right.left = node.left;
			} else {
				end = node.left;
			}
		}
	}



	@Override
	public void clear() {
		hashmap.clear();
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return hashmap.size();
	}

}
