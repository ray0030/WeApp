package com.util;


/**
 * �ӿ��쳣����
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
		_400("����ʧ��"),
		_500("����ʧ��"),
		_600("��ѯʧ��"),
		_401("��������ȷ���ֻ�����"),
		_402("����������"),
		_403("���벻��С��6λ"),
		_404("�������ǳ�"),
		_405("���ֻ������Ѿ�ע���"),
		_406("�ǳƲ�Ϊ�ܿգ�"),
		_407("����������룡"),
		_408("�����벻һ�£�"),
		_409("�����������룡"),
		_410("�¾����벻��һ�£�"),
		_99998("����Ϊ��"),
		_99999("�����쳣"),
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
