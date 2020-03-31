package com.vsj.service.Impl;

import com.vsj.dao.*;
import com.vsj.model.VsjMember;
import com.vsj.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements IMemberService{

	@Autowired
	private VsjMemberDAO vsjMemberDAO;

	
	@Override
	public VsjMember findMemberById(Integer id) {
		VsjMember member = vsjMemberDAO.selectByPrimaryKey(id);
		return member;
	}

}
