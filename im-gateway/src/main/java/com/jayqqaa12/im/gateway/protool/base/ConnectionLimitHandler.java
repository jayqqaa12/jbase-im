//package com.jayqqaa12.im.gateway.protool.base;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.channel.ChannelHandler.Sharable;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.atomic.AtomicLong;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Sharable
//public  final class ConnectionLimitHandler extends ChannelInboundHandlerAdapter {
//   private static final Logger logger = LoggerFactory.getLogger(ConnectionLimitHandler.class);
//   private final ConcurrentMap<InetAddress, AtomicLong> connectionsPerClient = new ConcurrentHashMap();
//   private final AtomicLong counter = new AtomicLong(0L);
//
//
//   public void channelActive(ChannelHandlerContext ctx) throws Exception {
//      long count = this.counter.incrementAndGet();
//      long limit = DatabaseDescriptor.getNativeTransportMaxConcurrentConnections();
//      if(limit < 0L) {
//         limit = 9223372036854775807L;
//      }
//
//      if(count > limit) {
//         logger.warn("Exceeded maximum native connection limit of {} by using {} connections", Long.valueOf(limit), Long.valueOf(count));
//         ctx.close();
//      } else {
//         long perIpLimit = DatabaseDescriptor.getNativeTransportMaxConcurrentConnectionsPerIp();
//         if(perIpLimit > 0L) {
//            InetAddress address = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress();
//            AtomicLong perIpCount = (AtomicLong)this.connectionsPerClient.get(address);
//            if(perIpCount == null) {
//               perIpCount = new AtomicLong(0L);
//               AtomicLong old = (AtomicLong)this.connectionsPerClient.putIfAbsent(address, perIpCount);
//               if(old != null) {
//                  perIpCount = old;
//               }
//            }
//
//            if(perIpCount.incrementAndGet() > perIpLimit) {
//               logger.warn("Exceeded maximum native connection limit per ip of {} by using {} connections", Long.valueOf(perIpLimit), perIpCount);
//               ctx.close();
//               return;
//            }
//         }
//
//         ctx.fireChannelActive();
//      }
//
//   }
//
//   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//      this.counter.decrementAndGet();
//      InetAddress address = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress();
//      AtomicLong count = (AtomicLong)this.connectionsPerClient.get(address);
//      if(count != null && count.decrementAndGet() <= 0L) {
//         this.connectionsPerClient.remove(address);
//      }
//
//      ctx.fireChannelInactive();
//   }
//}
