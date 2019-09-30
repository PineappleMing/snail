package com.acgist.snail.net;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import com.acgist.snail.system.config.SystemConfig;
import com.acgist.snail.system.exception.NetException;

/**
 * <p>消息代理</p>
 * <p>消息发送</p>
 * 
 * @author acgist
 * @since 1.1.0
 */
public interface IMessageHandler {
	
	/**
	 * 连接超时时间
	 */
	int CONNECT_TIMEOUT = SystemConfig.CONNECT_TIMEOUT;

	/**
	 * 发送超时时间
	 */
	int SEND_TIMEOUT = SystemConfig.SEND_TIMEOUT;
	
	/**
	 * 是否可用
	 * 
	 * @return 可用状态
	 */
	boolean available();
	
	/**
	 * 消息发送
	 * 
	 * @param message 消息内容
	 * 
	 * @throws NetException 网络异常
	 */
	default void send(String message) throws NetException {
		send(message, null);
	}
	
	/**
	 * 消息发送
	 * 
	 * @param message 消息内容
	 * @param charset 编码格式
	 * 
	 * @throws NetException 网络异常
	 * 
	 * @since 1.1.0
	 */
	default void send(String message, String charset) throws NetException {
		send(this.charset(message, charset));
	}
	
	/**
	 * 消息发送
	 * 
	 * @param message 消息内容
	 * 
	 * @throws NetException 网络异常
	 */
	default void send(byte[] message) throws NetException {
		send(ByteBuffer.wrap(message));
	}
	
	/**
	 * 消息发送（所有其他消息均有这个方法发送）
	 * 
	 * @param buffer 消息内容
	 * 
	 * @throws NetException 网络异常
	 */
	void send(ByteBuffer buffer) throws NetException;

	/**
	 * 获取远程客户端
	 * 
	 * @return 远程客户端/服务端地址
	 */
	InetSocketAddress remoteSocketAddress();
	
	/**
	 * 关闭
	 */
	void close();
	
	/**
	 * 字符编码
	 * 
	 * @param message 消息
	 * @param charset 编码
	 * 
	 * @return 编码后的消息
	 * 
	 * @throws NetException 网络异常
	 */
	default byte[] charset(String message, String charset) throws NetException {
		if(charset == null) {
			return message.getBytes();
		} else {
			try {
				return message.getBytes(charset);
			} catch (UnsupportedEncodingException e) {
				throw new NetException(String.format("编码异常，编码：%s，内容：%s。", charset, message), e);
			}
		}
	}
	
}
