package com.spring.app.personnel.domain;

/** 
* @FileName  : WorkhistoryVO.java 
* @Project   : TempFinal 
* @Date      : 2023. 12. 20 
* @작성자      : 윤예인 (eni1221) 
* @변경이력    : 
* @프로그램설명    : 
*/
public class WorkhistoryVO {

	
	/**
	 * 근무내역id
	 */
	private Long workhistoryid; 
	
	
	/**
	 * 사원id
	 */
	private Long fk_empid; 
	
	/**
	 * 근무종류
	 */
	private String worktype; 
	
	/**
	 * 등록일시
	 */
	private String resisterdaytime;

	/**
	 * @return the workhistoryid
	 */
	public Long getWorkhistoryid() {
		return workhistoryid;
	}

	/**
	 * @param workhistoryid the workhistoryid to set
	 */
	public void setWorkhistoryid(Long workhistoryid) {
		this.workhistoryid = workhistoryid;
	}

	/**
	 * @return the fk_empid
	 */
	public Long getFk_empid() {
		return fk_empid;
	}

	/**
	 * @param fk_empid the fk_empid to set
	 */
	public void setFk_empid(Long fk_empid) {
		this.fk_empid = fk_empid;
	}

	/**
	 * @return the worktype
	 */
	public String getWorktype() {
		return worktype;
	}

	/**
	 * @param worktype the worktype to set
	 */
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}

	/**
	 * @return the resisterdaytime
	 */
	public String getResisterdaytime() {
		return resisterdaytime;
	}

	/**
	 * @param resisterdaytime the resisterdaytime to set
	 */
	public void setResisterdaytime(String resisterdaytime) {
		this.resisterdaytime = resisterdaytime;
	}

	 
	
	
	
}
