package com.innovez.service.dto;

import java.io.Serializable;
import java.util.Date;

import com.innovez.entity.Money;

public interface TransferStatus extends Serializable {
	String getSourceAccountNumber();
	String getSourceAccountOwner();
	String getTargetAccountNumber();
	String getTargetAccountOwner();
	Money getTransferAmount();
	Date getTimestamp();
}
