package com.sdcommunity.qa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * ProblemLabel的对应关系
 * @author Leon
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_pl")
@IdClass(ProblemLabel.class)
public class ProblemLabel implements Serializable {

    @Id
    private String problemid;
    @Id
    private String Labelid;
}
