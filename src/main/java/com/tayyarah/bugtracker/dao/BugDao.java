package com.tayyarah.bugtracker.dao;

import java.util.List;

import com.tayyarah.bugtracker.entity.BugTrackerHistory;

public abstract class  BugDao {
	public abstract List<BugTrackerHistory> bugPendingStatusList();	 
 }
