package com.inspur.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileManager.Location;

public class LogAnalyse7 {
	static class Result {
		String filename;
		int totalCount;// 日志总行数（总行数）
		int errorCount;// 日志中的错误行数（匹配行数）
		int systemExceptionCount;// 日志中SystemException的行数（某domain的行数）
		Map<String, Integer> exceptionInfo = new HashMap<>();// 日志中所有异常的统计信息（所有domain的行数和(如果要添加其他信息，可以把Integer换成自定义类)）

		@Override
		public String toString() {
			return "Result [filename=" + filename + ", totalCount=" + totalCount + ", errorCount=" + errorCount + ", systemExceptionCount="
					+ systemExceptionCount + ", exceptionInfo=" + exceptionInfo + "]";
		}
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		File dir = new File("D:/work/project/gongshang/shandong/log");
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		if (dir.isDirectory()) {
			File[] listFiles = dir.listFiles();
			List<ForkJoinTask<Result>> resultList = new ArrayList<>();
			for (File f : listFiles) {
				ForkJoinTask<Result> task = pool.submit(new RecursiveTask<Result>() {
					@Override
					protected Result compute() {
						Result result = new Result();
						String filename = f.getName();
						result.filename = filename;
						int nameIndex = filename.indexOf(".log");
						if (nameIndex > 0) {
							BufferedReader reader = null;
							try {
								reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "GBK"));
								String line = reader.readLine();
								while (line != null) {
									result.totalCount++;
									if (line.indexOf("ERROR") >= 0) {
										result.errorCount++;
									}
									if (line.indexOf("SystemException") >= 0) {
										result.systemExceptionCount++;
									}
									if (line.indexOf("Exception") >= 0) {
										String exception = line.substring(0, line.indexOf("Exception") + 9);
										Integer size = result.exceptionInfo.get(exception);
										int s = size == null ? 1 : size + 1;
										result.exceptionInfo.put(exception, s);
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
							// System.out.println("----" +
							// f.getAbsolutePath());
						}
						return result;
					}
				});
				resultList.add(task);
			}
			pool.shutdown();
			Result totalResult = new Result();
			for (ForkJoinTask<Result> task : resultList) {
				Result r;
				try {
					r = task.get();
					// System.out.println(r);
					totalResult.totalCount += r.totalCount;
					totalResult.errorCount += r.errorCount;
					totalResult.systemExceptionCount += r.systemExceptionCount;
					for (Entry<String, Integer> e : r.exceptionInfo.entrySet()) {
						// System.out.println(e.getKey() + ":" + e.getValue());
						Integer size = totalResult.exceptionInfo.get(e.getKey());
						int s = size == null ? e.getValue() : size + e.getValue();
						totalResult.exceptionInfo.put(e.getKey(), s);
					}
				} catch (Exception e) {
					System.err.println("解析失败：" + e.getMessage());
					e.printStackTrace();
				}
			}
			System.out.println(totalResult);

			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime);
		}
	}

}
