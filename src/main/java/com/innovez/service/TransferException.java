package com.innovez.service;

@SuppressWarnings("serial")
public class TransferException extends RuntimeException {
	public TransferException(String message) {
		super(message);
	}
	
	public static class UnregisteredAccountException extends TransferException {

		public UnregisteredAccountException(String message) {
			super(message);
		}
	}
	public static class SuspendedAccountUsedException extends TransferException {

		public SuspendedAccountUsedException(String message) {
			super(message);
		}
	}
	public static class UnsufficientBalanceException extends TransferException {

		public UnsufficientBalanceException(String message) {
			super(message);
		}
	}
}
