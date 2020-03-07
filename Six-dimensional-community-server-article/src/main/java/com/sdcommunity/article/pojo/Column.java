package com.sdcommunity.article.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name="tb_column")
public class Column implements Serializable{

	@Id
	private String id;//ID
	private String name;//专栏名称
	private String summary;//专栏简介
	private String userid;//用户ID
	private java.sql.Date createtime;//申请日期
	private java.sql.Date checktime;//审核日期
	private String state;//状态
}
