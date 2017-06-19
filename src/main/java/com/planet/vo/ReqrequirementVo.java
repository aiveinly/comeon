package com.planet.vo;

import com.planet.reqrequirement.domain.ReqRequirement;
import com.planet.sysfile.domain.SysFile;

import java.util.Date;
import java.util.List;

/**
 * Created by Li on 2016/2/15.
 */
public class ReqrequirementVo {
        private ReqRequirement reqRequirement;
        private List<SysFile> sysFiles;

    public ReqRequirement getReqRequirement() {
        return reqRequirement;
    }

    public void setReqRequirement(ReqRequirement reqRequirement) {
        this.reqRequirement = reqRequirement;
    }

    public List<SysFile> getSysFiles() {
        return sysFiles;
    }

    public void setSysFiles(List<SysFile> sysFiles) {
        this.sysFiles = sysFiles;
    }
}
