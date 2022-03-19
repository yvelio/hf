package com.holdemfactory.history.client.domain;

public class History {
	private Long historyId;
	private String fileName;
	
	public Long getHistoryId() {
		return historyId;
	}
	
	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "HistoryClient [historyId=" + (historyId != null ? historyId : null) + ", fileName=" + fileName + "]";
	}
	
	
	
}
