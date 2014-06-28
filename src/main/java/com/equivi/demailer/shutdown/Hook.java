package com.equivi.demailer.shutdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hook {

	private static final Logger logger = LoggerFactory.getLogger(Hook.class);

	private boolean keepRunning = true;

	private final Thread thread;

	Hook(Thread thread) {
		this.thread = thread;
	}

	/**
	 * @return True if the daemon thread is to keep running
	 */
	public boolean keepRunning() {
		return keepRunning;
	}

	/**
	 * Tell the client daemon thread to shutdown and wait for it to close gracefully.
	 */
	public void shutdown() {
		keepRunning = false;
		thread.interrupt();
		try {
			thread.join();
		} catch (InterruptedException e) {
			logger.error("Error shutting down thread with hook", e);
		}
	}
}