package com.util;


/**
 * 接口异常处理
 *
 */
public class SysException extends RuntimeException {
	/** */
	private static final long serialVersionUID = 1L;
	
	public ExceptionEnum expenum;

	public SysException() {
	}
	
	public SysException(String msg) {
		super(msg);
	}
	
	public SysException(Throwable t) {
		super(t);
	}

	public SysException(ExceptionEnum ee) {
		super(ee.getCode());
		this.expenum = ee;
	}
	public SysException(ExceptionEnum ee, String... args) { super(ee.getCode());
		ee.setArgs(args);
		this.expenum = ee;
	}
	public static enum ExceptionEnum {
		_400("保存失败"),
		_500("更新失败"),
		_600("查询失败"),
		_401("请输入正确的手机号码"),
		_402("请输入密码"),
		_403("密码不能小于6位"),
		_404("请输入昵称"),
		_405("该手机号码已经注册过"),
		_406("昵称不为能空！"),
		_407("请输入旧密码！"),
		_408("旧密码不一致！"),
		_409("请输入新密码！"),
		_410("新旧密码不能一致！"),
		_99998("数据为空"),
		_99999("数据异常"),
		;

		private String value;
		private String[] args;

		ExceptionEnum(String value) {
			this.value = value;
		}
		public void setValue(String value) {
			this.value = value;
		}	
		public String getCode() {
			return super.toString().replace("_", "");
		}

		public String getValue() {
			return this.value;
		}
		public String[] getArgs() {
			return args;
		}
		public void setArgs(String[] args) {
			this.args = args;
		}		
	}
}
