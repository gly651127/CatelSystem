/*
 * Copyright (C) 2014 huawei.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.catel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * �̳߳ز�������
 * @author <a href="mailto:Qian.Keane@gmail.com">keane</a>
 * @version 1.0
 */
public class ThreadUtils {
    private static final ExecutorService pool;

    /**
     * ִ�п����е�����
     * @param command
     */
    public static void execute(Runnable command) {
        pool.execute(command);
    }

    /**
     * �ύһ�����ֵ������
     * @param <T>
     * @param task @return
     */
    public static <T> Future<T> submit(Callable<T> task) {
        return pool.submit(task);
    }

    /**
     * �ύһ�������е�����
     * @param task @return
     */
    public static Future<?> submit(Runnable task) {
        return pool.submit(task);
    }

    /**
     * �ύһ�������е�����
     * @param <T>
     * @param task
     * @param result @return
     */
    public static <T> Future<T> submit(Runnable task, T result) {
        return pool.submit(task, result);
    }


    static {
        pool = Executors.newCachedThreadPool();
    }

	private static HashMap<String, ArrayList<Future<?>>> instances = new HashMap<String, ArrayList<Future<?>>>();

	public static void putTask(String name, Future<?> task) {
		if (instances.containsKey(name)) {
			instances.get(name).add(task);
		} else {
			ArrayList<Future<?>> list = new ArrayList<Future<?>>();
			list.add(task);
			instances.put(name, list);
		}
	}

	public static ArrayList<Future<?>> getTask(String name) {
		ArrayList<Future<?>> list = null;
		if (instances.containsKey(name)) {
			list = instances.get(name);
			instances.remove(name);
		}
		return list;
	}
	
	public static void cancelTask(String name) {
		ArrayList<Future<?>> list = getTask(name);
		if (null != list) {
			for (Future<?> task : list) {
				if (null != task && !task.isCancelled()) {
					
					task.cancel(true);
				}
			}
		}
	}
}
