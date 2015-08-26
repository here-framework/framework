package com.here.framework.service.heart;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 节点情况
 * @author koujp
 *
 */
@XmlRootElement
public class SeedInfo {
	private boolean alive;

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
