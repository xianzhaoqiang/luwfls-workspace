package com.inspur.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class LogAnalyse {
	private static ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	private static AtomicInteger totalCount = new AtomicInteger(0);
	private static AtomicInteger errorCount = new AtomicInteger(0);
	private static AtomicInteger systemExceptionCount = new AtomicInteger(0);
	private static ConcurrentHashMap<String, AtomicInteger> exceptionInfo = new ConcurrentHashMap<String, AtomicInteger>();

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("報錯了：" + t.getName() + ":" + e.getMessage());
				e.printStackTrace();
			}
		});

		File dir = new File("D:/work/project/gongshang/shandong/log");
		if (dir.isDirectory()) {
			File[] listFiles = dir.listFiles();
			CountDownLatch latch = new CountDownLatch(listFiles.length);
			for (File f : listFiles) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						// Pattern r = Pattern.compile("");
						// Matcher m = r.matcher(f.getName());
						String filename = f.getName();
						int nameIndex = filename.indexOf(".log");
						if (nameIndex > 0) {
							BufferedReader reader = null;
							try {
								reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "GBK"));
								String line = reader.readLine();
								while (line != null) {
									totalCount.incrementAndGet();
									if (line.indexOf("ERROR") >= 0) {
										errorCount.incrementAndGet();
									}
									if (line.indexOf("SystemException") >= 0) {
										systemExceptionCount.incrementAndGet();
									}
									if (line.indexOf("Exception") >= 0) {
										String exception = line.substring(0, line.indexOf("Exception") + 9);
										AtomicInteger size = exceptionInfo.get(exception);
										if (size == null) {
											AtomicInteger temp = new AtomicInteger(0);
											size = exceptionInfo.putIfAbsent(exception, temp);
											if (size == null) {
												size = temp;
											}
										}
										size.incrementAndGet();
									}
									line = reader.readLine();
								}
							} catch (Exception e) {
								throw new RuntimeException(filename, e);
							} finally {
								if (reader != null) {
									try {
										reader.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						}
						latch.countDown();
					}
				}, f.getName());
				es.execute(t);
			}
			es.shutdown();
			try {
				latch.await();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("totalCount=" + totalCount);
			System.out.println("errorCount=" + errorCount);
			System.out.println("systemExceptionCount=" + systemExceptionCount);
			for (Entry<String, AtomicInteger> e : exceptionInfo.entrySet()) {
				System.out.println(e.getKey() + ":" + e.getValue());
			}

			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime);
		}
	}

}
