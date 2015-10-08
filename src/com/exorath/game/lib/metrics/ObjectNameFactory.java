package com.exorath.game.lib.metrics;

import javax.management.ObjectName;

public interface ObjectNameFactory {

	ObjectName createName(String type, String domain, MetricName name);
}
