package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MemberUpgradeList implements Serializable {
    private List<MemberUpgrade> memberUpgradeList;
}
