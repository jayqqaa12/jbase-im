//package com.jayqqaa12.im.util;
//
//import com.baomidou.mybatisplus.core.toolkit.IdWorker;
//import com.google.gateway.collect.Maps;
//import com.google.gateway.collect.Queues;
//import com.jayqqaa12.im.common.model.vo.TcpRespVO;
//import com.lmax.disruptor.BlockingWaitStrategy;
//import com.lmax.disruptor.WorkHandler;
//import com.lmax.disruptor.dsl.Disruptor;
//import com.lmax.disruptor.dsl.ProducerType;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.BlockingQueue;
//
///**
// * @author: 12
// * @create: 2019-09-12 15:11
// **/
//public class DisruptorKit {
//
//    private static Map<Disruptor, BlockingQueue> blockingQueueMap = Maps.newConcurrentMap();
//
//
//    private static Map<String, Disruptor> disruptorMap = Maps.newConcurrentMap();
//
//    public static Disruptor newDisruptor(String name, int ringBufferSize, WorkHandler workHandler, int concurrenceNum) {
//
//        Disruptor disruptor = new Disruptor(ArrayList::new, ringBufferSize,
//                (Runnable r) -> new Thread(r, "disruptor-" + name + "-" + IdWorker.getId()),
//                ProducerType.MULTI, new BlockingWaitStrategy());
//
//        disruptor.setDefaultExceptionHandler(new EventExceptionHandler());
//
//        WorkHandler[] workHandlers = new WorkHandler[concurrenceNum];
//        for (int i = 0; i < concurrenceNum; i++) {
//            workHandlers[i]=workHandler;
//        }
//
//        disruptor.handleEventsWithWorkerPool(workHandlers);
//
//        disruptor.start();
//        disruptorMap.put(name, disruptor);
//
//        if (!blockingQueueMap.containsKey(disruptor)) {
//            blockingQueueMap.putIfAbsent(disruptor, Queues.newArrayBlockingQueue(10_000));
//        }
//
//        return disruptor;
//    }
//
//
//    public static Disruptor newDisruptor(String name, int ringBufferSize, WorkHandler workHandler) {
//        return newDisruptor(name, ringBufferSize, workHandler, 1);
//    }
//
//
//    public static void fresh() {
//
//        blockingQueueMap.forEach((k, v) -> {
//            sendEvent(k, v);
//        });
//    }
//
//    public static void onEvent(Disruptor disruptor, Object event) {
//
//
//        BlockingQueue queue = blockingQueueMap.get(disruptor);
//
//        queue.add(event);
//
//        if (queue.size() < 100) return;
//
//        sendEvent(disruptor, queue);
//
//    }
//
//
//    public static void onEvent(String name, Object event) {
//        Disruptor disruptor = disruptorMap.get(name);
//
//        if (disruptor == null) throw new RuntimeException(name + " disruptor not define");
//        onEvent(disruptor, event);
//    }
//
//    private static void sendEvent(Disruptor<List> disruptor, BlockingQueue<TcpRespVO> queue) {
//
//        if (queue.isEmpty()) return;
//
//        disruptor.publishEvent((e, seq) -> {
//            while (!queue.isEmpty()) {
//                Object obj = queue.poll();
//                if (obj != null) e.add(obj);
//            }
//        });
//    }
//
//
//    public static void stopAll() {
//
//        for (Disruptor disruptor : disruptorMap.values()) {
//            disruptor.shutdown();
//        }
//    }
//
//
//}
