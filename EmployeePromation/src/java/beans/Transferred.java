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
public class Transferred {
    Integer id ;
    String  managerAnswer;
    String  directorOneAnswer;
    String  directorTwoAnswer;
    String  vpAnswer;
    String  transfeerdStatus;
    Integer fromDpId;
    Integer toDepId;
    Integer empId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManagerAnswer() {
        return managerAnswer;
    }

    public void setManagerAnswer(String managerAnswer) {
        this.managerAnswer = managerAnswer;
    }

    public String getDirectorOneAnswer() {
        return directorOneAnswer;
    }

    public void setDirectorOneAnswer(String directorOneAnswer) {
        this.directorOneAnswer = directorOneAnswer;
    }

    public String getDirectorTwoAnswer() {
        return directorTwoAnswer;
    }

    public void setDirectorTwoAnswer(String directorTwoAnswer) {
        this.directorTwoAnswer = directorTwoAnswer;
    }

    public String getVpAnswer() {
        return vpAnswer;
    }

    public void setVpAnswer(String vpAnswer) {
        this.vpAnswer = vpAnswer;
    }

    public String getTransfeerdStatus() {
        return transfeerdStatus;
    }

    public void setTransfeerdStatus(String transfeerdStatus) {
        this.transfeerdStatus = transfeerdStatus;
    }

    public Integer getFromDpId() {
        return fromDpId;
    }

    public void setFromDpId(Integer fromDpId) {
        this.fromDpId = fromDpId;
    }

    public Integer getToDepId() {
        return toDepId;
    }

    public void setToDepId(Integer toDepId) {
        this.toDepId = toDepId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
    
    
}
