import java.sql.Date;
import java.time.LocalDate;


public class Usuario {
    String fullName;
    String userName;
    String email;
    String password;
    java.sql.Date creationDate;
    java.sql.Date modificationDate;
    public Usuario(String fullName, String userName, String email, String password) {

        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.creationDate = Date.valueOf(LocalDate.now());
        this.modificationDate = Date.valueOf(LocalDate.now());

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreationDate(Date fecha){
        this.creationDate = fecha;
    }

    public Date getCreationDate() {
        return creationDate;
    }


    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate() {
        this.modificationDate = Date.valueOf(LocalDate.now());
    }
}
