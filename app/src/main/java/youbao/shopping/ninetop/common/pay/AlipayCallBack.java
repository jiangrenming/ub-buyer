package youbao.shopping.ninetop.common.pay;

public interface AlipayCallBack {
	/**
	 * 支付成功
	 */
	public void paySuccess();

	/**
	 * 支付失败
	 */
	public void payFailure();

}
