package com.acgist.snail.net.torrent.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acgist.snail.net.torrent.PeerConnect;
import com.acgist.snail.net.torrent.peer.bootstrap.PeerSubMessageHandler;
import com.acgist.snail.pojo.session.PeerSession;
import com.acgist.snail.pojo.session.TorrentSession;
import com.acgist.snail.system.config.PeerConfig;

/**
 * <p>Peer接入</p>
 * <p>被动接入Peer</p>
 * 
 * @author acgist
 * @since 1.0.2
 */
public final class PeerUploader extends PeerConnect {

	private static final Logger LOGGER = LoggerFactory.getLogger(PeerUploader.class);
	
	private PeerUploader(PeerSession peerSession, TorrentSession torrentSession, PeerSubMessageHandler peerSubMessageHandler) {
		super(peerSession, torrentSession, peerSubMessageHandler);
		this.available = true;
	}
	
	public static final PeerUploader newInstance(PeerSession peerSession, TorrentSession torrentSession, PeerSubMessageHandler peerSubMessageHandler) {
		return new PeerUploader(peerSession, torrentSession, peerSubMessageHandler);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>下载条件：解除阻塞或者快速允许</p>
	 */
	@Override
	public void download() {
		if(
			this.peerSession.isPeerUnchoked() || // 解除阻塞
			this.peerSession.supportAllowedFast() // 快速允许
		) {
			super.download();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>设置非上传状态</p>
	 */
	@Override
	public void release() {
		try {
			if(this.available) {
				LOGGER.debug("PeerUploader关闭：{}-{}", this.peerSession.host(), this.peerSession.port());
				super.release();
			}
		} catch (Exception e) {
			LOGGER.error("PeerUploader关闭异常", e);
		} finally {
			this.peerSession.statusOff(PeerConfig.STATUS_UPLOAD);
			this.peerSession.peerUploader(null);
			this.peerSession.reset();
		}
	}
	
}
