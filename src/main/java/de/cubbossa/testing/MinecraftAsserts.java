package de.cubbossa.testing;

import com.destroystokyo.paper.event.executor.MethodHandleEventExecutor;
import de.cubbossa.pathfinder.test.TestPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.*;

@RequiredArgsConstructor
public class MinecraftAsserts {

	public static <E extends Event> void assertInEvent(Class<E> eventType, TestContext context, ThrowingConsumer<E> listener) {
		Listener lis = new Listener() {
			@EventHandler
			public void onEvent(Event e) {
				if (e.getClass().equals(eventType)) {
					try {
						listener.apply((E) e);
						HandlerList handlerList = (HandlerList) eventType.getMethod("getHandlerList").invoke(e);
						handlerList.unregister(this);
						context.next();
					} catch (Throwable t) {
						context.fail(t);
					}
				}
			}
		};
		try {
			Bukkit.getPluginManager().registerEvent(eventType, lis, EventPriority.HIGHEST,
					new MethodHandleEventExecutor(eventType, lis.getClass().getMethod("onEvent", Event.class)),
					TestPlugin.getInstance());
		} catch (Throwable t) {
			context.fail(t);
		}
	}

	public static <E extends Event> void assertEventCalled(Class<E> eventType, TestContext context) {
		assertInEvent(eventType, context, value -> {});
	}

	public interface ThrowingConsumer<V> {
		void apply(V value) throws Throwable;
	}
}
