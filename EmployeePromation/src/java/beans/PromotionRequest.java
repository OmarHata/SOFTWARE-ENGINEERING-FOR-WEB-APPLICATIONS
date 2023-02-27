/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author User
 */
public class PromotionRequest {
    private Integer pId;
    private Integer empId;
    private Integer depId;
    private Integer oldRank;
    private Integer newRank;
    private String managerApp;
    private String directorApp;
    private String vpApp;
    private String presedentApp;
    private String  status;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getOldRank() {
        return oldRank;
    }

    public void setOldRank(Integer oldRank) {
        this.oldRank = oldRank;
    }

    public Integer getNewRank() {
        return newRank;
    }

    public void setNewRank(Integer newRank) {
        this.newRank = newRank;
    }

    public String getManagerApp() {
        return managerApp;
    }

    public void setManagerApp(String managerApp) {
        this.managerApp = managerApp;
    }

    public String getDirectorApp() {
        return directorApp;
    }

    public void setDirectorApp(String directorApp) {
        this.directorApp = directorApp;
    }

    public String getVpApp() {
        return vpApp;
    }

    public void setVpApp(String vpApp) {
        this.vpApp = vpApp;
    }

    public String getPresedentApp() {
        return presedentApp;
    }

    public void setPresedentApp(String presedentApp) {
        this.presedentApp = presedentApp;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }
    
    
    
   
    
    
}
