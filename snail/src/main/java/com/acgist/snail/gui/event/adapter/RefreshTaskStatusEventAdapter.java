package com.acgist.snail.gui.event.adapter;

import com.acgist.snail.gui.GuiManager;
import com.acgist.snail.gui.event.GuiEvent;
import com.acgist.snail.pojo.message.ApplicationMessage;

/**
 * <p>GUI刷新任务状态事件</p>
 * 
 * @author acgist
 * @since 1.1.0
 */
public abstract class RefreshTaskStatusEventAdapter extends GuiEvent {

	protected RefreshTaskStatusEventAdapter() {
		super(Type.REFRESH_TASK_STATUS, "刷新任务状态事件");
	}

	@Override
	protected final void executeExtend(Object ... args) {
		final ApplicationMessage message = ApplicationMessage.message(ApplicationMessage.Type.REFRESH);
		GuiManager.getInstance().sendExtendGuiMessage(message);
	}

}
